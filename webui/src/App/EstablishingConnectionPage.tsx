import { Dialog, Spinner } from '@blueprintjs/core';
import React from 'react';
import { bp } from '../BlueprintVariables/BlueprintVariables';

export function EstablishingConnectionPage(props: { isOpen: boolean; }) {
  return (
    <Dialog 
    title="Connecting to GlowDTF Server..."
    isOpen={props.isOpen} 
    className="bp3-dark" 
    isCloseButtonShown={false} 
    canOutsideClickClose={false} 
    canEscapeKeyClose={false}>
      <div style={{ marginTop: 2 * bp.ptGridSizePx, }}>
        <Spinner />
      </div>
    </Dialog>
  );
}
