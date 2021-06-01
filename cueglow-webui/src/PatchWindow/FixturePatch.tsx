
import React, { useContext, useState } from "react";
import { Button } from "@blueprintjs/core";
import { ReactTabulator } from 'react-tabulator'
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { RouteComponentProps, useNavigate } from "@reach/router";
import { fixtureTypeString } from "../Types/FixtureTypeUtils";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";

export function FixturePatch(props: RouteComponentProps) {
    const navigate = useNavigate();
    const [selectedFixtureUuid, setSelectedFixtureUuid] = useState<string>("");

    function removeFixtures() {
        const msg = new ClientMessage.RemoveFixtures([selectedFixtureUuid]);
        connectionProvider.send(msg)
        setSelectedFixtureUuid("")
    }

    return (
        <div style={{
            position: "absolute",
            top: bpVariables.ptNavbarHeight,
            bottom: "0px",
            width: "100%",
            padding: bpVariables.ptGridSize,
            display: "flex",
            flexDirection: "column",
        }}>
            <div style={{
                display: "flex",
                justifyContent: "flex-start", //flex-end for right-align
                marginBottom: bpVariables.ptGridSize,
            }}>
                <Button intent="success" icon="plus"
                    onClick={() => navigate("/patch/newFixture")}>
                    Add New Fixtures</Button>
                <div style={{flexGrow: 1}} />
                {/* TODO this remove button should probably be moved inline into the table, 
                but not sure how to do that */}
                <Button minimal={true} icon="trash" 
                    disabled={selectedFixtureUuid === ""} onClick={removeFixtures}
                    data-cy="remove_selected_fixture_button"/>
            </div>
            <div style={{
                flexGrow: 1,
                minHeight: 0,
            }}>
                <PatchTable onRowSelect={(row) => setSelectedFixtureUuid(row.getData().uuid)}/>
            </div>
        </div>
    );
}

function PatchTable(props: {onRowSelect: (row: Tabulator.RowComponent) => void}) {
    const patchData = useContext(PatchContext);

    const columns = [
        { field: "fid", title: "FID" },
        { field: "name", title: "Name" },
        { field: "fixtureType", title: "Fixture Type" },
        { field: "dmxMode", title: "DMX Mode" },
        { field: "universe", title: "Universe" },
        { field: "address", title: "Address" },
    ];

    const data = patchData.fixtures.map((fixture) => {
        const associatedFixtureType = patchData.fixtureTypes.find(fixtureType => fixtureType.fixtureTypeId === fixture.fixtureTypeId);
        const associatedFixtureTypeString = fixtureTypeString(associatedFixtureType);
        return { ...fixture, fixtureType: associatedFixtureTypeString }
    });

    return (
        <ReactTabulator
            data={data}
            columns={columns}
            options={{ 
                height: "100%", 
                layout: "fitDataStretch", 
                selectable: 1,
                rowSelected: props.onRowSelect,
            }}
            />
    );
}

