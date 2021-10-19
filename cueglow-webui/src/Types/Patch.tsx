import { FixtureType } from "./FixtureType"

export type PatchData = {
    fixtures: PatchFixture[];
    fixtureTypes: FixtureType[];
}

export type PatchFixture = {
    uuid: string;
    fid: number;
    name: string;
    fixtureTypeId: string;
    dmxMode: string;
    universe: number;
    address: number;
}

