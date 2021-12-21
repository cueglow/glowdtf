export type FixtureType = {
    fixtureTypeId: string;
    manufacturer: string;
    name: string;
    modes: DmxMode[];
    shortName: string;
    longName: string;
    description: string;
    refFT: string|null;
    revisions: Revision[];
};

export type DmxMode = {
    name: string;
    channelCount: number;
    channelLayout: DmxBreak[];
    channelFunctions: ChannelFunction[];
    multiByteChannels: MultiByteChannel[];
    channelFunctionDependencies: {
        nodes: { id: string }[];
        edges: { 
            source: string;
            target: string;
            modeFromClipped: number;
            modeToClipped: number;
         }[];
    };
};

export type DmxBreak = (string | null)[]

export type ChannelFunction = {
    name: string;
    dmxFrom: number;
    dmxTo: number;
    multiByteChannelInd: number;
    attribute: string;
    featureGroup: string;
}

export type MultiByteChannel = {
    name: string;
    bytes: number;
    channelFunctionIndices: number[];
    geometry: string;
    dmxBreak: number;
    offsets: number[];
}

export type Revision = {
    date: string;
    text: string;
    userId: number;
}

// Utilities

export function fixtureTypeString(fixtureType: FixtureType | undefined) {
    return fixtureType?.manufacturer + " " + fixtureType?.name;
}

export function DmxModeString(mode: DmxMode) {
    return mode.name + " (" + mode.channelCount + " ch)";
}