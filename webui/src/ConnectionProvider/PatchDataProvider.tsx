import _ from "lodash";
import { createContext, ReactNode, useEffect, useState } from "react";
import { FixtureType } from "src/Types/FixtureType";
import { PatchData, PatchFixture } from "src/Types/Patch";
import { PatchFixtureUpdate } from "./ClientMessage";

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
        // alway copy, never mutate old array because of React Tabulator diffing
        this.currentPatchData.fixtureTypes = [
            ...this.currentPatchData.fixtureTypes, 
            ...fixtureTypesToAdd
        ]
        this.notify()
    }

    onRemoveFixtureTypes(fixtureTypesToRemove: string[]) {
        this.currentPatchData.fixtureTypes = this.currentPatchData.fixtureTypes.filter((fixtureType) => 
            !fixtureTypesToRemove.includes(fixtureType.fixtureTypeId)
        )
        this.notify()
    }

    onAddFixtures(fixturesToAdd: PatchFixture[]) {
        this.currentPatchData.fixtures = [
            ...this.currentPatchData.fixtures,
            ...fixturesToAdd
        ]
        this.notify()
    }

    onUpdateFixtures(fixtureUpdates: PatchFixtureUpdate[]) {
        this.currentPatchData.fixtures = this.currentPatchData.fixtures.map((oldFixture) => {
            const update = fixtureUpdates.find((fixtureUpdate) => fixtureUpdate.uuid === oldFixture.uuid)
            if (update === undefined) {
                return oldFixture
            } else {
                return _.mergeWith(oldFixture, update, (oldValue, updateValue) => updateValue ?? oldValue)
            }
        })
        this.notify()
    }

    onRemoveFixtures(uuidsToRemove: string[]) {
        this.currentPatchData.fixtures = this.currentPatchData.fixtures.filter((fixture) => 
            !uuidsToRemove.includes(fixture.uuid)
        )
        this.notify();
    }

    // callback that PatchDataProvider component hooks into
    onPatchChange = (newPatchData: PatchData) => { }
}()

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