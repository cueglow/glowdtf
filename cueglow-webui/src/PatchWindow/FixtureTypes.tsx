import { Button, Classes, Dialog, Position, Toaster, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps } from "@reach/router";
import _ from "lodash";
import React, { useCallback, useContext, useMemo, useRef, useState } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { GlowTabulator } from "src/Components/GlowTabulator";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { LeftRightSplit } from "src/Components/LeftRightSplit";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import styled from 'styled-components';
import { } from 'styled-components/macro';
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { DmxModeString, FixtureType, fixtureTypeString } from "../Types/FixtureType";
import { ChannelFunctionGraphWrapper } from "./ChannelFunctionGraph";

export function FixtureTypes(props: RouteComponentProps) {
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType | undefined>(undefined);

    return (
        <LeftRightSplit growLeft={.5}>
            <div css={`
                display: flex;
                flex-direction: column;
            `}>
                <div style={{
                    marginBottom: bp.vars.ptGridSize,
                }}>
                    <AddGdtfButton />
                </div>
                <div style={{
                    flexGrow: 1,
                    minHeight: 0,
                }}>
                    <FixtureTypeTable
                        rowSelectionChanged={
                            (selectedData) => setSelectedFixtureType(selectedData[0])} />
                </div>
            </div>
            <>
                <div style={{ display: "flex", }}>
                    <h3 className="bp3-heading">Details</h3>
                    <div style={{ flexGrow: 1, }} />
                    <RemoveGdtfButton selectedFixtureType={selectedFixtureType} />
                </div>
                <FixtureTypeDetails fixtureType={selectedFixtureType} />
            </>
        </LeftRightSplit>
    );
}

function FixtureTypeTable(props: { rowSelectionChanged: (data: FixtureType[]) => void }) {
    const patchData = useContext(PatchContext);

    const columns = [
        { field: "manufacturer", title: "Manufacturer" },
        { field: "name", title: "Name" },
    ];

    return (
        <GlowTabulator
            // It is important that the data array is NEVER mutated, only
            // replaced. This is because the diffing algorithm of
            // react-tabulator does not make a copy of the previous state for
            // later comparison. It could therefore happen that the new state is
            // the same as the old one, because the old one was modified in the
            // same way that created the new one. 
            data={patchData.fixtureTypes}
            columns={columns}
            // TODO fitDataStretch: When making window narrow, not all data is visible in column width 
            // and table-internal horizontal scrolling does not activate even though it should
            options={{
                height: "100%",
                layout: "fitDataStretch",
                selectable: 1,
                rowSelectionChanged: props.rowSelectionChanged,
            }} />
    );
}

function AddGdtfButton() {

    const hotkeys = useMemo(() => [
        {
            combo: "a",
            global: true,
            label: "Add New GDTF Fixture Type",
            onKeyDown: () => document.body.classList.contains("bp3-overlay-open") || selectFile()
        }
    ], []);
    useHotkeys(hotkeys);

    const fileInput = useRef<HTMLInputElement>(null);
    const toaster = useRef<Toaster>(null);

    function selectFile() {
        // Reset File Input
        if (fileInput.current !== null) {
            fileInput.current.value = ""
        }
        fileInput.current?.click()
    }

    function uploadFile() {
        // if empty do nothing
        if (fileInput.current?.value === "") { return }

        const file = fileInput.current?.files?.[0]
        if (file == null) { return }
        const formData = new FormData();
        formData.append("file", file);
        const upload = fetch('/api/fixturetype', { method: "POST", body: formData });
        upload.then(
            async (successResponse) => {
                if (!successResponse.ok) {
                    const responseJson = await successResponse.json()
                    const msg = <>
                        <b>{_.startCase(responseJson.data.name)}</b>
                        <br />
                        {responseJson.data.description}
                    </>
                    toaster.current?.clear()
                    toaster.current?.show({ intent: "danger", message: msg, timeout: 12_000 })
                }
            },
            (failureResponse) => {
                console.log("Network Error on GDTF Upload", failureResponse)
                toaster.current?.clear()
                toaster.current?.show({ intent: "danger", message: "Network Error", timeout: 12_000 })
            },
        )
    }

    return (
        <>
        <Button intent="success" icon="plus" onClick={selectFile}>
            <LabelWithHotkey label="Add GDTF" combo="A" />
            <input type="file" id="gdtfFileInput" ref={fileInput} accept=".gdtf"
                onChange={uploadFile} data-cy="gdtf_hidden_input" hidden />
        </Button>
        <Toaster position={Position.BOTTOM} ref={toaster}/>
        </>
    )
}


