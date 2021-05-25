import { createContext, ReactNode, useEffect, useState } from "react";
import { PatchData } from "src/Types/Patch";
import { connectionProvider } from "./ConnectionProvider";

const emptyPatch: PatchData = {fixtures: [], fixtureTypes: []}
export const PatchContext = createContext(emptyPatch);

const messageHandler = connectionProvider.connection.messageHandler

export function PatchDataProvider(props: {children: ReactNode}) {
    const [patchData, setPatchData] = useState(emptyPatch)

    useEffect(() => {
        messageHandler.onPatchInitialState = setPatchData
    }, [])
    
    useEffect(() => {
        messageHandler.onAddFixtureTypes = (fixtureTypesToAdd) => {
            const newFixtureTypes = [...patchData.fixtureTypes]
            newFixtureTypes.push(...fixtureTypesToAdd)
            setPatchData({fixtures: patchData.fixtures, fixtureTypes: newFixtureTypes})
        };
    }, [patchData]) // TODO can we avoid setting this on every update?

    return (
        <PatchContext.Provider value={patchData}>
            {props.children}
        </PatchContext.Provider>
    )
}