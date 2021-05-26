import { createContext, ReactNode, useEffect, useRef, useState } from "react";
import { PatchData } from "src/Types/Patch";
import { connectionProvider } from "./ConnectionProvider";

const emptyPatch: PatchData = {fixtures: [], fixtureTypes: []}
export const PatchContext = createContext(emptyPatch);

const messageHandler = connectionProvider.connection.messageHandler

let currentPatchData = emptyPatch

// callback that PatchDataProvider component hooks into
let onPatchChange = (newPatchData: PatchData) => { }

// TODO once messageHandler is destroyed on re-connect, 
// this needs to happen after every re-connect
messageHandler.onPatchInitialState = (initialPatchState: PatchData) => {
    currentPatchData = initialPatchState
    onPatchChange(currentPatchData)
}
messageHandler.onAddFixtureTypes = (fixtureTypesToAdd) => {
    currentPatchData.fixtureTypes.push(...fixtureTypesToAdd)
    // shallow copy required for react setState
    currentPatchData = {...currentPatchData}
    onPatchChange(currentPatchData)
}

export function PatchDataProvider(props: {children: ReactNode}) {
    const [patchData, setPatchData] = useState(currentPatchData)

    useEffect(() => {
        onPatchChange = setPatchData
    }, [])

    return (
        <PatchContext.Provider value={patchData}>
            {props.children}
        </PatchContext.Provider>
    )
}