function RemoveGdtfButton(props: { selectedFixtureType: FixtureType | undefined }) {
    const selectedFixtureType = props.selectedFixtureType

    const patchData = useContext(PatchContext);

    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [numberOfFixturesThatWillBeDeleted, setNumberOfFixturesThatWillBeDeleted] = useState<number | undefined>(undefined);

    const removeFixtureType = useCallback(() => {
        if (selectedFixtureType !== undefined) {
            setNumberOfFixturesThatWillBeDeleted(
                patchData.fixtures.filter((fixture) =>
                    fixture.fixtureTypeId === selectedFixtureType.fixtureTypeId
                ).length
            )
            setIsDialogOpen(true);
            // TODO once we have undo/redo
            // don't show Alert, instead, 
            // if patched fixtures were removed in this operation, show toast
            // with how many fixtures were removed and with undo button
        }
    }, [patchData.fixtures, selectedFixtureType])

    const hotkeys = useMemo(() => [
        {
            combo: "del",
            global: true,
            label: "Remove the selected fixture type",
            onKeyDown: () => document.body.classList.contains("bp3-overlay-open") || removeFixtureType(),
            disabled: selectedFixtureType === undefined,
        },
    ], [removeFixtureType, selectedFixtureType]);
    useHotkeys(hotkeys);

    const handleDialogClose = useCallback(() => {
        setIsDialogOpen(false);
        setNumberOfFixturesThatWillBeDeleted(undefined);
    }, []);

    const handleConfirmedRemove = useCallback(() => {
        if (selectedFixtureType !== undefined) {
            const msg = new ClientMessage.RemoveFixtureTypes([selectedFixtureType.fixtureTypeId])
            connectionProvider.send(msg)
        }
        setIsDialogOpen(false);
        setNumberOfFixturesThatWillBeDeleted(undefined);
    }, [selectedFixtureType])

    return (
        <>
            <Button minimal={true} icon="trash"
                disabled={selectedFixtureType === undefined}
                onClick={removeFixtureType}
                data-cy="remove_selected_fixture_type_button">
                <LabelWithHotkey label="Remove" combo="Del" />
            </Button>
            <Dialog
                title={`Removing ${fixtureTypeString(selectedFixtureType)}`}
                isOpen={isDialogOpen && selectedFixtureType !== undefined}
                canEscapeKeyClose={true}
                canOutsideClickClose={true}
                onClose={(e) => {
                    e.nativeEvent.stopImmediatePropagation()
                    handleDialogClose()
                }}
                className="bp3-dark"
            >
                <div className={Classes.DIALOG_BODY}>
                    <RemoveDialogMessage numberOfFixturesThatWillBeDeleted={numberOfFixturesThatWillBeDeleted} />
                </div>
                <div className={Classes.DIALOG_FOOTER}>
                    <div className={Classes.DIALOG_FOOTER_ACTIONS}>
                        <Button
                            text="Cancel"
                            onClick={handleDialogClose}
                            autoFocus
                        />
                        <Button
                            text="Remove"
                            intent="danger"
                            onClick={handleConfirmedRemove}
                            data-cy="confirm_fixture_type_remove_button"
                        />
                    </div>
                </div>
            </Dialog>
        </>
    );
}

