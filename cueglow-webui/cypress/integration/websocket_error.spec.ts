import { WebSocket, Server } from 'mock-socket';

describe("WebSocket Error Handling", () => {
    it("displays error and prevents interaction when websocket can't connect", () => {
        // stub WebSocket with mock-socket
        cy.visit("/", {
            onBeforeLoad: (win) => {
                cy.stub(win, "WebSocket").callsFake((url) => {
                    const MockServer = new Server(url)
                    MockServer.on('connection', (socket) => {
                      socket.close()
                    })
                    return new WebSocket(url) // mock-socket WebSocket
                })
            }
        })

        cy.contains("Error")
        cy.contains("Patch").should("not.exist")
        
        // try to navigate to Patch with Hotkey
        cy.get("body").type("{shift}p")
        // navigating with Hotkey should not work
        cy.location("pathname").should("equal", "/")
    })
})