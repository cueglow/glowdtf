// import ReconnectingWebSocket from 'reconnecting-websocket';
import React, { useEffect, useState } from 'react';
import { connectionProvider, ConnectionState } from '../ConnectionProvider/ConnectionProvider';
import { App } from './App';
import { EstablishingConnectionPage } from './EstablishingConnectionPage';
import { NoConnectionPage } from './NoConnectionPage';

function useConnectionState() {
  const [connectionState, setConnectionState] = useState(connectionProvider.connectionState);

  useEffect(() => {
    connectionProvider.onConnectionChange = (newConnectionState) => setConnectionState(newConnectionState)
  }, [])

  return connectionState;
}

export function AppWrapper() {
  const connectionState = useConnectionState()

  if (connectionState === ConnectionState.Open) {
    return <App />
  } else if (connectionState === ConnectionState.Connecting) {
    return <EstablishingConnectionPage isOpen={connectionState === ConnectionState.Connecting} />
  } else { // connectionState === ConnectionState.Closed
    return <NoConnectionPage isOpen={connectionState === ConnectionState.Closed} />
  }
}


