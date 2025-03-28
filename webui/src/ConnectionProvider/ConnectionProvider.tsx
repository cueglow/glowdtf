
// TODO this module should provide automatic reconnection in the future
// see https://github.com/cueglow/glowdtf/issues/52

import { ClientMessage } from "./ClientMessage";
import { MessageHandler } from "./MessageHandler";
import { SubscriptionProvider } from "./SubscriptionProvider";

export enum ConnectionState {
    Connecting,
    Open,
    Closed,
}

const webSocketPath = "ws://" + window.location.host + "/webSocket";

const pingDelay = 1*60*1000; // 1 minute in ms
const pingMessage = JSON.stringify({event: "ping"})

class Connection {
    readonly webSocket; 
    readonly subscriptions;
    readonly messageHandler;

    constructor() {
        this.webSocket = new WebSocket(webSocketPath);
        // regularly send ping messages to prevent WebSocket timeout
        this.webSocket.addEventListener(
            "open", () => setInterval(() => {
                this.webSocket.send(pingMessage)
            }, pingDelay)
        )

        this.subscriptions = new SubscriptionProvider(this.webSocket);
        this.messageHandler = new MessageHandler(this.webSocket);
        }
        
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