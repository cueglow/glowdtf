// Variables exported in `blueprint.module.scss`
import bpVariables from './blueprintVariables.module.scss';
// Color Variables already provided by Blueprint.js
import { Colors } from '@blueprintjs/core'

// derive numeric variables from strings
function pixelStringToNumber(inputString: string): number {
    return Number(inputString.slice(0, -2));
}

const bpNumVariables = {
    ptGridSizePx: pixelStringToNumber(bpVariables.ptGridSize),
};

export const bp = {...Colors, ...bpNumVariables, vars: bpVariables}
