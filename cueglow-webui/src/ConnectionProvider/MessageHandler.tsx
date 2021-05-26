import { patchDataHandler } from "./PatchDataProvider";

export class MessageHandler {
    constructor (private webSocket: WebSocket) {
        webSocket.addEventListener("message", this.handleMessage.bind(this))
    };
    
    handleMessage(e: MessageEvent) {
        const message = JSON.parse(e.data)
        console.log("Received", message)
        const event = message.event;
        if (event === "patchInitialState") {
            patchDataHandler.onPatchInitialState(message.data)
        } else if (event === "addFixtureTypes") {
            patchDataHandler.onAddFixtureTypes(message.data)
        }
    };
}