import bpVariables from './blueprintVariables.module.scss';
import { Colors } from '@blueprintjs/core'

// Color Variables
export { Colors as bpColors }

// Variables exported in `blueprint.module.scss`
export { bpVariables }

// derived numeric variables
function pixelStringToNumber(inputString: string): number {
    return Number(inputString.slice(0, -2));
}

export const bpNumVariables = {
    ptGridSizePx: pixelStringToNumber(bpVariables.ptGridSize),
};
