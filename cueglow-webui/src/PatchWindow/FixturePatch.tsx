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
        { make: "Toyota", model: "Celica", price: 35000 },
        { make: "Ford", model: "Mondeo", price: 32000 },
        { make: "Porsche", model: "Boxter", price: 72000 }
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
                justifyContent: "flex-end",
                marginBottom: bp.global["$pt-grid-size"].value,
            }}>
                <Button intent="success" icon="plus">Add New Fixtures</Button>
            </div>
            <div className="ag-theme-alpine-dark" style={{
                flexGrow: 1,
            }}>
                <AgGridReact
                    rowData={rowData}>
                    <AgGridColumn field="make"></AgGridColumn>
                    <AgGridColumn field="model"></AgGridColumn>
                    <AgGridColumn field="price"></AgGridColumn>
                </AgGridReact>
            </div>
        </div>

    );
}
