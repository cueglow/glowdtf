
import React, { useMemo, useState } from "react";
import { Button } from "@blueprintjs/core";
import { useTable } from "react-table";

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');
export function FixturePatch() {

    const columns = [
        { id: "fid", accessor: (row: { fid: number; }) => row.fid, Header: "FID" },
        { id: "name", accessor: (row: { name: string; }) => row.name, Header: "Name" },
        { id: "fixtureType", accessor: (row: { fixtureType: string; }) => row.fixtureType, Header: "Fixture Type" },
        { id: "dmxMode", accessor: (row: { dmxMode: string; }) => row.dmxMode, Header: "DMX Mode" },
        { id: "universe", accessor: (row: { universe: number; }) => row.universe, Header: "Universe" },
        { id: "address", accessor: (row: { address: number; }) => row.address, Header: "Address" },
    ];
    const data = useMemo(
        () => [
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
        ],
        []
    );
    const tableInstance = useTable({ columns: columns, data: data });
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow
    } = tableInstance;
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
            }}>
                <table {...getTableProps()}>
                    <thead>
                        {// Loop over the header rows
                            headerGroups.map(headerGroup => (
                                // Apply the header row props
                                <tr {...headerGroup.getHeaderGroupProps()}>
                                    {// Loop over the headers in each row
                                        headerGroup.headers.map(column => (
                                            // Apply the header cell props
                                            <th {...column.getHeaderProps()}>
                                                {// Render the header
                                                    column.render('Header')}
                                            </th>
                                        ))}
                                </tr>
                            ))}
                    </thead>
                    {/* Apply the table body props */}
                    <tbody {...getTableBodyProps()}>
                        {// Loop over the table rows
                            rows.map(row => {
                                // Prepare the row for display
                                prepareRow(row)
                                return (
                                    // Apply the row props
                                    <tr {...row.getRowProps()}>
                                        {// Loop over the rows cells
                                            row.cells.map(cell => {
                                                // Apply the cell props
                                                return (
                                                    <td {...cell.getCellProps()}>
                                                        {// Render the cell contents
                                                            cell.render('Cell')}
                                                    </td>
                                                )
                                            })}
                                    </tr>
                                )
                            })}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
