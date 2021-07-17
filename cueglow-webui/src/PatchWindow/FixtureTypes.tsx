import { Button, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps } from "@reach/router";
import React, { useCallback, useContext, useMemo, useRef, useState } from "react";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { GlowTabulator } from "src/Components/GlowTabulator";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { DmxModeString, FixtureType } from "../Types/FixtureType";

export function FixtureTypes(props: RouteComponentProps) {
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType|undefined>(undefined);
    
    const removeFixtureType = useCallback(() => {
        if (selectedFixtureType !== undefined) {
            const msg = new ClientMessage.RemoveFixtureTypes([selectedFixtureType.fixtureTypeId])
            connectionProvider.send(msg)
            // TODO once we have undo/redo
            // if patched fixtures were removed in this operation, show toast
            // with how many fixtures were removed and with undo button
        }
    }, [selectedFixtureType])

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

    return (
        <div style={{
            position: "absolute",
            top: bpVariables.ptNavbarHeight,
            bottom: "0px",
            width: "100%",
            padding: bpVariables.ptGridSize,
            display: "flex",
            flexDirection: "row",
        }}>
            <div style={{
                // as flex-item
                minWidth: 0,
                flexBasis: 0,
                flexGrow: 1,
                paddingRight: bpVariables.ptGridSize,
                // as flex-container
                display: "flex",
                flexDirection: "column",
            }}>
                <div style={{
                    marginBottom: bpVariables.ptGridSize,
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
                    <Button minimal={true} icon="trash"
                        disabled={selectedFixtureType === undefined} onClick={removeFixtureType}
                        data-cy="remove_selected_fixture_type_button" >
                        <LabelWithHotkey label="Remove" combo="Del" />
                    </Button>
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
    }

    return (
        <Button intent="success" icon="plus" onClick={selectFile}>
            <LabelWithHotkey label="Add GDTF" combo="A" />
            <input type="file" id="gdtfFileInput" ref={fileInput} accept=".gdtf"
                onChange={uploadFile} data-cy="gdtf_hidden_input" hidden />
        </Button>
    )
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