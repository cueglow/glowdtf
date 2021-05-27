import { PatchFixture } from "src/Types/Patch"

export enum ClientEvent {
    Subscribe = "subscribe",
    RemoveFixtureTypes = "removeFixtureTypes",
    AddFixtures = "addFixtures",
    RemoveFixtures = "removeFixtures",
}

export enum GlowTopic {
    Patch = "patch",
}

export class ClientMessage {
    private constructor(readonly event: ClientEvent) {}

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

    static RemoveFixtures = class extends ClientMessage {
        constructor(readonly data: string[]) {
            super(ClientEvent.RemoveFixtures)
        }
    }
}

