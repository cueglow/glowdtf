export type FixtureType = {
    fixtureTypeId: string;
    manufacturer: string;
    name: string;
    modes: DmxMode[];
};

export type DmxMode = {
    name: string;
    channelCount: number;
    channelLayout: DmxBreak[];
};

export type DmxBreak = (string | null)[]

// Utilities

export function fixtureTypeString(fixtureType: FixtureType | undefined) {
    return fixtureType?.manufacturer + " " + fixtureType?.name;
}

export function DmxModeString(mode: DmxMode) {
    return mode.name + " (" + mode.channelCount + " ch)";
}