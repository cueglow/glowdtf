import { Button } from "@blueprintjs/core";
import { RouteComponentProps } from "@reach/router";
import React, { useContext, useState } from "react";
import { ReactTabulator } from "react-tabulator";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { PatchContext } from "../App/App";
import { DmxModeString } from "../FixtureType/FixtureTypeUtils";

export function FixtureTypes(props: RouteComponentProps) {
    const [detailState, setDetailState] = useState({
        name: "",
        manufacturer: "",
        modes: [] as {name: string, channelCount: number}[],
    });

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
                    <Button intent="success" icon="plus">Add GDTF</Button>
                </div>
                <div style={{
                    flexGrow: 1,
                    minHeight: 0,
                }}>
                    <FixtureTypeTable
                        rowSelected={(row: any) => {
                            console.log(row);
                            setDetailState(row._row.data)
                        }} />
                </div>
            </div>
            <div style={{ flexGrow: 1, flexBasis: 0, }}>
                <h4>Details</h4>
                <div>
                    Manufacturer: {detailState.manufacturer}
                </div>
                <div>
                    Name: {detailState.name}
                </div>
                <div>
                    <h5>Modes</h5>
                    {detailState.modes.map((mode) => {
                        return (
                            <div key={mode.name}>
                                {DmxModeString(mode)}
                            </div>);
                    })}
                </div>
            </div>

        </div>
    );
}

function FixtureTypeTable(props: { rowSelected: any; }) {
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