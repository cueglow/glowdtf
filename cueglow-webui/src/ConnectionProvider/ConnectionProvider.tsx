
// TODO this module should provide automatic reconnection in the future
// see https://github.com/cueglow/cueglow/issues/52

import { ClientMessage } from "./ClientMessage";
import { MessageHandler } from "./MessageHandler";
import { SubscriptionProvider } from "./SubscriptionProvider";

export enum ConnectionState {
    Connecting,
    Open,
    Closed,
}

const webSocketPath = "ws://" + window.location.host + "/ws";

class Connection {
    readonly webSocket = new WebSocket(webSocketPath);
    readonly subscriptions = new SubscriptionProvider(this.webSocket);
    readonly messageHandler = new MessageHandler(this.webSocket);
}

export const connectionProvider = new class {
    readonly connection = new Connection();

    send(message: ClientMessage) {
        const str = JSON.stringify(message)
        this.connection.webSocket.send(str)
    }

    private _connectionState = mapReadyStateToConnectionState(
        this.connection.webSocket.readyState
    )
    get connectionState() { return this._connectionState; }

    onConnectionChange = (newConnectionState: ConnectionState) => { };

    constructor() {
        // add webSocket event listeners
        this.connection.webSocket.addEventListener(
            "open", () => {
                this._connectionState = ConnectionState.Open;
                this.onConnectionChange(ConnectionState.Open);
            }
        );
        this.connection.webSocket.addEventListener(
            "close", () => {
                this._connectionState = ConnectionState.Closed;
                this.onConnectionChange(ConnectionState.Closed);
            }
        );
    }

}()

function mapReadyStateToConnectionState(readyState: Number) {
    switch (readyState) {
        case 0: return ConnectionState.Connecting;
        case 1: return ConnectionState.Open;
        case 2: return ConnectionState.Closed;
        case 3: return ConnectionState.Closed;
    }
}