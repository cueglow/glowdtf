describe("Patch Fixture Lifecycle", () => {
    beforeEach(() => {
        cy.clearFixtureTypes()
    })

    it("adds Fixture Type, adds Fixtures, removes Fixtures and removes Fixture Type", () => {
        cy.visit("/patch/fixtureTypes")

        // Add GDTF
        // The user would click on the "Add GDTF" button, but Cypress can't easily
        // interact with the file picker, so we directly attach a GDTF file to the input
        cy.dataCy("gdtf_hidden_input")
            .attachFile({
                filePath: "Generic@Tungsten_8Bit@Update_to_GDTF_1.1.gdtf",
                encoding: "binary"
            })
        cy.contains("Generic")
        const fixtureTypeName = "Tungsten 8Bit"
        cy.contains(fixtureTypeName)

        // Add Fixtures
        cy.contains("Fixtures").click()
        cy.contains("Add New Fixtures").click()

        cy.contains("Fixture Type")
            .click()
        cy.contains("Tungsten").click()
        cy.contains("DMX Mode")
            .click()
            .type("{enter}")
        const testFixtureName = "Test Fixture"
        cy.contains("Name")
            .click()
            .type(testFixtureName)
        cy.contains("Quantity")
            .click()
            .type("2")

        cy.contains("Add Fixtures").click()

        // we should see the two names
        cy.contains(`${testFixtureName} 1`)
        cy.contains(`${testFixtureName} 2`).click()

        // Remove One Fixture
        cy.dataCy("remove_selected_fixture_button").click()
        cy.contains(`${testFixtureName} 1`)
        cy.contains(`${testFixtureName} 2`).should("not.exist")

        // Remove GDTF
        cy.visit("/patch/fixtureTypes")
        cy.contains(fixtureTypeName).click()
        cy.dataCy("remove_selected_fixture_type_button").click()

        // check GDTF is gone
        cy.contains(fixtureTypeName).should("not.exist")

        // check that fixture is also gone
        cy.contains("Fixtures").click()
        cy.contains(`${testFixtureName} 1`).should("not.exist")
    })
})