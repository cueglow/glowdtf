import { createContext, ReactNode, useEffect, useState } from "react";
import { FixtureType } from "src/Types/FixtureTypeUtils";
import { PatchData, PatchFixture } from "src/Types/Patch";

const emptyPatch: PatchData = { fixtures: [], fixtureTypes: [] }
export const PatchContext = createContext(emptyPatch);

export const patchDataHandler = new class {
    private currentPatchData = emptyPatch
    get current() { return this.currentPatchData }

    /**
     * Notify the subscriber via `onPatchChange`.  
     * React's setState requires us to make a shallow copy before calling it. 
     */
    private notify() {
        this.currentPatchData = { ...this.currentPatchData };
        this.onPatchChange(this.currentPatchData);
    }

    onPatchInitialState(initialPatchState: PatchData) {
        this.currentPatchData = initialPatchState
        this.onPatchChange(this.currentPatchData)
    }

    onAddFixtureTypes(fixtureTypesToAdd: FixtureType[]) {
        this.currentPatchData.fixtureTypes.push(...fixtureTypesToAdd)
        this.notify()
    }

    onRemoveFixtureTypes(fixtureTypesToRemove: string[]) {
        fixtureTypesToRemove.forEach((uuidString) => {
            const removed = removeIf(this.currentPatchData.fixtureTypes, 
                (fixtureType) => fixtureType.fixtureTypeId === uuidString)
            if (removed == null) {
                console.error("server wanted to remove unknown fixture type");
                // TODO re-subscribe and stop removing fixture types
            }
        })
        this.notify()
    }

    onAddFixtures(fixturesToAdd: PatchFixture[]) {
        this.currentPatchData.fixtures.push(...fixturesToAdd)
        this.notify()
    }

    onRemoveFixtures(uuidsToRemove: string[]) {
        uuidsToRemove.forEach((uuidString) => {
            const removed = removeIf(this.currentPatchData.fixtures, 
                (fixture) => fixture.uuid === uuidString)
            if (removed == null) {
                console.error("server wanted to remove unknown fixture");
                // TODO re-subscribe and stop removing fixtures
            }
        })
        this.notify();
    }

    // callback that PatchDataProvider component hooks into
    onPatchChange = (newPatchData: PatchData) => { }
}()

function removeIf<T>(array: T[], callback: (value: T) => boolean): T | null {
    var i = array.length;
    while (i--) {
        if (callback(array[i])) {
            return array.splice(i, 1)[0];
        }
    }
    return null
};

export function PatchDataProvider(props: { children: ReactNode }) {
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