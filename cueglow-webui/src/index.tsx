import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ReconnectingWebSocket from 'reconnecting-websocket';

console.log("starting...")

// TODO
// serve this via Kotlin server

// TODO remove hardcoded websocket url
// make websocket request to url from which website was served
let ws = new ReconnectingWebSocket("ws://localhost:7000/ws", [],
  { maxReconnectionDelay: 2000, minReconnectionDelay: 1000, debug: true});

ws.send("testing, testing");


ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

function App() {
  let [readyState, setReadyState] = useState(ws.readyState);
  ws.addEventListener(
    "close", (e) => { setTimeout(() => setReadyState(ws.readyState), 50) }
  );
  ws.addEventListener("open", e => (setReadyState(ws.readyState)));

  return (
    <div className="App">
      {readyState}
    </div>
  );
}



