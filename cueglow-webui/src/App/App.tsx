import { HotkeysProvider } from '@blueprintjs/core';
import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { PatchDataProvider } from 'src/ConnectionProvider/PatchDataProvider';
import { PatchWindow } from '../PatchWindow/PatchWindow';
import { MainWindow } from './MainWindow';

export function App() {
  return (
    <BrowserRouter>
      <HotkeysProvider>
        <PatchDataProvider>
          <Routes>
            <Route path="/*" element={<MainWindow />}/>
            <Route path="patch/*" element={<PatchWindow />}/>
          </Routes>
        </PatchDataProvider>
      </HotkeysProvider>
    </BrowserRouter>
  );
}
