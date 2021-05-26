export enum ClientEvent {
    Subscribe = "subscribe",
    RemoveFixtureTypes = "removeFixtureTypes",
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
}

