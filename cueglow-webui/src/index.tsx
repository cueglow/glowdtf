import React, { createContext } from 'react';
import ReactDOM from 'react-dom';
import { v4 as uuidv4, NIL as uuidNilString, parse as uuidParse } from 'uuid';
import './index.css';
import './index.scss'
// import ReconnectingWebSocket from 'reconnecting-websocket';
import { Router } from '@reach/router';
import PatchWindow from './PatchWindow/PatchWindow';
import MainWindow from './MainWindow';

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');

// TODO remove hardcoded websocket url
// make websocket request to url from which website was served

// commented out WebSocket code
//
// let ws = new ReconnectingWebSocket("ws://localhost:7000/ws", [],
//   { maxReconnectionDelay: 2000, minReconnectionDelay: 1000, debug: true});

// ws.send("testing, testing");

// function App() {
//   let [readyState, setReadyState] = useState(ws.readyState);
//   ws.addEventListener(
//     "close", (e) => { setTimeout(() => setReadyState(ws.readyState), 50) }
//   );
//   ws.addEventListener("open", e => (setReadyState(ws.readyState)));

//   return (
//     <div className="App">
//       {readyState}
//     </div>
//   );
// }

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

function App() {
  return (
    <PatchContext.Provider value={patchExampleData}>
      <Router className="bp3-dark" style={{
        height: "100vh",
        background: bp.global["$pt-dark-app-background-color"].value.hex
      }}>
        <MainWindow path="/" default />
        <PatchWindow path="patch" />
      </Router>
    </PatchContext.Provider >
  );
}

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);





