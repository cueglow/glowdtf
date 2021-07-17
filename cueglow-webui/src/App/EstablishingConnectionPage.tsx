import { Dialog, Spinner } from '@blueprintjs/core';
import React from 'react';
import { bpNumVariables } from '../BlueprintVariables/BlueprintVariables';

export function EstablishingConnectionPage(props: { isOpen: boolean; }) {
  return (
    <Dialog className="bp3-dark" isOpen={props.isOpen} title="Connecting to CueGlow Server..."
      isCloseButtonShown={false} canOutsideClickClose={false} canEscapeKeyClose={false}>
      <div style={{ marginTop: 2 * bpNumVariables.ptGridSizePx, }}>
        <Spinner />
      </div>
    </Dialog>
  );
}
