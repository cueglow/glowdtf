// Define Custom Cypress Commands here
// More Info: https://docs.cypress.io/api/cypress-api/custom-commands
//
// Please manually add all custom commands to the Cypress namespace
// Info on Typing: https://docs.cypress.io/guides/tooling/typescript-support#Types-for-custom-commands

declare global {
    namespace Cypress {
        interface Chainable {
            /**
             * Custom command to select DOM element by data-cy attribute.
             * @example cy.dataCy('greeting')
             */
            dataCy(value: string): Chainable

            /**
             * Remove all patched Fixture Types and their associated Fixtures
             */
            clearFixtureTypes(): void
        }
    }
}

Cypress.Commands.add('dataCy', (value) => {
    return cy.get(`[data-cy=${value}]`)
})

export function openWebSocket() {
    const urlWithoutProtocol = Cypress.config().baseUrl.replace(/(^\w+:|^)\/\//, '');
    return new WebSocket(`ws://${urlWithoutProtocol}/ws`)
}

Cypress.Commands.add("clearFixtureTypes", () => {
    // setup: remove all patched fixture types
    const ws = openWebSocket()
    ws.onopen = () => {
        ws.send(JSON.stringify({
            event: "subscribe",
            data: "patch",
        }))
    }
    // this is how to await a Cypress.Promise (https://docs.cypress.io/api/utilities/promise#Waiting-for-Promises)
    cy.wrap(null, { log: false }).then(() => {
        return new Cypress.Promise<void>((resolve, reject) => {
            ws.onmessage = (event) => {
                const parsed = JSON.parse(event.data)
                const uuids = parsed.data.fixtureTypes.map((ft) => {
                    return ft.fixtureTypeId
                })
                if (!(uuids.length === 0)) {
                    ws.send(JSON.stringify({
                        event: "removeFixtureTypes",
                        data: uuids,
                        messageId: 827
                    }))
                    const debugNames = parsed.data.fixtureTypes.map((ft) => {
                        return `${ft.manufacturer} ${ft.name} (${ft.fixtureTypeId})`
                    })
                    cy.log("Removed Fixture Types before test: " + debugNames)
                }
                ws.close()
                resolve()
            }
        })
    })
})
