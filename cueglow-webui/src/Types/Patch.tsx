import { FixtureType } from "./FixtureTypeUtils"

export type PatchData = {
    fixtures: PatchFixture[];
    fixtureTypes: FixtureType[];
}

export type PatchFixture = {
    uuid: Uint8Array;
    fid: number;
    name: string;
    fixtureTypeId: Uint8Array;
    dmxMode: string;
    universe: number;
    address: number;
}

