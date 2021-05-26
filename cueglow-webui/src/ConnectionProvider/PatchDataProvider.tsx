import { createContext, ReactNode, useEffect, useState } from "react";
import { FixtureType } from "src/Types/FixtureTypeUtils";
import { PatchData } from "src/Types/Patch";

const emptyPatch: PatchData = {fixtures: [], fixtureTypes: []}
export const PatchContext = createContext(emptyPatch);

export const patchDataHandler = new class {
    private currentPatchData = emptyPatch
    get current() {return this.currentPatchData}

    onPatchInitialState(initialPatchState: PatchData) {
        this.currentPatchData = initialPatchState
        this.onPatchChange(this.currentPatchData)
    }

    onAddFixtureTypes(fixtureTypesToAdd: FixtureType[]) {
        this.currentPatchData.fixtureTypes.push(...fixtureTypesToAdd)
        // shallow copy required for react setState
        this.currentPatchData = {...this.currentPatchData}
        this.onPatchChange(this.currentPatchData)
    }

    onRemoveFixtureTypes(fixtureTypesToRemove: string[]) {
        fixtureTypesToRemove.forEach((uuidString) => {
            const index = this.currentPatchData.fixtureTypes.findIndex((fixtureType) => {
                return fixtureType.fixtureTypeId === uuidString
            })
            if (index === -1) {
                console.error("server wanted to remove unknown fixture type"); 
                // TODO re-subscribe
                return
            }
            this.currentPatchData.fixtureTypes.splice(index, 1)
        })
        this.currentPatchData = {...this.currentPatchData}
        this.onPatchChange(this.currentPatchData)
    }

    // callback that PatchDataProvider component hooks into
    onPatchChange = (newPatchData: PatchData) => { }
}()

export function PatchDataProvider(props: {children: ReactNode}) {
    const [patchData, setPatchData] = useState(patchDataHandler.current)

    useEffect(() => {
        patchDataHandler.onPatchChange = setPatchData
    }, [])

    return (
        <PatchContext.Provider value={patchData}>
            {props.children}
        </PatchContext.Provider>
    )
}