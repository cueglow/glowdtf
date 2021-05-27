import { NIL as uuid_NIL } from "uuid";

export function fixtureTypeString(fixtureType: FixtureType | undefined) {
    return fixtureType?.manufacturer + " " + fixtureType?.name;
}

export function DmxModeString(mode: DmxMode) {
    return mode.name + " (" + mode.channelCount + " ch)";
}


export type FixtureType = {
    fixtureTypeId: string;
    manufacturer: string;
    name: string;
    modes: DmxMode[];
};

export const emptyFixtureType: FixtureType = {
    fixtureTypeId: uuid_NIL,
    manufacturer: "",
    name: "",
    modes: [],
}

export type DmxMode = {
    name: string;
    channelCount: number;
    channelLayout: DmxBreak[];
};

export type DmxBreak = (string | null)[]