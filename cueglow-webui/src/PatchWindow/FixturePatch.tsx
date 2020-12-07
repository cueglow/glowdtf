
import React, { useContext } from "react";
import { Button } from "@blueprintjs/core";

import 'react-tabulator/lib/styles.css'; // required styles
import 'react-tabulator/lib/css/tabulator_midnight.min.css'; // theme
import { ReactTabulator } from 'react-tabulator'
import { PatchContext } from "../App";
import { RouteComponentProps, useNavigate } from "@reach/router";
import { fixtureTypeString } from "../FixtureType/FixtureTypeUtils";

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');

export function FixturePatch(props: RouteComponentProps) {
    const navigate = useNavigate();
    return (
        <div style={{
            position: "absolute",
            top: bp.global["$pt-navbar-height"].value,
            bottom: "0px",
            width: "100%",
            padding: bp.global["$pt-grid-size"].value,
            display: "flex",
            flexDirection: "column",
        }}>
            <div style={{
                display: "flex",
                justifyContent: "flex-start", //flex-end for right-align
                marginBottom: bp.global["$pt-grid-size"].value,
            }}>
                <Button intent="success" icon="plus"
                    onClick={() => navigate("/patch/newFixture")}>
                    Add New Fixtures</Button>
            </div>
            <div style={{
                flexGrow: 1,
                minHeight: 0,
            }}>
                <PatchTable />
            </div>
        </div>
    );
}

function PatchTable() {
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
            options={{ height: "100%", layout: "fitDataStretch", }}
            />
    );
}

