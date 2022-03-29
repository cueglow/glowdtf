import { ClientMessage, GlowTopic } from "./ClientMessage"
import { connectionProvider } from "./ConnectionProvider"

export class SubscriptionProvider {
    constructor (private webSocket: WebSocket) {
        webSocket.addEventListener("open", () => {
            this.openSubscriptions()
        })
    }
    
    openSubscriptions() {
        const msg = new ClientMessage.Subscribe(GlowTopic.Patch)
        connectionProvider.send(msg)
        const rigStateSubscription = new ClientMessage.Subscribe(GlowTopic.RigState)
        connectionProvider.send(rigStateSubscription)
    }
}