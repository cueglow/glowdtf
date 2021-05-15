import { Dialog, HotkeysProvider, Spinner, UL } from '@blueprintjs/core';
// import ReconnectingWebSocket from 'reconnecting-websocket';
import { Router } from '@reach/router';
import React, { createContext } from 'react';
import { NIL as uuidNilString, parse as uuidParse, v4 as uuidv4 } from 'uuid';
import { bpNumVariables, bpVariables } from '../BlueprintVariables/BlueprintVariables';
import { ConnectionState, useConnection } from '../ConnectionProvider/ConnectionProvider';
import MainWindow from '../MainWindow';
import PatchWindow from '../PatchWindow/PatchWindow';

// TODO install emotion (https://emotion.sh/docs/introduction), a CSS-in-JS library
// in contrast to inline styles allows media queries, etc.

const exampleSpotOne = uuidv4(null, new Uint8Array(16));
const exampleDimmer = uuidv4(null, new Uint8Array(16));
const uuidNil = uuidParse(uuidNilString);

const patchExampleData = {
  fixtures: [
    {
      uuid: uuidNil,
      fid: 1,
      name: "Lamp 1",
      fixtureTypeId: exampleSpotOne,
      dmxMode: "Standard",
      universe: 1,
      address: 1,
    },
    {
      uuid: uuidNil,
      fid: 2,
      name: "Lamp 2",
      fixtureTypeId: exampleSpotOne,
      dmxMode: "Standard",
      universe: 1,
      address: 22,
    },
    {
      uuid: uuidNil,
      fid: 10,
      name: "Front L",
      fixtureTypeId: exampleDimmer,
      dmxMode: "8-bit",
      universe: 1,
      address: 43,
    },
    {
      uuid: uuidNil,
      fid: 11,
      name: "Front R",
      fixtureTypeId: exampleDimmer,
      dmxMode: "8-bit",
      universe: 1,
      address: 44,
    },
    {
      uuid: uuidNil,
      fid: 12,
      name: "Bühne vorne",
      fixtureTypeId: exampleDimmer,
      dmxMode: "8-bit",
      universe: 1,
      address: 45,
    },
    {
      uuid: uuidNil,
      fid: 12,
      name: "Bühne mitte",
      fixtureTypeId: exampleDimmer,
      dmxMode: "8-bit",
      universe: 1,
      address: 46,
    },
    {
      uuid: uuidNil,
      fid: 13,
      name: "Bühne hinten",
      fixtureTypeId: exampleDimmer,
      dmxMode: "8-bit",
      universe: 1,
      address: 47,
    },
  ],
  fixtureTypes: [
    {
      fixtureTypeId: exampleSpotOne,
      manufacturer: "GLP",
      name: "impression SpotOne",
      modes: [
        {
          name: "Standard",
          channelCount: 21,
        },
        {
          name: "Extended",
          channelCount: 34,
        }
      ],
    },
    {
      fixtureTypeId: exampleDimmer,
      manufacturer: "Generic",
      name: "Dimmer",
      modes: [
        {
          name: "8-bit",
          channelCount: 1,
        },
        {
          name: "16-bit",
          channelCount: 2,
        }
      ],
    },
  ],
};


export const PatchContext = createContext(patchExampleData);

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

function App() {
  return (
    <HotkeysProvider>
      <PatchContext.Provider value={patchExampleData}>
        <Router className="bp3-dark" style={{
          height: "100vh",
          background: bpVariables.ptDarkAppBackgroundColor,
        }}>
          <MainWindow path="/" default />
          <PatchWindow path="patch/*" />
        </Router>
      </PatchContext.Provider >
    </HotkeysProvider>
  )
}

function NoConnectionPage(props: { isOpen: boolean }) {
  return (
    <div className="bp3-dark" style={{
      height: "100vh",
      background: bpVariables.ptDarkAppBackgroundColor,
    }}>
      <Dialog className="bp3-dark" isOpen={props.isOpen} icon="error" title="Connection Error"
        isCloseButtonShown={false} canOutsideClickClose={false} canEscapeKeyClose={false}>
        <div style={{
          padding: bpVariables.ptGridSize,
        }}>
          <p style={{ marginBottom: 2 * bpNumVariables.ptGridSizePx, }}>Cannot connect to the server.</p>
          <p>Please check: </p>
          <UL style={{ marginBottom: 2 * bpNumVariables.ptGridSizePx, }}>
            <li>that the CueGlow Server is running</li>
            <li>your network connection to the CueGlow Server</li>
          </UL>
          <p>Then reload the page to try connecting.  </p>
        </div>
      </Dialog>
    </div>
  )
}

function EstablishingConnectionPage(props: { isOpen: boolean }) {
  return (
    <div className="bp3-dark" style={{
      height: "100vh",
      background: bpVariables.ptDarkAppBackgroundColor,
    }}>
      <Dialog className="bp3-dark" isOpen={props.isOpen} title="Connecting to CueGlow Server..."
        isCloseButtonShown={false} canOutsideClickClose={false} canEscapeKeyClose={false}>
        <div style={{ marginTop: 2 * bpNumVariables.ptGridSizePx, }}>
          <Spinner />
        </div>
      </Dialog>
    </div>
  )
}