function RemoveDialogMessage(props: { numberOfFixturesThatWillBeDeleted: number | undefined }) {
    const fixtureNumber = props.numberOfFixturesThatWillBeDeleted

    if (fixtureNumber === undefined || fixtureNumber < 0) {
        console.error("Trying to assemble a remove warning message but the number of " +
            "fixtures that will be deleted is", fixtureNumber)
        return <p></p>
    }

    if (fixtureNumber === 0) {
        return <p>This will remove 0 Fixtures.</p>
    } else if (fixtureNumber === 1) {
        return <p>This will also remove 1 Fixture and all of its associated data.</p>
    } else {
        return <p>This will also remove {fixtureNumber} Fixtures and all of their associated data.</p>
    }
}

const AttributeTable = styled.table`
    td:nth-child(1) {  
        vertical-align: top;
        text-align: end;
        padding-right: .2em;
        font-weight: bold;
        padding-bottom: .2em;
        white-space: nowrap;
    }
    margin-bottom: ${2*bp.ptGridSizePx}px;
`

function FixtureTypeDetails(props: { fixtureType?: FixtureType }) {
    const fixtureType = props.fixtureType
    if (fixtureType === undefined) {
        return <div>Select a Fixture Type to show Details</div>
    }

    return (
        <div>
            <AttributeTable>
                <tbody>
                <tr>
                    <td>Manufacturer</td>
                    <td>{fixtureType.manufacturer}</td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td>{fixtureType.name}</td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td>{fixtureType.description}</td>
                </tr>
                <tr>
                    <td>Short Name</td>
                    <td>{fixtureType.shortName}</td>
                </tr>
                <tr>
                    <td>Long Name</td>
                    <td>{fixtureType.longName}</td>
                </tr>
                <tr>
                    <td>Fixture Type ID</td>
                    <td>{fixtureType.fixtureTypeId}</td>
                </tr>
                {fixtureType.refFT && 
                    <tr>
                        <td>Referenced Fixture Type</td>
                        <td>{fixtureType.refFT}</td>
                    </tr>
                }
                </tbody>
            </AttributeTable>
            <div>
                <h4 className="bp3-heading">Modes</h4>
                {fixtureType.modes.map((mode) => {
                    return (
                        <div key={fixtureType.fixtureTypeId + mode.name} css={`
                        padding-bottom: ${bp.ptGridSizePx}px;
                        padding-top: ${bp.ptGridSizePx}px;
                    `}>
                            <h5 className="bp3-heading">{DmxModeString(mode)}</h5>
                            <div css={`
                                column-width: ${23*bp.ptGridSizePx}px;
                                column-count: 4;
                                margin-bottom: ${bp.ptGridSizePx}px;
                            `}>
                            {mode.channelLayout.map( (dmxBreak, breakInd) => 
                                dmxBreak.map( (channelName, channelInd) => 
                                    <div key={breakInd + "_" + channelInd} css={`padding: .2em;`}>
                                        {breakInd+1}.{channelInd+1} {channelName}
                                    </div>
                                )
                            )}
                            </div>
                            <ChannelFunctionGraphWrapper dmxMode={mode} fixtureTypeName={fixtureTypeString(fixtureType)}/>
                        </div>);
                })}
            </div>
            <div css={`padding-bottom: ${bp.ptGridSizePx}px;`}>
                <h4 className="bp3-heading">Revisions</h4>
                <table className="bp3-html-table bp3-html-table-striped bp3-html-table-condensed bp3-html-table-bordered">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Date</th>
                            <th>Text</th>
                        </tr>
                    </thead>
                    <tbody>
                        {fixtureType.revisions.map( revision => 
                            <tr key={revision.date}>
                                <td>{revision.userId}</td>
                                <td>{revision.date}</td>
                                <td>{revision.text}</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    )
}