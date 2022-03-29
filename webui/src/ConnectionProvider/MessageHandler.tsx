import { patchDataHandler } from "./PatchDataProvider";
import { rigStateHandler } from "./RigStateProvider";

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
        } else if (event === "removeFixtureTypes") {
            patchDataHandler.onRemoveFixtureTypes(data)
        } else if (event === "addFixtures") {
            patchDataHandler.onAddFixtures(data)
        } else if (event === "updateFixtures") {
            patchDataHandler.onUpdateFixtures(data)
        } else if (event === "removeFixtures") {
            patchDataHandler.onRemoveFixtures(data)
        } else if (event === "rigState") {
            rigStateHandler.onNewRigStateMessage(data)
        } else {
            console.log("Received unhandled WebSocket Message", message)
        }
    };
}