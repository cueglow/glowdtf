import { Button, FormGroup, FormGroupProps, HTMLInputProps, InputGroup, InputGroupProps2, MenuItem, Navbar, NumericInput, NumericInputProps, useHotkeys } from '@blueprintjs/core';
import { Tooltip2 } from '@blueprintjs/popover2';
import { IItemRendererProps, ItemPredicate } from '@blueprintjs/select';
import { Suggest, SuggestProps } from '@blueprintjs/select/lib/esm/components/select/suggest';
import { zodResolver } from '@hookform/resolvers/zod';
import { RouteComponentProps, useNavigate } from '@reach/router';
import React, { useCallback, useContext, useEffect, useMemo } from 'react';
import { Controller, ControllerProps, useForm, useWatch } from 'react-hook-form';
import { ClientMessage } from 'src/ConnectionProvider/ClientMessage';
import { connectionProvider } from 'src/ConnectionProvider/ConnectionProvider';
import { PatchFixture } from 'src/Types/Patch';
import { LabelWithHotkey } from 'src/Utilities/HotkeyHint';
import { v4 as uuidv4 } from 'uuid';
import { z } from 'zod';
import { NavbarExitWithTitle } from '../App/NavbarExitWithTitle';
import { PatchContext } from '../ConnectionProvider/PatchDataProvider';
import { DmxMode, DmxModeString, FixtureType, fixtureTypeString } from '../Types/FixtureTypeUtils';

// TODO clean everything up and split into smaller components if possible

export default function NewFixture(props: RouteComponentProps) {
    const navigate = useNavigate();

    const { register, handleSubmit, setFocus, setValue, trigger: triggerValidation, control, formState: { errors } } = useForm({
        mode: "onTouched",
        reValidateMode: "onChange",
        resolver: zodResolver(newFixtureSchema),
    });

    const selectedFixtureType = useWatch({ control, name: "fixtureType" })
    const selectedDmxMode = useWatch({ control, name: "dmxMode" })

    const { ref: fixtureTypeRef } = register("fixtureType")
    const { ref: dmxModeRef } = register("dmxMode")
    const { ref: nameRef, ...nameRegister } = register("name")

    // focus first field at the start
    useEffect(() => {
        setFocus("fixtureType")
        // Initialize to null so error messages don't mention the default empty string
        setValue("fixtureType", null)
        setValue("dmxMode", null)
    }, [setFocus, setValue]);

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
                fixtureTypeId: data.fixtureType.fixtureTypeId,
                dmxMode: data.dmxMode.name,
                universe: universe,
                address: address,
            })
        }
        const msg = new ClientMessage.AddFixtures(fixtureArray)
        connectionProvider.send(msg)
        navigate("patch")
    }, [navigate])

    const hotkeys = useMemo(() => [
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
    ], [handleSubmit, onSubmit]);
    useHotkeys(hotkeys);
    const patchData = useContext(PatchContext);

    return (
        <div style={{ height: "100%", }}>
            <Navbar>
                <NavbarExitWithTitle title="Add New Fixtures" exitPath="/patch" />
            </Navbar>
            <div style={{
                maxWidth: "25em",
                margin: "auto",
            }}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <ValidatedSuggest
                        label="Fixture Type"
                        errorMessage={errors.fixtureType?.message}
                        isOpen={errors.fixtureType ? true : false}
                        items={patchData.fixtureTypes}
                        inputValueRenderer={fixtureTypeString}
                        itemPredicate={filterFixtureType}
                        onItemSelect={(item) => {
                            setValue("fixtureType", item);
                            setValue("dmxMode", item.modes[0]);
                            setFocus("dmxMode");
                            triggerValidation();
                        }}
                        keyRenderer={(item) => item.fixtureTypeId.toString()}
                        id="addFixture_fixtureTypeInput"
                        inputProps={{
                            inputRef: fixtureTypeRef,
                            tabIndex: 1,
                            onBlur: () => triggerValidation(),
                        }}
                    />
                    <ValidatedSuggest
                        label="DMX Mode"
                        errorMessage={errors.dmxMode?.message}
                        isOpen={errors.dmxMode ? true : false}
                        items={selectedFixtureType?.modes ?? []}
                        inputValueRenderer={DmxModeString}
                        itemPredicate={filterDmxMode}
                        selectedItem={selectedDmxMode}
                        onItemSelect={(item) => {
                            setValue("dmxMode", item)
                            setFocus("name");
                        }}
                        keyRenderer={(item) => item.name}
                        id="addFixture_modeInput"
                        inputProps={{
                            inputRef: dmxModeRef,
                            tabIndex: 2,
                        }}
                    />
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


const maxQuantity = 256

export const artnetMaxUniverse = 32767

export const i32MinValue = -2147483648
export const i32MaxValue = 2147483647

const newFixtureSchema = z.object({
    fixtureType: z.object({}).passthrough(),
    dmxMode: z.object({}).passthrough(),
    name: z.string(),
    quantity: z.number().int().positive().max(maxQuantity),
    fid: z.number().int().min(i32MinValue).max(i32MaxValue),
    universe: z.number().int().min(0).max(artnetMaxUniverse).nullish(),
    address: z.number().int().min(1).max(512).nullish(),
})

function ValidatedSuggest<T>(props: 
    Omit<SuggestProps<T>, "itemRenderer" | "popoverProps" | "resetOnClose" | "noResults" | "inputProps"> & 
    { errorMessage: string, isOpen: boolean , keyRenderer: (item: T) => string, inputProps: Omit<InputGroupProps2, "intent"|"id">, id: string, label: string}
    ) {
    const { errorMessage, isOpen, inputProps, keyRenderer, id, label, ...other } = props;

    // error Message musn't be empty, otherwise Tooltip will unmount itself and its child
    const paddedErrorMessage = errorMessage || "-"

    return <Tooltip2
        /* must provide default content, otherwise will unmount child  */
        content={paddedErrorMessage}
        isOpen={isOpen}
        enforceFocus={false}
        autoFocus={false}
        placement="right"
        intent="danger">
        <FormGroup label={label} labelFor={id}>
            <Suggest
                {...other}

                itemRenderer={defaultSuggestItemRenderer(props.inputValueRenderer, keyRenderer)}
                popoverProps={{ minimal: true, }}
                resetOnClose={true}
                noResults={<MenuItem disabled={true} text="No results." />}
                inputProps={{
                    intent: isOpen ? "danger" : "none",
                    id: id,
                    ...inputProps,
                }}
            />
        </FormGroup>
    </Tooltip2>;
}

const defaultSuggestItemRenderer = <T,>(inputValueRenderer: (item: T) => string, keyRenderer: (item: T) => string) => 
    (item: T, { handleClick, modifiers, query }: IItemRendererProps) => {
        if (!modifiers.matchesPredicate) {
            return null;
        }
        return (
            <MenuItem
                active={modifiers.active}
                disabled={modifiers.disabled}
                onClick={handleClick}
                text={highlightText(inputValueRenderer(item), query)}
                key={keyRenderer(item)} />
        );
    }

function ValidatedNumericInput(props: Omit<ControllerProps, "render"> & HTMLInputProps & NumericInputProps & FormGroupProps) {
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
        }
    />
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

