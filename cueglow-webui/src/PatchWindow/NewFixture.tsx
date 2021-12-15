import { Button, FormGroup, InputGroup, Navbar, useHotkeys } from '@blueprintjs/core';
import { ItemPredicate } from '@blueprintjs/select';
import { zodResolver } from '@hookform/resolvers/zod';
import { RouteComponentProps, useNavigate } from '@reach/router';
import React, { useCallback, useContext, useEffect, useMemo } from 'react';
import { useForm, useWatch } from 'react-hook-form';
import { ClientMessage } from 'src/ConnectionProvider/ClientMessage';
import { connectionProvider } from 'src/ConnectionProvider/ConnectionProvider';
import { PatchFixture } from 'src/Types/Patch';
import { LabelWithHotkey } from 'src/Components/HotkeyHint';
import { v4 as uuidv4 } from 'uuid';
import { z } from 'zod';
import { NavbarExitWithTitle } from '../Components/NavbarExitWithTitle';
import { PatchContext } from '../ConnectionProvider/PatchDataProvider';
import { DmxMode, DmxModeString, FixtureType, fixtureTypeString } from '../Types/FixtureType';
import { ValidatedNumericInput } from './ValidatedNumericInput';
import { ValidatedSuggest } from './ValidatedSuggest';
import _ from 'lodash';

export function NewFixtureWrapper(props: RouteComponentProps) {
    return (
        <div style={{ height: "100%", }}>
            <Navbar>
                <NavbarExitWithTitle title="Add New Fixtures" exitPath="/patch" />
            </Navbar>
            <div style={{
                maxWidth: "25em",
                margin: "auto",
            }}>
                <NewFixtureForm />
            </div>
        </div>
    )
}

export function NewFixtureForm(props: RouteComponentProps) {
    const navigate = useNavigate();

    // react-hook-form
    const {
        register,
        handleSubmit,
        setFocus,
        setValue,
        trigger: triggerValidation,
        control,
        formState: { errors },
    } = useForm({
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

        const names = generateFixtureNames(name, quantity)

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

    const defaultFid = useMemo(() => (_.maxBy(patchData.fixtures, "fid")?.fid ?? 0) + 1, [patchData])
    // if you reload, this will first return 1 because Fixtures are empty when rendering the from
    // later, this will take the right value, but then defaultValue won't be updated anymore

    // this is REALLY simple and stupid
    // e.g. when the channel count of the new fixtures is bigger than one, things might break afterwards
    const defaultAddress = useMemo(() => {
        const highestPatchedFixture = _.maxBy(patchData.fixtures, ["universe", "address"])
        if (highestPatchedFixture) {
            const channelCount = patchData.fixtureTypes
            .find(fixtureType => fixtureType.fixtureTypeId === highestPatchedFixture?.fixtureTypeId)
            ?.modes
            .find(mode => mode.name === highestPatchedFixture?.dmxMode)
            ?.channelCount ?? 1
            const possibleStartAddress = highestPatchedFixture.address + channelCount
            if (possibleStartAddress <= 512) {
                return {universe: highestPatchedFixture.universe, address: possibleStartAddress}
            } else {
                return {universe: highestPatchedFixture.universe+1, address: 1}
            }
        }
        return {universe: 1, address: 1}
    }, [patchData])
    // also doesn't work properly if you reload on the form page


    return (
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
                defaultValue={defaultFid}
                min={i32MinValue}
                max={i32MaxValue}
                tabIndex={5}
                id="addFixture_fidInput"
                control={control}
            />
            <ValidatedNumericInput
                label="Universe"
                name="universe"
                defaultValue={defaultAddress.universe}
                min={0}
                max={artnetMaxUniverse}
                tabIndex={6}
                id="addFixture_universeInput"
                control={control}
            />
            <ValidatedNumericInput
                label="Address"
                name="address"
                defaultValue={defaultAddress.address}
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
    );
}

// Schema for Validating Form

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

// Utility Functions for Form

function generateFixtureNames(name: string, quantity: number): string[] {
    if (quantity === 1) {
        return [name]
    } else {
        return Array.from({ length: quantity }, (_, index) => `${name} ${index + 1}`)
    }
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

