
import React, { useContext, useState } from "react";
import { Button } from "@blueprintjs/core";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { RouteComponentProps, useNavigate } from "@reach/router";
import { fixtureTypeString } from "../Types/FixtureTypeUtils";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { GlowTabulator } from "src/Utilities/GlowTabulator";
import { useMemo } from "react";
import { PatchFixture } from "src/Types/Patch";

export function FixturePatch(props: RouteComponentProps) {
    const navigate = useNavigate();
    const [selectedFixtureUuids, setSelectedFixtureUuids] = useState<string[]>([]);

    function removeFixtures() {
        const msg = new ClientMessage.RemoveFixtures(selectedFixtureUuids);
        connectionProvider.send(msg)
        setSelectedFixtureUuids([])
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
                <div style={{ flexGrow: 1 }} />
                <Button minimal={true} icon="trash"
                    disabled={selectedFixtureUuids.length === 0} onClick={removeFixtures}
                    data-cy="remove_selected_fixture_button" />
            </div>
            <div style={{
                flexGrow: 1,
                minHeight: 0,
            }}>
                <PatchTable rowSelectionChanged={
                    (selectedData) => setSelectedFixtureUuids(
                        selectedData.map((fixture) => fixture.uuid))} />
            </div>
        </div>
    );
}

function PatchTable(props: { rowSelectionChanged: (selectedData: PatchFixture[]) => void }) {
    const patchData = useContext(PatchContext);

    const columns = [
        { field: "fid", title: "FID" },
        { field: "name", title: "Name" },
        { field: "fixtureType", title: "Fixture Type" },
        { field: "dmxMode", title: "DMX Mode" },
        { field: "universe", title: "Universe" },
        { field: "address", title: "Address" },
    ];

    const data = useMemo(() => {
        return patchData.fixtures.map((fixture) => {
            const associatedFixtureType = patchData.fixtureTypes.find(
                fixtureType => fixtureType.fixtureTypeId === fixture.fixtureTypeId
            );
            const associatedFixtureTypeString = fixtureTypeString(associatedFixtureType);
            return { ...fixture, fixtureType: associatedFixtureTypeString }
        });
    }, [patchData])

    return (
        <GlowTabulator
            data={data}
            columns={columns}
            options={{
                height: "100%",
                layout: "fitDataStretch",
                selectable: true,
                selectableRangeMode: "click",
                rowSelectionChanged: props.rowSelectionChanged,
            }}
        />
    );
}

