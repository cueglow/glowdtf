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
    channelFunctions: ChannelFunction[];
    multiByteChannels: MultiByteChannel[];
    //TODO channelFunctionDependencies: ?;
};

export type DmxBreak = (string | null)[]

export type ChannelFunction = {
    name: string;
    dmxFrom: number;
    dmxTo: number;
    multiByteChannelInd: number;
    attribute: string;
}

export type MultiByteChannel = {
    name: string;
    bytes: number;
    channelFunctionIndices: number[];
    geometry: string;
}

// Utilities

export function fixtureTypeString(fixtureType: FixtureType | undefined) {
    return fixtureType?.manufacturer + " " + fixtureType?.name;
}

export function DmxModeString(mode: DmxMode) {
    return mode.name + " (" + mode.channelCount + " ch)";
}