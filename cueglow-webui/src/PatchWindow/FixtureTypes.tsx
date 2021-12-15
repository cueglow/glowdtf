import { Button, Classes, Dialog, Position, Toaster, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps } from "@reach/router";
import React, { useCallback, useContext, useMemo, useRef, useState } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { GlowTabulator } from "src/Components/GlowTabulator";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { LeftRightSplit } from "src/Components/LeftRightSplit";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { DmxModeString, FixtureType, fixtureTypeString } from "../Types/FixtureType";
import { } from 'styled-components/macro';
import _ from "lodash";

export function FixtureTypes(props: RouteComponentProps) {
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType | undefined>(undefined);

    return (
        <LeftRightSplit>
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
                    <h4 className="bp3-heading">Details</h4>
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
            onKeyDown: selectFile
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
            onKeyDown: () => removeFixtureType(),
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
                onClose={handleDialogClose}
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

function FixtureTypeDetails(props: { fixtureType?: FixtureType }) {
    const fixtureType = props.fixtureType
    if (fixtureType === undefined) {
        return <div>Select a Fixture Type to show Details</div>
    }

    return (
        <div>
            <div>
                Manufacturer: {fixtureType.manufacturer}
            </div>
            <div css={`
                padding-bottom: ${2*bp.ptGridSizePx}px;
            `}>
                Name: {fixtureType.name}
            </div>
            <div>
                <h5 className="bp3-heading">Modes</h5>
                {fixtureType.modes.map((mode) => {
                    return (
                        <div key={fixtureType.fixtureTypeId + mode.name} css={`
                        padding-bottom: ${bp.ptGridSizePx}px;
                    `}>
                            <h6 className="bp3-heading">{DmxModeString(mode)}</h6>
                            {mode.channelLayout.map( (dmxBreak, breakInd) => 
                                dmxBreak.map( (channelName, channelInd) => 
                                    <div key={breakInd + "_" + channelInd}>
                                        {breakInd+1}.{channelInd+1} {channelName}
                                    </div>
                                )
                            )}
                        </div>);
                })}
            </div>
        </div>
    )
}