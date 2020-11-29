import { Alignment, Button, FormGroup, InputGroup, MenuItem, Navbar, NavbarGroup, NavbarHeading, NumericInput } from '@blueprintjs/core';
import { ItemPredicate } from '@blueprintjs/select';
import { Suggest } from '@blueprintjs/select/lib/esm/components/select/suggest';
import { RouteComponentProps, useNavigate } from '@reach/router';
import React, { useContext, useState } from 'react';
import { useHotkeys } from 'react-hotkeys-hook';
import { getAutomaticTypeDirectiveNames } from 'typescript';
import { PatchContext } from '..';
import { DmxMode, DmxModeString, FixtureType, fixtureTypeString } from '../FixtureType/FixtureTypeUtils';

export default function NewFixture(props: RouteComponentProps) {
    const navigate = useNavigate();
    useHotkeys('esc', () => { navigate("/patch"); });
    const patchData = useContext(PatchContext);
    const [selectedFixtureType, setSelectedFixtureType] = useState<FixtureType | { modes: [] }>({ modes: [] });

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
                                />
                            );
                        }}
                        inputValueRenderer={DmxModeString}
                        onItemSelect={() => { }}
                        popoverProps={{ minimal: true, }}
                        itemPredicate={filterDmxMode}
                        resetOnClose={true}
                        noResults={<MenuItem disabled={true} text="No results." />}
                    />
                </FormGroup>
                <FormGroup label="Name">
                    <InputGroup />
                </FormGroup>
                {/* TODO disallow floating point numbers and out of bound numbers by parsing step after enter or un-focus -> see docs or examples*/}
                <FormGroup label="Quantity">
                    <NumericInput defaultValue={1} min={1} minorStepSize={null} selectAllOnFocus selectAllOnIncrement />
                </FormGroup>
                <FormGroup label="FID">
                    <NumericInput min={0} minorStepSize={null} selectAllOnFocus selectAllOnIncrement />
                </FormGroup>
                <FormGroup label="Universe">
                    <NumericInput min={0} max={32767} minorStepSize={null} selectAllOnFocus selectAllOnIncrement />
                </FormGroup>
                <FormGroup label="Address">
                    <NumericInput min={1} max={512} minorStepSize={null} selectAllOnFocus selectAllOnIncrement />
                </FormGroup>
                <div style={{
                    display: "flex",
                    justifyContent: "flex-end",
                    paddingTop: "1em",
                }}>
                    <Button intent="success">
                        Add Fixtures
                    </Button>
                </div>
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
    return text.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
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