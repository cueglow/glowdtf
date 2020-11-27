import { AgGridColumn, AgGridReact } from "ag-grid-react";
import React, { useState } from "react";

import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine-dark.css';
import { Button } from "@blueprintjs/core";

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');

export function FixturePatch() {
    const [gridApi, setGridApi] = useState(null);
    const [gridColumnApi, setGridColumnApi] = useState(null);

    const [rowData, setRowData] = useState([
        {
            fid: 1,
            name: "Lamp 1",
            fixtureType: "GLP impression Spot One",
            dmxMode: "Standard (21 ch)",
            universe: 1,
            address: "1"
        },
        {
            fid: 2,
            name: "Lamp 2",
            fixtureType: "GLP impression Spot One",
            dmxMode: "Standard (21 ch)",
            universe: 1,
            address: "22"
        },
    ]);

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
            <div className="ag-theme-alpine-dark" style={{
                flexGrow: 1,
            }}>
                <AgGridReact
                    rowData={rowData}
                    rowSelection="multiple">
                    <AgGridColumn field="fid" headerName="FID"></AgGridColumn>
                    <AgGridColumn field="name"></AgGridColumn>
                    <AgGridColumn field="fixtureType"></AgGridColumn>
                    <AgGridColumn field="dmxMode" headerName="DMX Mode"></AgGridColumn>
                    <AgGridColumn field="universe"></AgGridColumn>
                    <AgGridColumn field="address"></AgGridColumn>
                </AgGridReact>
            </div>
        </div>

    );
}
