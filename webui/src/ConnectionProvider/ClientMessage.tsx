import { PatchFixture } from "src/Types/Patch"

export enum ClientEvent {
    Subscribe = "subscribe",
    RemoveFixtureTypes = "removeFixtureTypes",
    AddFixtures = "addFixtures",
    RemoveFixtures = "removeFixtures",
    UpdateFixtures = "updateFixtures",
    SetChannel = "setChannel"
}

export enum GlowTopic {
    Patch = "patch",
    RigState = "rigState",
}

// In Updates, uuid must be contained and fixtureTypeId/dmxMode must not be
// contained (currently immutable after creation). 
export type PatchFixtureUpdate =
    Pick<PatchFixture, "uuid"> &
    Partial<Omit<PatchFixture, "fixtureTypeId" | "dmxMode">>

export type SetChannelUpdate = {
    fixtureUuid: string,
    chInd: number,
    value: number,
}

export class ClientMessage {
    private constructor(readonly event: ClientEvent) { }

    static Subscribe = class extends ClientMessage {
        constructor(readonly data: GlowTopic) {
            super(ClientEvent.Subscribe)
        }
    }

    static RemoveFixtureTypes = class extends ClientMessage {
        constructor(readonly data: string[]) {
            super(ClientEvent.RemoveFixtureTypes)
        }
    }

    static AddFixtures = class extends ClientMessage {
        constructor(readonly data: PatchFixture[]) {
            super(ClientEvent.AddFixtures)
        }
    }

    static UpdateFixtures = class extends ClientMessage {
        constructor(readonly data: PatchFixtureUpdate[]) {
            super(ClientEvent.UpdateFixtures)
        }
    }

    static RemoveFixtures = class extends ClientMessage {
        constructor(readonly data: string[]) {
            super(ClientEvent.RemoveFixtures)
        }
    }

    static SetChannel = class extends ClientMessage {
        constructor(readonly data: SetChannelUpdate) {
            super(ClientEvent.SetChannel)
        }
    }
}

