import { HotkeysProvider } from '@blueprintjs/core';
import { Router } from '@reach/router';
import React from 'react';
import { PatchDataProvider } from 'src/ConnectionProvider/PatchDataProvider';
import { bpVariables } from '../BlueprintVariables/BlueprintVariables';
import PatchWindow from '../PatchWindow/PatchWindow';
import MainWindow from './MainWindow';

// TODO install emotion (https://emotion.sh/docs/introduction), a CSS-in-JS library
// in contrast to inline styles allows media queries, etc.

export function App() {
  return (
    <HotkeysProvider>
      <PatchDataProvider>
        <Router className="bp3-dark" style={{
          height: "100vh",
          background: bpVariables.ptDarkAppBackgroundColor,
        }}>
          <MainWindow path="/" default />
          <PatchWindow path="patch/*" />
        </Router>
      </PatchDataProvider>
    </HotkeysProvider>
  );
}
