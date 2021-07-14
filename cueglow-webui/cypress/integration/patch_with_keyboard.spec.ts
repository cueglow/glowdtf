import {pathEquals} from '../support/utilities'

describe("Patch Fixture Lifecycle", () => {
    beforeEach(() => {
        cy.clearFixtureTypes()
    })

    it("does basic Patch actions with keyboard only", () => {
        cy.visit("/")

        // Open Patch
        cy.contains("Patch").type("{shift}p")
        pathEquals("/patch")

        // Open Fixture Types
        cy.contains("Patch").type("p")
        pathEquals("/patch/fixtureTypes")

        // Add GDTF
        cy.dataCy("gdtf_hidden_input")
            .attachFile({
                filePath: "Generic@Tungsten_8Bit@Update_to_GDTF_1.1.gdtf",
                encoding: "binary"
            })
        cy.contains("Generic")
        const fixtureTypeName = "Tungsten 8Bit"
        cy.contains(fixtureTypeName)

        // Add Fixture
        const testFixtureName = "Test Fixture"
        cy.contains("Patch")
            .type("i")
            .type("a")
        cy.get("body")
            .type("{enter}")
            .type("{enter}")
            .type(testFixtureName)
            .type("{ctrl}{enter}")

        // Select Fixture
        cy.contains(testFixtureName)
            .click()
            .type("{esc}")

        // Delete Fixture
        cy.get("body").type("{del}")
        cy.contains(testFixtureName).should("not.exist")
        
        // Delete GDTF
        cy.get("body").type("p")
        cy.contains("Generic").click()
        cy.get("body").type("{del}")
        cy.contains("Generic").should("not.exist")

        // Go to Main Screen again
        cy.get("body").type("{esc}")
        pathEquals("/")
    })
})