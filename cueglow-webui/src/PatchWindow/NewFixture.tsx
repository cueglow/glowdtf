import { Alignment, Button, FormGroup, InputGroup, MenuItem, Navbar, NavbarGroup, NavbarHeading, NumericInput, useHotkeys } from '@blueprintjs/core';
import { ItemPredicate } from '@blueprintjs/select';
import { Suggest } from '@blueprintjs/select/lib/esm/components/select/suggest';
import { RouteComponentProps, useNavigate } from '@reach/router';
import React, { FormEvent, useContext, useMemo, useRef, useState } from 'react';
import { ClientMessage } from 'src/ConnectionProvider/ClientMessage';
import { connectionProvider } from 'src/ConnectionProvider/ConnectionProvider';
import { PatchFixture } from 'src/Types/Patch';
import { v4 as uuidv4 } from 'uuid';
import { PatchContext } from '../ConnectionProvider/PatchDataProvider';
import { DmxMode, DmxModeString, emptyFixtureType, FixtureType, fixtureTypeString } from '../Types/FixtureTypeUtils';

export default function NewFixture(props: RouteComponentProps) {
    const navigate = useNavigate();
    const hotkeys = useMemo(() => [
        {
            combo: "esc",
            global: true,
            label: "Go Back to Patch",
            onKeyDown: () => navigate("patch"),
        }
    ], [navigate]);
    useHotkeys(hotkeys);
    const patchData = useContext(PatchContext);
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType>(emptyFixtureType);

    const dmxModeInput = useRef<Suggest<DmxMode>>(null);
    const nameInput = useRef<HTMLInputElement>(null);
    const quantityInput = useRef<HTMLInputElement>(null);
    const fidInput = useRef<HTMLInputElement>(null);
    const universeInput = useRef<HTMLInputElement>(null);
    const addressInput = useRef<HTMLInputElement>(null);

    function handleSubmit(event: FormEvent) {
        event.preventDefault()
        const mode = (dmxModeInput.current?.props.selectedItem ?? selectedFixtureType.modes[0]).name
        const name = nameInput.current?.value ?? "no name"
        const quantity = parseInt(quantityInput.current?.value as string)
        const fid = parseInt(fidInput.current?.value as string)
        const universe = parseInt(universeInput.current?.value as string)
        const address = parseInt(addressInput.current?.value as string)

        const fixtureArray: PatchFixture[] = []
        for (let i = 0; i < quantity; i++) {
            fixtureArray.push({
                uuid: uuidv4(),
                fid: fid,
                name: name,
                fixtureTypeId: selectedFixtureType.fixtureTypeId,
                dmxMode: mode,
                universe: universe,
                address: address,
            })
        }
        const msg = new ClientMessage.AddFixtures(fixtureArray)
        connectionProvider.send(msg)
        navigate("patch")
    }

    return (
        <div style={{ height: "100%", }}>
            <Navbar>
                <NavbarGroup align={Alignment.LEFT}>
                    <Button text={<kbd>Esc</kbd>}
                        icon="cross" minimal={true} onClick={() => navigate("/patch")} />
                    <NavbarHeading style={{ paddingLeft: "6vw" }}>
                        <strong>Add New Fixtures</strong>
                    </NavbarHeading>
                </NavbarGroup>
            </Navbar>
            <div style={{
                maxWidth: "25em",
                margin: "auto",
            }}>
                <form onSubmit={handleSubmit}>
                    {/* TODO missing default handling for all fields */}
                    <FormGroup label="Fixture Type">
                        {/* TODO */}
                        {/* should we really use this one from blueprint.js? It is barely documented...
                    use select2 or selectize.js instead? */}
                        {/* Little Triangle at end of input to indicate selection style behaviour? */}
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
                            onItemSelect={(item) => { setSelectedFixtureType(item); }}
                            popoverProps={{ minimal: true, }}
                            itemPredicate={filterFixtureType}
                            resetOnClose={true}
                            noResults={<MenuItem disabled={true} text="No results." />} />
                    </FormGroup>
                    <FormGroup label="DMX Mode">
                        <Suggest
                            // TODO does not reset current selection when changing fixture type
                            // works when opening dialog again though
                            items={selectedFixtureType?.modes}
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
                            onItemSelect={() => { }}
                            popoverProps={{ minimal: true, }}
                            itemPredicate={filterDmxMode}
                            resetOnClose={true}
                            noResults={<MenuItem disabled={true} text="No results." />}
                            ref = {dmxModeInput}
                        />
                    </FormGroup>
                    <FormGroup label="Name">
                        <InputGroup  inputRef={nameInput}/>
                    </FormGroup>
                    {/* TODO disallow floating point numbers and out of bound numbers by parsing step after enter or un-focus -> see docs or examples*/}
                    <FormGroup label="Quantity">
                        <NumericInput defaultValue={1} min={1} minorStepSize={null} 
                            inputRef={quantityInput} selectAllOnFocus selectAllOnIncrement />
                    </FormGroup>
                    <FormGroup label="FID">
                        <NumericInput min={0} minorStepSize={null} selectAllOnFocus selectAllOnIncrement inputRef={fidInput}/>
                    </FormGroup>
                    <FormGroup label="Universe">
                        <NumericInput min={0} max={32767} minorStepSize={null} selectAllOnFocus selectAllOnIncrement inputRef={universeInput}/>
                    </FormGroup>
                    <FormGroup label="Address">
                        <NumericInput min={1} max={512} minorStepSize={null} selectAllOnFocus selectAllOnIncrement inputRef={addressInput}/>
                    </FormGroup>
                    <div style={{
                        display: "flex",
                        justifyContent: "flex-end",
                        paddingTop: "1em",
                    }}>
                        <Button intent="success" type="submit">
                            Add Fixtures
                    </Button>
                    </div>
                </form>
            </div>

        </div>
    );
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