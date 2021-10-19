import React, { useEffect, useState } from 'react';
import { bp } from 'src/BlueprintVariables/BlueprintVariables';
import { } from 'styled-components/macro';
import { connectionProvider, ConnectionState } from '../ConnectionProvider/ConnectionProvider';
import { App } from './App';
import { EstablishingConnectionPage } from './EstablishingConnectionPage';
import { NoConnectionPage } from './NoConnectionPage';

export function AppWrapper() {
  return (
  <div className="bp3-dark" css={`
    height: 100vh;
    background: ${bp.vars.ptDarkAppBackgroundColor};
  `}> 
    <ContentBasedOnConnectionState />
  </div>
  )
}

function ContentBasedOnConnectionState() {
  const [connectionState, setConnectionState] = useState(connectionProvider.connectionState);

  useEffect(() => {
    connectionProvider.onConnectionChange = (newConnectionState) => setConnectionState(newConnectionState)
  }, [])

  if (connectionState === ConnectionState.Open) {
    return <App />
  } else if (connectionState === ConnectionState.Connecting) {
    return <EstablishingConnectionPage isOpen={connectionState === ConnectionState.Connecting} />
  } else { // connectionState === ConnectionState.Closed
    return <NoConnectionPage isOpen={connectionState === ConnectionState.Closed} />
  }
}


