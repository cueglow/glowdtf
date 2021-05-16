
// TODO this module should provide automatic reconnection in the future
// see https://github.com/cueglow/cueglow/issues/52

export enum ConnectionState {
    Connecting,
    Open,
    Closed,
}

export const connectionProvider = new class {
    private readonly webSocketPath = "ws://" + window.location.host + "/ws";
    private readonly webSocketConnection = new WebSocket(this.webSocketPath);

    private _connectionState = mapReadyStateToConnectionState(this.webSocketConnection.readyState)
    get connectionState() { return this._connectionState; }

    onConnectionChange = (newConnectionState: ConnectionState) => { };

    constructor() {
        // add webSocket event listeners
        this.webSocketConnection.addEventListener(
            "open", () => {
                this._connectionState = ConnectionState.Open;
                this.onConnectionChange(ConnectionState.Open);
            }
        );
        this.webSocketConnection.addEventListener(
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