import { createContext, ReactNode, useEffect, useState } from "react";
import { RigState } from "src/Types/RigState";

const emptyRigState: RigState = {}
export const RigStateContext = createContext(emptyRigState);

export const rigStateHandler = new class {
    private currentRigState = emptyRigState
    get current() { return this.currentRigState }

    /**
     * Notify the subscriber via `onRigStateChange`.  
     */
    private notify() {
        this.onRigStateChange(this.currentRigState);
    }

    onNewRigStateMessage(newRigState: RigState) {
        this.currentRigState = newRigState
        this.notify()
    }

    // callback that PatchDataProvider component hooks into
    onRigStateChange = (newRigState: RigState) => { }
}()

export function RigStateProvider(props: { children: ReactNode }) {
    const [rigState, setRigState] = useState(rigStateHandler.current)

    useEffect(() => {
        rigStateHandler.onRigStateChange = setRigState
    }, [])

    return (
        <RigStateContext.Provider value={rigState}>
            {props.children}
        </RigStateContext.Provider>
    )
}