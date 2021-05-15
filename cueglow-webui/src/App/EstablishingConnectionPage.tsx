import { Dialog, Spinner } from '@blueprintjs/core';
import React from 'react';
import { bpNumVariables, bpVariables } from '../BlueprintVariables/BlueprintVariables';

export function EstablishingConnectionPage(props: { isOpen: boolean; }) {
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
  );
}
