import { Button, Classes, Dialog, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps } from "@reach/router";
import React, { useCallback, useContext, useMemo, useRef, useState } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { GlowTabulator } from "src/Components/GlowTabulator";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { DmxModeString, FixtureType, fixtureTypeString } from "../Types/FixtureType";

export function FixtureTypes(props: RouteComponentProps) {
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType|undefined>(undefined);

    return (
        <div style={{
            position: "absolute",
            top: bp.vars.ptNavbarHeight,
            bottom: "0px",
            width: "100%",
            padding: bp.vars.ptGridSize,
            display: "flex",
            flexDirection: "row",
        }}>
            <div style={{
                // as flex-item
                minWidth: 0,
                flexBasis: 0,
                flexGrow: 1,
                paddingRight: bp.vars.ptGridSize,
                // as flex-container
                display: "flex",
                flexDirection: "column",
            }}>
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
            <div style={{ flexGrow: 1, flexBasis: 0, }}>
                <div style={{ display: "flex", }}>
                    <h4>Details</h4>
                    <div style={{ flexGrow: 1, }} />
                    <RemoveGdtfButton selectedFixtureType={selectedFixtureType} />
                </div>
                <FixtureTypeDetails fixtureType={selectedFixtureType}/>
            </div>
            
        </div>
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

    function selectFile() {
        fileInput.current?.click()
    }

    function uploadFile() {
        const file = fileInput.current?.files?.[0]
        if (file == null) { return }
        const formData = new FormData();
        formData.append("file", file);
        fetch('/api/fixturetype', { method: "POST", body: formData });
        // TODO handle response, especially error responses
        // TODO when uploading GDTF file, deleting it and trying to re-upload it, \
        // the onChange callback isn't triggered and the file isn't uploaded again
        // possible solution: set value of fileInput to empty string here and at beginning of uploadFile check for empty string
    }

    return (
        <Button intent="success" icon="plus" onClick={selectFile}>
            <LabelWithHotkey label="Add GDTF" combo="A" />
            <input type="file" id="gdtfFileInput" ref={fileInput} accept=".gdtf"
                onChange={uploadFile} data-cy="gdtf_hidden_input" hidden />
        </Button>
    )
}


function RemoveGdtfButton(props: {selectedFixtureType: FixtureType | undefined}) {
    const selectedFixtureType = props.selectedFixtureType

    const patchData = useContext(PatchContext);

    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [numberOfFixturesThatWillBeDeleted, setNumberOfFixturesThatWillBeDeleted] = useState<number|undefined>(undefined);

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
            label: "Remove the selected fixtures",
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
                canEscapeKeyClose={true} // TODO escape exits, but also navigates out of Patch 
                // -> navigating out of Patch should be disabled while this alert is open -> how to do this elegantly?
                // also allows interacting with other global hotkeys (like a to add GDTF, i to navigate into fixtures, ...)
                // these interactions should also be blocked...
                canOutsideClickClose={true}
                onClose={handleDialogClose}
                className="bp3-dark" 
            >
                <div className={Classes.DIALOG_BODY}>
                    <RemoveDialogMessage numberOfFixturesThatWillBeDeleted={numberOfFixturesThatWillBeDeleted}/>
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

function RemoveDialogMessage(props: {numberOfFixturesThatWillBeDeleted: number|undefined}) {
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

function FixtureTypeDetails(props: {fixtureType?: FixtureType}) {
    const fixtureType = props.fixtureType
    if (fixtureType === undefined) {
        return <div>Select a Fixture Type to show Details</div>
    }

    return (
        <div>
            <div>
                Manufacturer: {fixtureType.manufacturer}
            </div>
            <div>
                Name: {fixtureType.name}
            </div>
            <div>
                <h5>Modes</h5>
                {fixtureType.modes.map((mode) => {
                    return (
                        <div key={mode.name}>
                            {DmxModeString(mode)}
                        </div>);
                })}
            </div>
        </div>
    )
}