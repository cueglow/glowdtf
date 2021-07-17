import { Alignment, Button, FormGroup, InputGroup, MenuItem, Navbar, NavbarGroup, NavbarHeading, NumericInput, useHotkeys } from '@blueprintjs/core';
import { Tooltip2 } from '@blueprintjs/popover2';
import { ItemPredicate } from '@blueprintjs/select';
import { Suggest } from '@blueprintjs/select/lib/esm/components/select/suggest';
import { zodResolver } from '@hookform/resolvers/zod';
import { RouteComponentProps, useNavigate } from '@reach/router';
import React, { useCallback, useContext, useEffect, useMemo, useRef, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { ClientMessage } from 'src/ConnectionProvider/ClientMessage';
import { connectionProvider } from 'src/ConnectionProvider/ConnectionProvider';
import { PatchFixture } from 'src/Types/Patch';
import { HotkeyHint, LabelWithHotkey } from 'src/Utilities/HotkeyHint';
import { v4 as uuidv4 } from 'uuid';
import { z } from 'zod';
import { PatchContext } from '../ConnectionProvider/PatchDataProvider';
import { DmxMode, DmxModeString, FixtureType, fixtureTypeString } from '../Types/FixtureTypeUtils';

// TODO clean everything up and split into smaller components if possible

const maxQuantity = 256

export const artnetMaxUniverse = 32767

export const i32MinValue = -2147483648
export const i32MaxValue = 2147483647

const newFixtureSchema = z.object({
    fixtureTypeId: z.string().min(1),
    dmxMode: z.string(),
    name: z.string(),
    quantity: z.number().int().positive().max(maxQuantity),
    fid: z.number().int().min(i32MinValue).max(i32MaxValue),
    universe: z.number().int().min(0).max(artnetMaxUniverse).nullish(),
    address: z.number().int().min(1).max(512).nullish(),
})

export default function NewFixture(props: RouteComponentProps) {
    const { register, handleSubmit, setFocus, setValue, getValues, trigger: triggerValidation, control, formState: { errors } } = useForm({
        mode: "onTouched",
        reValidateMode: "onChange",
        resolver: zodResolver(newFixtureSchema),
    });

    console.log("values", getValues())
    console.log(errors)

    const navigate = useNavigate();

    // TODO maybe we can get rid of these now that we use react-hook-form?
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType | undefined>(undefined);
    const [selectedDmxMode, setSelectedDmxMode] = useState<DmxMode | null>(null);

    const onSubmit = useCallback((data) => {
        const name = data.name
        const quantity = data.quantity
        const fid = data.fid // TODO auto-increment and error when i32 boundary exceeded
        const universe = data.universe
        const address = data.address

        const names = generateNames(name, quantity)

        const fixtureArray: PatchFixture[] = []
        for (let i = 0; i < quantity; i++) {
            fixtureArray.push({
                uuid: uuidv4(),
                fid: fid,
                name: names[i],
                fixtureTypeId: selectedFixtureType!.fixtureTypeId,
                dmxMode: data.dmxMode,
                universe: universe,
                address: address,
            })
        }
        const msg = new ClientMessage.AddFixtures(fixtureArray)
        connectionProvider.send(msg)
        navigate("patch")
    }, [navigate, selectedFixtureType])

    const hotkeys = useMemo(() => [
        {
            combo: "esc",
            global: true,
            label: "Go Back to Patch",
            allowInInput: true,
            onKeyDown: () => navigate("patch"),
        },
        {
            combo: "ctrl+enter",
            global: true,
            label: "Submit Form to Add Fixtures",
            allowInInput: true,
            onKeyDown: () => {
                console.log("running hotkey submit")
                handleSubmit(onSubmit)()
            },
        }
    ], [handleSubmit, navigate, onSubmit]);
    useHotkeys(hotkeys);
    const patchData = useContext(PatchContext);

    const {ref: fixtureTypeRef} = register("fixtureTypeId")
    const {ref: dmxModeRef} = register("dmxMode")
    const { ref: nameRef, ...nameRegister } = register("name")

    // focus first field at the start
    useEffect(() => {
        setFocus("fixtureTypeId")
    }, [setFocus]);

    return (
        <div style={{ height: "100%", }}>
            <Navbar>
                <NavbarGroup align={Alignment.LEFT}>
                    <Button icon="cross" minimal={true} onClick={() => navigate("/patch")}>
                        <HotkeyHint combo="Esc" />
                    </Button>
                    <NavbarHeading style={{ paddingLeft: "6vw" }}>
                        <strong>Add New Fixtures</strong>
                    </NavbarHeading>
                </NavbarGroup>
            </Navbar>
            <div style={{
                maxWidth: "25em",
                margin: "auto",
            }}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <Tooltip2
                        /* must provide default content, otherwise will unmount child  */
                        content={errors.fixtureTypeId?.message ?? "-"}
                        isOpen={errors.fixtureTypeId ? true : false}
                        enforceFocus={false}
                        autoFocus={false}
                        placement="right"
                        intent="danger"
                    >
                        <FormGroup label="Fixture Type" labelFor="addFixture_fixtureTypeInput">
                            <Suggest
                                items={patchData.fixtureTypes}
                                itemRenderer={(fixtureType, { handleClick, modifiers, query }) => {
                                    if (!modifiers.matchesPredicate) {
                                        return null;
                                    }
                                    return (
                                        <MenuItem
                                            active={modifiers.active}
                                            disabled={modifiers.disabled}
                                            onClick={handleClick}
                                            text={highlightText(fixtureTypeString(fixtureType), query)}
                                            key={fixtureType.fixtureTypeId.toString()}
                                        />
                                    );
                                }}
                                inputValueRenderer={fixtureTypeString}
                                onItemSelect={(item) => {
                                    setValue("fixtureTypeId", item.fixtureTypeId)
                                    setSelectedFixtureType(item);
                                    setSelectedDmxMode(item.modes[0]);
                                    setValue("dmxMode", item.modes[0].name);
                                    setFocus("dmxMode")
                                    triggerValidation()
                                }}
                                popoverProps={{ minimal: true, }}
                                itemPredicate={filterFixtureType}
                                resetOnClose={true}
                                noResults={<MenuItem disabled={true} text="No results." />}
                                inputProps={{
                                    id: "addFixture_fixtureTypeInput",
                                    inputRef: fixtureTypeRef,
                                    tabIndex: 1,
                                    intent: errors.fixtureTypeId ? "danger" : "none",
                                    onBlur: () => triggerValidation(),
                                }}
                            />
                        </FormGroup>
                    </Tooltip2>
                    <Tooltip2
                        /* must provide default content, otherwise will unmount child  */
                        content={errors.dmxMode?.message ?? "-"}
                        isOpen={errors.dmxMode ? true : false}
                        enforceFocus={false}
                        autoFocus={false}
                        placement="right"
                        intent="danger"
                    >
                        <FormGroup label="DMX Mode" labelFor="addFixture_modeInput">
                            <Suggest
                                items={selectedFixtureType?.modes ?? []}
                                itemRenderer={(mode, { handleClick, modifiers, query }) => {
                                    if (!modifiers.matchesPredicate) {
                                        return null;
                                    }
                                    return (
                                        <MenuItem
                                            active={modifiers.active}
                                            disabled={modifiers.disabled}
                                            onClick={handleClick}
                                            text={highlightText(DmxModeString(mode), query)}
                                            key={DmxModeString(mode)}
                                        />
                                    );
                                }}
                                inputValueRenderer={DmxModeString}
                                selectedItem={selectedDmxMode}
                                onItemSelect={(item) => {
                                    setValue("dmxMode", item.name)
                                    setSelectedDmxMode(item);
                                    setFocus("name");
                                }}
                                popoverProps={{ minimal: true, }}
                                itemPredicate={filterDmxMode}
                                resetOnClose={true}
                                noResults={<MenuItem disabled={true} text="No results." />}
                                inputProps={{
                                    id: "addFixture_modeInput",
                                    inputRef: dmxModeRef,
                                    tabIndex: 2,
                                    intent: errors.dmxMode ? "danger" : "none",
                                }}
                            />
                        </FormGroup>
                    </Tooltip2>
                    <FormGroup label="Name" labelFor="addFixture_nameInput">
                        <InputGroup
                            id="addFixture_nameInput" tabIndex={3}
                            inputRef={nameRef}
                            {...nameRegister}
                        />
                    </FormGroup>
                    <ValidatedNumericInput
                        label="Quantity"
                        name="quantity"
                        defaultValue={1}
                        min={1}
                        max={maxQuantity}
                        tabIndex={4}
                        id="addFixture_quantityInput"
                        control={control}
                    />
                    <ValidatedNumericInput
                        label="FID"
                        name="fid"
                        defaultValue={1}
                        min={i32MinValue}
                        max={i32MaxValue}
                        tabIndex={5}
                        id="addFixture_fidInput"
                        control={control}
                    />
                    <ValidatedNumericInput
                        label="Universe"
                        name="universe"
                        min={0}
                        max={artnetMaxUniverse}
                        tabIndex={6}
                        id="addFixture_universeInput"
                        control={control}
                    />
                    <ValidatedNumericInput
                        label="Address"
                        name="address"
                        min={1}
                        max={512}
                        tabIndex={7}
                        id="addFixture_addressInput"
                        control={control}
                    />
                    <div style={{
                        display: "flex",
                        justifyContent: "flex-end",
                        paddingTop: "1em",
                    }}>
                        {/* Prevent implicit submission of form by hitting Enter */}
                        <Button type="submit" disabled style={{ display: "none" }} />
                        <Button intent="success" type="submit" tabIndex={8}>
                            <LabelWithHotkey label="Add Fixtures" combo={["Ctrl", "Enter"]} />
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    );
}

function ValidatedNumericInput(props: any) {
    return <Controller name={props.name} control={props.control} defaultValue={props.defaultValue}
        render={({ field, fieldState }) =>
            <FormGroup label={props.label} labelFor={props.id}>
                <Tooltip2
                    /* need to provide some content at all times, otherwise Tooltip
                    will unmount and once it re-renders the numeric input will reset
                    to default value  */
                    content={fieldState.error?.message ?? "-"}
                    isOpen={fieldState.invalid}
                    enforceFocus={false}
                    autoFocus={false}
                    placement="right"
                    intent="danger"
                >
                    <NumericInput
                        defaultValue={props.defaultValue}
                        min={props.min} max={props.max}
                        minorStepSize={null}
                        intent={fieldState.invalid ? "danger" : "none"}
                        onValueChange={field.onChange}
                        onBlur={field.onBlur}
                        name={field.name}
                        selectAllOnFocus selectAllOnIncrement
                        id={props.id}
                        tabIndex={props.tabIndex}
                    />
                </Tooltip2>
            </FormGroup>
        } />
}

function highlightText(text: string, query: string) {
    let lastIndex = 0;
    const words = query
        .split(/\s+/)
        .filter(word => word.length > 0)
        .map(escapeRegExpChars);
    if (words.length === 0) {
        return [text];
    }
    const regexp = new RegExp(words.join("|"), "gi");
    const tokens: React.ReactNode[] = [];
    while (true) {
        const match = regexp.exec(text);
        if (!match) {
            break;
        }
        const length = match[0].length;
        const before = text.slice(lastIndex, regexp.lastIndex - length);
        if (before.length > 0) {
            tokens.push(before);
        }
        lastIndex = regexp.lastIndex;
        tokens.push(<strong key={lastIndex}>{match[0]}</strong>);
    }
    const rest = text.slice(lastIndex);
    if (rest.length > 0) {
        tokens.push(rest);
    }
    return tokens;
}

function escapeRegExpChars(text: string) {
    return text.replace(/([.*+?^=!:${}()|[\]/\\])/g, "\\$1");
}

const filterFixtureType: ItemPredicate<FixtureType> =
    (query: string, fixtureType: FixtureType | undefined, _, exactMatch) => {
        const normalizedType = fixtureTypeString(fixtureType).toLowerCase();
        const normalizedQuery = query.toLowerCase();

        if (exactMatch) {
            return normalizedType === normalizedQuery;
        } else {
            return normalizedType.indexOf(normalizedQuery) >= 0;
        }
    };

const filterDmxMode: ItemPredicate<DmxMode> =
    (query, mode, _, exactMatch) => {
        const normalizedItem = DmxModeString(mode).toLowerCase();
        const normalizedQuery = query.toLowerCase();

        if (exactMatch) {
            return normalizedItem === normalizedQuery;
        } else {
            return normalizedItem.indexOf(normalizedQuery) >= 0;
        }
    };

function generateNames(name: string, quantity: number): string[] {
    if (quantity === 1) {
        return [name]
    } else {
        return Array.from({ length: quantity }, (_, index) => `${name} ${index + 1}`)
    }
}