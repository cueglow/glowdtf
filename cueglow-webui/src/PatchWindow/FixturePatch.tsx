
import { Button, Callout, Toaster, useHotkeys } from "@blueprintjs/core";
import { NavigateFn, RouteComponentProps, useNavigate } from "@reach/router";
import React, { useCallback, useContext, useMemo, useRef, useState } from "react";
import { bp } from "src/BlueprintVariables/BlueprintVariables";
import { GlowTabulator } from "src/Components/GlowTabulator";
import { LabelWithHotkey } from "src/Components/HotkeyHint";
import { ClientMessage, PatchFixtureUpdate } from "src/ConnectionProvider/ClientMessage";
import { connectionProvider } from "src/ConnectionProvider/ConnectionProvider";
import { PatchFixture } from "src/Types/Patch";
import { PatchContext } from "../ConnectionProvider/PatchDataProvider";
import { fixtureTypeString } from "../Types/FixtureType";
import { } from 'styled-components/macro';
import _ from "lodash";

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
            onKeyDown: () => document.body.classList.contains("bp3-overlay-open") || removeSelectedFixtures(),
            disabled: selectedFixtureUuids.length === 0,
        },
        {
            combo: "a",
            global: true,
            label: "Add New Fixtures",
            onKeyDown: () => document.body.classList.contains("bp3-overlay-open") || navigate("/patch/newFixture"),
        }
    ], [removeSelectedFixtures, selectedFixtureUuids, navigate]);
    useHotkeys(hotkeys);

    return (
        <div style={{
            position: "absolute",
            top: bp.vars.ptNavbarHeight,
            bottom: "0px",
            width: "100%",
            padding: bp.vars.ptGridSize,
            display: "flex",
            flexDirection: "column",
        }}>
            <ValidationMessage />
            <TopButtons 
            navigate={navigate} 
            selectedFixtureUuids={selectedFixtureUuids} 
            removeSelectedFixtures={removeSelectedFixtures} 
            />
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

function ValidationMessage() {
    const patchData = useContext(PatchContext);

    const errorMessage = useMemo(() => {
        // quickly validate Patch
        const sortedFixtures = _.sortBy(patchData.fixtures, ["universe", "address"])

        for (let i =0; i < sortedFixtures.length; i++) {
            const fixture = sortedFixtures[i]

            const channelCount = patchData.fixtureTypes
            .find(fixtureType => fixtureType.fixtureTypeId === fixture?.fixtureTypeId)
            ?.modes
            .find(mode => mode.name === fixture?.dmxMode)
            ?.channelCount ?? 1

            const endAddress = fixture.address + channelCount - 1

            if (endAddress > 512) {
                return `Fixture ${fixture.name} at start address ${fixture.universe}.${fixture.address} ` +
                `ends at address ${fixture.universe}.${endAddress}, which is outside the DMX universe. The ` +
                `Art-Net output will be truncated. `
            }

            if (i >= sortedFixtures.length - 1) { return }

            const nextFixture = sortedFixtures[i+1]

            if (nextFixture.universe === fixture.universe && endAddress >= nextFixture.address) {
                return `Fixture ${fixture.name} at start address ${fixture.universe}.${fixture.address} ` +
                `ends at address ${fixture.universe}.${endAddress} and overlaps with fixture ${nextFixture.name} ` +
                `at start address ${nextFixture.universe}.${nextFixture.address}. Overlapping channels are controlled by only ` +
                `one of the fixtures.`
            }
        }
    }, [patchData])

    return errorMessage ? <Callout intent="danger" title="Error Found in Patch" css={`margin-bottom: ${1*bp.ptGridSizePx}px;`}>
        {errorMessage}
    </Callout> : <></>
}

function TopButtons(props: {
    navigate: NavigateFn,
    selectedFixtureUuids: string[],
    removeSelectedFixtures: () => void
}) {
    return <div style={{
        display: "flex",
        justifyContent: "flex-start", //flex-end for right-align
        marginBottom: bp.vars.ptGridSize,
    }}>
        <Button intent="success" icon="plus"
            onClick={() => props.navigate("/patch/newFixture")}>
            <LabelWithHotkey label="Add New Fixtures" combo="A" />
        </Button>
        <div style={{ flexGrow: 1 }} />
        <Button minimal={true} icon="trash"
            disabled={props.selectedFixtureUuids.length === 0} onClick={props.removeSelectedFixtures}
            data-cy="remove_selected_fixture_button">
            <LabelWithHotkey label="Remove" combo="Del" />
        </Button>
    </div>;
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
                "required"
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
                "required",
                "integer",
                "min:0",
                "max:32767",
            ],
            formatter: displayNegativeAddressAsEmptyString
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
            ],
            mutator: translateBetweenNegativeAddressAndEmptyString
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
        _validators: unknown,
    ) => {
        const validators = _validators as TabulatorValidator[]
        const errorMessage = errorMessageFromValidators(validators);

        validationFailToaster.current?.clear()
        validationFailToaster.current?.show({ intent: "danger", message: errorMessage })
    }, []);

    const cellEdited = useCallback((cell: Tabulator.CellComponent) => {
        // validation failed toasts are now obsolete
        validationFailToaster.current?.clear()

        const newData = cell.getData() as PatchFixtureUpdate
        const changedField = cell.getField() as keyof PatchFixtureUpdate
        var newValue = newData[changedField]
        const patchFixtureUpdate: PatchFixtureUpdate = {
            uuid: newData.uuid,
            [changedField]: newValue
        }
        // send update to server
        const msg = new ClientMessage.UpdateFixtures([patchFixtureUpdate])
        connectionProvider.send(msg)
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
                    keybindings: {
                        navPrev: "shift+9",
                        navUp: "38",
                        navDown: "40",
                    },
                }}
            />
            <Toaster ref={validationFailToaster} />
        </>
    );
}

function displayNegativeAddressAsEmptyString(cell: Tabulator.CellComponent, formatterParams: {}, onRendered: Tabulator.EmptyCallback): string | HTMLElement {
    const val = cell.getValue()
    if (val === -1) {
        return ""
    }
    return val
}

function translateBetweenNegativeAddressAndEmptyString(value: number | string): string | number {
    // coming from server
    if (value === -1) {
        return "" // to user
    }

    // coming from user
    if (value === "") {
        return -1 // to server
    }

    // default case
    return value
}

type TabulatorValidator = {
    type: string;
    parameters: unknown;
}

function errorMessageFromValidators(validators: TabulatorValidator[]) {
    return validators
        .map((validator) => {
            if (validator.type === "integer") {
                return "Value must be an integer.";
            } else if (validator.type === "max") {
                return `Value must not be bigger than ${validator.parameters}.`;
            } else if (validator.type === "min") {
                return `Value must not be smaller than ${validator.parameters}`;
            } else if (validator.type === "required") {
                return `Value must not be empty.`;
            } else {
                return "Value is invalid.";
            }
        })
        .join("\n")
}
