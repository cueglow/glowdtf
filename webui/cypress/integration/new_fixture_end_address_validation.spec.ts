import { getInputByLabel, pathEquals } from "../support/utilities"

describe("New Fixture End Address Validation", () => {
    beforeEach(() => {
        cy.clearFixtureTypes()

    })

    it("throws a helpful error if fixtures go outside the DMX universe", () => {
        // Add GDTF
        cy.visit("/patch/fixtureTypes")
        cy.dataCy("gdtf_hidden_input")
            .attachFile({
                filePath: "Test@Dimmer_16bit@version1.gdtf",
                encoding: "binary"
            })
        cy.contains("Test")

        // Add Fixtures
        cy.visit("/patch/newFixture")

        cy.contains("Dimmer 16bit").click()
        cy.contains("Default").click()
        const testFixtureName = "Test Fixture"
        cy.contains("Name")
            .type(testFixtureName)
        
        // first, case where end address is beyond universe
        getInputByLabel("Address").type("512")
        cy.contains("Add Fixtures").click()
        // There should be an error
        cy.contains("Only 0 fixtures fit into universe")

        // second, case where end address is in universe, but next one does not
        getInputByLabel("Address").type("510")
        getInputByLabel("Quantity").type("2")
        cy.contains("Add Fixtures").click()
        cy.contains("Only 1 fixtures fit into universe")

        // if we set it properly, it works
        getInputByLabel("Quantity").type("1")
        cy.contains("Add Fixtures").click()
        pathEquals("/patch")
        cy.contains(testFixtureName)
    })
})