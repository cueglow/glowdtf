import { createContext, ReactNode, useEffect, useState } from "react";
import { PatchData } from "src/Types/Patch";
import { connectionProvider } from "./ConnectionProvider";

const emptyPatch: PatchData = {fixtures: [], fixtureTypes: []}
export const PatchContext = createContext(emptyPatch);

export function PatchDataProvider(props: {children: ReactNode}) {
    const [patchData, setPatchData] = useState(emptyPatch)
    useEffect(() => {
        connectionProvider.connection.messageHandler.onPatchInitialState = setPatchData
    }, [])

    return (
        <PatchContext.Provider value={patchData}>
            {props.children}
        </PatchContext.Provider>
    )
}