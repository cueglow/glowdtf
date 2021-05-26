import { patchDataHandler } from "./PatchDataProvider";

export class MessageHandler {
    constructor (private webSocket: WebSocket) {
        webSocket.addEventListener("message", this.handleMessage.bind(this))
    };
    
    handleMessage(e: MessageEvent) {
        const message = JSON.parse(e.data);
        const event = message.event;
        const data = message.data;
        
        if (event === "patchInitialState") {
            patchDataHandler.onPatchInitialState(data)
        } else if (event === "addFixtureTypes") {
            patchDataHandler.onAddFixtureTypes(data)
        }
    };
}