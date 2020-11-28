
import React from "react";
import { Button } from "@blueprintjs/core";

import 'react-tabulator/lib/styles.css'; // required styles
import 'react-tabulator/lib/css/tabulator_midnight.min.css'; // theme
import { ReactTabulator } from 'react-tabulator'

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');
export function FixturePatch() {

    const columns = [
        { field: "fid", title: "FID" },
        { field: "name", title: "Name" },
        { field: "fixtureType", title: "Fixture Type" },
        { field: "dmxMode", title: "DMX Mode" },
        { field: "universe", title: "Universe" },
        { field: "address", title: "Address" },
    ];
    const data = [
        {
            fid: 1,
            name: "Lamp 1",
            fixtureType: "GLP impression Spot One",
            dmxMode: "Standard (21 ch)",
            universe: 1,
            address: 1,
        },
        {
            fid: 2,
            name: "Lamp 2",
            fixtureType: "GLP impression Spot One",
            dmxMode: "Standard (21 ch)",
            universe: 1,
            address: 22,
        },
        {
            fid: 10,
            name: "Front L",
            fixtureType: "Dimmer",
            dmxMode: "8-bit (1 ch)",
            universe: 1,
            address: 43,
        },
        {
            fid: 11,
            name: "Front R",
            fixtureType: "Dimmer",
            dmxMode: "8-bit (1 ch)",
            universe: 1,
            address: 44,
        },
        {
            fid: 12,
            name: "Bühne vorne",
            fixtureType: "Dimmer",
            dmxMode: "8-bit (1 ch)",
            universe: 1,
            address: 45,
        },
        {
            fid: 12,
            name: "Bühne mitte",
            fixtureType: "Dimmer",
            dmxMode: "8-bit (1 ch)",
            universe: 1,
            address: 46,
        },
        {
            fid: 13,
            name: "Bühne hinten",
            fixtureType: "Dimmer",
            dmxMode: "8-bit (1 ch)",
            universe: 1,
            address: 47,
        },
    ];


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
                <Button intent="success" icon="plus">Add New Fixtures</Button>
            </div>
            <div style={{
                flexGrow: 1,
                minHeight: 0,
            }}>
                <ReactTabulator
                    data={data}
                    columns={columns}
                    options={{height: "100%",}}
                />
            </div>
        </div>
    );
}
