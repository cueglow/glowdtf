export type RigState = {
    [fixtureUuid: string]: FixtureState
}

export type FixtureState = {
    chValues: number[],
    chFDisabled: (string | null)[],
}