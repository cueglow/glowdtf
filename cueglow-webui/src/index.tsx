import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import './index.css';
// import ReconnectingWebSocket from 'reconnecting-websocket';
import { Router, Link, RouteComponentProps } from '@reach/router';
import { AnchorButton, Button, Colors } from '@blueprintjs/core';

// Import SASS-variables from blueprint.js
/* eslint import/no-webpack-loader-syntax: off */
const bp = require('sass-extract-loader!@blueprintjs/core/lib/scss/variables.scss');
console.log(bp);
console.log("starting...");

// TODO
// serve this via Kotlin server

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

console.log(bp.global["$pt-dark-app-background-color"].value.hex);

// TODO install emotion (https://emotion.sh/docs/introduction), a CSS-in-JS library
// in contrast to inline styles allows media queries, etc.

function App() {
  return (
    <Router className="bp3-dark" style={{
      height: "100vh",
      background: bp.global["$pt-dark-app-background-color"].value.hex
    }}>
        <MainWindow path="/" />
        <PatchWindow path="patch" />
      </Router>
  );
}

function MainWindow(props: RouteComponentProps) {
  return (
    // TODO renders non-valid HTML
    // create Link-Button component with useNavigation Hook
    <Link to="patch"><Button text="Patch" /></Link>
    
  );
}

function PatchWindow(props: RouteComponentProps) {
  return (
    // TODO renders non-valid HTML
    // create Link-Button component with useNavigation Hook
    <Link to="/"><Button text="Exit" /></Link>
  );
}

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);





