import { useEffect, useState } from "react"

// TODO this module should provide automatic reconnection in the future
// see https://github.com/cueglow/cueglow/issues/52

const webSocketPath = "ws://" + window.location.host + "/ws"
const webSocketConnection = new WebSocket(webSocketPath)

export enum ConnectionState {
    Connecting,
    Open,
    Closed,
}

function mapReadyStateToConnectionState(readyState: Number) {
    switch (readyState) {
        case 0: return ConnectionState.Connecting;
        case 1: return ConnectionState.Open;
        case 2: return ConnectionState.Closed;
        case 3: return ConnectionState.Closed;
    }
}

export function useConnection() {
    const [connectionState, setConnectionState] = 
        useState(mapReadyStateToConnectionState(webSocketConnection.readyState));
    
    // add event handlers to WebSocket once
    useEffect(() => {
        webSocketConnection.addEventListener(
            "open", () => {setConnectionState(ConnectionState.Open)}
        );
        webSocketConnection.addEventListener(
            "close", () => {setConnectionState(ConnectionState.Closed)}
        );
    }, [])

    return connectionState;
}