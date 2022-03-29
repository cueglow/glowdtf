import { openWebSocket } from "../support/commands"

describe("Fixture Control", () => {
    beforeEach(() => {
        cy.clearFixtureTypes()

    })

    it("let's us select a fixture, move a slider and the slider updates", () => {
        // add GDTF
        cy.visit("/patch/fixtureTypes")
        cy.dataCy("gdtf_hidden_input")
            .attachFile({
                filePath: "Generic@Tungsten_8Bit@Update_to_GDTF_1.1.gdtf",
                encoding: "binary"
            })
        // add fixture
        cy.contains("Tungsten").then(() => {
            const ws = openWebSocket()
            ws.onopen = () => {
                ws.send(JSON.stringify({
                    event: "addFixtures",
                    data: [
                        {
                            uuid: "5b652e3d-7973-4118-8690-2bbef12c9deb",
                            fid: 1,
                            name: "Generic Dimmer",
                            fixtureTypeId: "001b2163-d489-0000-9e2c-c1c702a22246",
                            dmxMode: "Default",
                            universe: 1,
                            address: 1,
                        }
                    ]
                }))
                ws.close()
            }
        })
        // move a slider
        cy.visit("/")
        cy.contains("Generic Dimmer").click()
        cy.contains("Dimmer 1")
        cy.contains("38").should("not.exist")
        cy.contains("32").click("right")
        cy.contains("38")
    })
})