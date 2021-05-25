export class SubscriptionProvider {
    constructor (private webSocket: WebSocket) {
        webSocket.addEventListener("open", () => {
            this.openSubscriptions()
        })
    }
    
    openSubscriptions() {
        const patchSubscribe = {
            event: "subscribe",
            data: "patch",
        }
        this.webSocket.send(JSON.stringify(patchSubscribe))
    }
}