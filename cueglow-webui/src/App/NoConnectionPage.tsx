import { Dialog, UL } from '@blueprintjs/core';
import React from 'react';
import { bpNumVariables, bpVariables } from '../BlueprintVariables/BlueprintVariables';

export function NoConnectionPage(props: { isOpen: boolean; }) {
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
  );
}
