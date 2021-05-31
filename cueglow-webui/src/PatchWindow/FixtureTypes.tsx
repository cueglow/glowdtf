import { Button } from "@blueprintjs/core";
import { RouteComponentProps } from "@reach/router";
import React, { useCallback, useContext, useRef, useState } from "react";
import { ReactTabulator } from "react-tabulator";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { DmxModeString, emptyFixtureType, FixtureType } from "../Types/FixtureTypeUtils";

export function FixtureTypes(props: RouteComponentProps) {
    const [selectedFixtureType, setSelectedFixtureType] = useState(emptyFixtureType);
    
    const removeFixtureType = useCallback(() => {
        const msg = new ClientMessage.RemoveFixtureTypes([selectedFixtureType.fixtureTypeId])
        connectionProvider.send(msg)
        setSelectedFixtureType(emptyFixtureType)
        // TODO if patched fixtures were removed in this operation, show toast
        // with how many fixtures were removed and with undo button
    }, [selectedFixtureType])

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
                        rowSelected={(row) => setSelectedFixtureType(row.getData())} />
                </div>
            </div>
            <div style={{ flexGrow: 1, flexBasis: 0, }}>
                <div style={{ display: "flex", }}>
                    <h4>Details</h4>
                    <div style={{ flexGrow: 1, }} />
                    {/* TODO this remove button should probably be moved inline into the table, 
                    but not sure how to do that */}
                    <Button minimal={true} icon="trash"
                        disabled={selectedFixtureType.name === ""} onClick={removeFixtureType}
                        data-cy="remove_selected_fixture_type_button" />
                </div>
                <FixtureTypeDetails fixtureType={selectedFixtureType}/>
            </div>
            
        </div>
    );
}

function FixtureTypeTable(props: { rowSelected: (row: Tabulator.RowComponent) => void }) {
    const patchData = useContext(PatchContext);

    const columns = [
        { field: "manufacturer", title: "Manufacturer" },
        { field: "name", title: "Name" },
    ];

    return (
        <ReactTabulator
            data={patchData.fixtureTypes}
            columns={columns}
            // fitDataStretch: When making window narrow, not all data is visible in column width 
            // and table-internal horizontal scrolling does not activate even though it should
            options={{
                height: "100%", layout: "fitDataStretch", selectable: 1,
                rowSelected: props.rowSelected,
            }} />
    );
}

function AddGdtfButton() {
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
            Add GDTF
            <input type="file" id="gdtfFileInput" ref={fileInput} accept=".gdtf"
                onChange={uploadFile} data-cy="gdtf_hidden_input" hidden />
        </Button>
    )
}

function FixtureTypeDetails(props: {fixtureType: FixtureType}) {
    const fixtureType = props.fixtureType
    if (fixtureType.name === "") {
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