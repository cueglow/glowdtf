// import ReconnectingWebSocket from 'reconnecting-websocket';
import React from 'react';
import { ConnectionState, useConnection } from '../ConnectionProvider/ConnectionProvider';
import { App } from './App';
import { EstablishingConnectionPage } from './EstablishingConnectionPage';
import { NoConnectionPage } from './NoConnectionPage';



export function AppWrapper() {
  const connectionState = useConnection()

  if (connectionState === ConnectionState.Open) {
    return <App />
  } else if (connectionState === ConnectionState.Connecting) {
    return <EstablishingConnectionPage isOpen={connectionState === ConnectionState.Connecting} />
  } else { // connectionState === ConnectionState.Closed
    return <NoConnectionPage isOpen={connectionState === ConnectionState.Closed} />
  }
}


