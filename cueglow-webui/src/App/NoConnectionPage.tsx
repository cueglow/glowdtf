import { Dialog, UL } from '@blueprintjs/core';
import React from 'react';
import { bp } from '../BlueprintVariables/BlueprintVariables';

export function NoConnectionPage(props: { isOpen: boolean; }) {
  return (
    <Dialog className="bp3-dark" isOpen={props.isOpen} icon="error" title="Connection Error"
      isCloseButtonShown={false} canOutsideClickClose={false} canEscapeKeyClose={false}>
      <div style={{ padding: bp.vars.ptGridSize }}>
        <p style={{ marginBottom: 2 * bp.ptGridSizePx, }}>
          Cannot connect to the server.
        </p>
        <p>Please check: </p>
        <UL style={{ marginBottom: 2 * bp.ptGridSizePx, }}>
          <li>that the CueGlow Server is running</li>
          <li>your network connection to the CueGlow Server</li>
        </UL>
        <p>Then reload the page to try connecting.  </p>
      </div>
    </Dialog>
  );
}
