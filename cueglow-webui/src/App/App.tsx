import { HotkeysProvider } from '@blueprintjs/core';
import { Router } from '@reach/router';
import React from 'react';
import { PatchDataProvider } from 'src/ConnectionProvider/PatchDataProvider';
import { RigStateProvider } from 'src/ConnectionProvider/RigStateProvider';
import { PatchWindow } from '../PatchWindow/PatchWindow';
import { MainWindow } from './MainWindow';

export function App() {
  return (
    <HotkeysProvider>
      <PatchDataProvider>
        <Router>
          <MainWindow path="/" default />
          <PatchWindow path="patch/*" />
        </Router>
      </PatchDataProvider>
    </HotkeysProvider>
  );
}
