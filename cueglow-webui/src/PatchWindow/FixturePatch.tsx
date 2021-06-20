
import { Button, Toaster, useHotkeys } from "@blueprintjs/core";
import { RouteComponentProps, useNavigate } from "@reach/router";
import React, { useCallback, useContext, useMemo, useRef, useState } from "react";
import { bpVariables } from "src/BlueprintVariables/BlueprintVariables";
import { ClientMessage } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchFixture } from "src/Types/Patch";
import { GlowTabulator } from "src/Utilities/GlowTabulator";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { fixtureTypeString } from "../Types/FixtureTypeUtils";

export function FixturePatch(props: RouteComponentProps) {
    const navigate = useNavigate();
    const [selectedFixtureUuids, setSelectedFixtureUuids] = useState<string[]>([]);

    const removeSelectedFixtures = useCallback(() => {
        const msg = new ClientMessage.RemoveFixtures(selectedFixtureUuids);
        connectionProvider.send(msg)
        setSelectedFixtureUuids([])
    }, [selectedFixtureUuids])

    const hotkeys = useMemo(() => [
        {
            combo: "del",
            global: true,
            label: "Remove the selected fixtures",
            onKeyDown: () => removeSelectedFixtures(),
            disabled: selectedFixtureUuids.length === 0,
        }
    ], [removeSelectedFixtures, selectedFixtureUuids]);
    useHotkeys(hotkeys);

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
                    disabled={selectedFixtureUuids.length === 0} onClick={removeSelectedFixtures}
                    data-cy="remove_selected_fixture_button">
                    Remove <kbd>Del</kbd>
                </Button>
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
    const validationFailToaster = useRef<Toaster>(null);

    const columns = useMemo(() => [
        {
            field: "fid", title: "FID",
            editor: "number" as Tabulator.Editor,
            editorParams: {
                min: -2147483648,
                max: 2147483647,
            },
            validator: [
                "integer",
                "min:-2147483648",
                "max:2147483647",
            ],
        },
        { field: "name", title: "Name", editor: "input" as Tabulator.Editor },
        { field: "fixtureType", title: "Fixture Type" },
        { field: "dmxMode", title: "DMX Mode" },
        { 
            field: "universe", title: "Universe", 
            editor: "number" as Tabulator.Editor,
            editorParams: {
                min: 0,
                max: 32767,
            },
            validator: [
                "integer",
                "min:0",
                "max:32767",
            ]
        },
        { 
            field: "address", title: "Address",
            editor: "number" as Tabulator.Editor,
            editorParams: {
                min: 1,
                max: 512,
            },
            validator: [
                "integer",
                "min:1",
                "max:512",
            ]
        },
    ], []);

    const data = useMemo(() => {
        return patchData.fixtures.map((fixture) => {
            const associatedFixtureType = patchData.fixtureTypes.find(
                fixtureType => fixtureType.fixtureTypeId === fixture.fixtureTypeId
            );
            const associatedFixtureTypeString = fixtureTypeString(associatedFixtureType);
            return { ...fixture, fixtureType: associatedFixtureTypeString }
        });
    }, [patchData])

    const showValidationFailPopover = useCallback((
        cell: Tabulator.CellComponent,
        value: unknown,
        validators: unknown,
    ) => {
        const errorMessages = (validators as { type: string, parameters: unknown }[])
            .map((validator) => {
                if (validator.type === "integer") {
                    return "Value must be an integer."
                } else if (validator.type === "max") {
                    return `Value must not be bigger than ${validator.parameters}.`
                } else if (validator.type === "min") {
                    return `Value must not be smaller than ${validator.parameters}`
                } else {
                    return "Value is invalid."
                }
            })
        const errorMessage = errorMessages.join("\n");

        validationFailToaster.current?.clear()

        validationFailToaster.current?.show({ intent: "danger", message: errorMessage })
    }, []);

    const cellEdited = useCallback((cell: Tabulator.CellComponent) => {
        validationFailToaster.current?.clear()

        // TODO send update to server
    }, [])

    return (
        <>
            <GlowTabulator
                data={data}
                columns={columns}
                options={{
                    height: "100%",
                    layout: "fitDataStretch",
                    selectable: true,
                    selectableRangeMode: "click",
                    rowSelectionChanged: props.rowSelectionChanged,
                    validationFailed: showValidationFailPopover,
                    cellEdited: cellEdited,
                    cellEditCancelled: () => validationFailToaster.current?.clear(),
                }}
            />
            <Toaster ref={validationFailToaster} />
        </>
    );
}