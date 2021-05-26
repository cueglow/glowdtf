import { FixtureType } from "src/Types/FixtureTypeUtils";
import { PatchData } from "src/Types/Patch";

export class MessageHandler {
    constructor (private webSocket: WebSocket) {
        webSocket.addEventListener("message", this.handleMessage.bind(this))
    };
    
    handleMessage(e: MessageEvent) {
        const message = JSON.parse(e.data)
        console.log("Received", message)
        const event = message.event;
        if (event === "patchInitialState") {
            this.onPatchInitialState(message.data)
        } else if (event === "addFixtureTypes") {
            this.onAddFixtureTypes(message.data)
        }
    };


    // TODO is there a way to not write these manually for every event?
    onPatchInitialState = (patchInitialState: PatchData) => { };
    onAddFixtureTypes = (fixtureTypes: FixtureType[]) => { };
}