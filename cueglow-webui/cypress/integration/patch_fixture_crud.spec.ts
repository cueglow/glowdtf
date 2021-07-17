describe("Patch Fixture Lifecycle", () => {
    beforeEach(() => {
        cy.clearFixtureTypes()
    })

    it("adds, updates and removes Fixture Types and Fixtures", () => {
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
        // TODO remove force: true once issue fixed in Cypress: 
        // https://github.com/cypress-io/cypress/issues/7306
        cy.contains(`${testFixtureName} 2`).click({ force: true })

        // Remove One Fixture
        cy.dataCy("remove_selected_fixture_button").click()
        cy.contains(`${testFixtureName} 1`)
        cy.contains(`${testFixtureName} 2`).should("not.exist")

        // Edit FID
        cy.get('.tabulator-cell[tabulator-field=fid]')
            .click()
            .type("{backspace}2{enter}")
        cy.get('.tabulator-cell[tabulator-field=fid]') // new element - re-get
            .should("have.text", "2")

        // Edit Name
        cy.get('.tabulator-cell[tabulator-field=name]')
            .click()
            .type("{selectall}{backspace}bizaar{enter}")
        cy.get('.tabulator-cell[tabulator-field=name]')
            .should("have.text", "bizaar")

        // Edit Universe - invalid
        cy.get('.tabulator-cell[tabulator-field=universe]')
            .click()
            .type("{backspace}-1{enter}")
        // error shown
        cy.contains("must not be smaller than 0")
        // correct to something valid
        cy.get('.tabulator-cell[tabulator-field=universe]')
            .type("{selectall}{backspace}0{enter}")
        cy.get('.tabulator-cell[tabulator-field=universe]')
            .should("have.text", "0")
        
        // Edit DMX Address
        cy.get('.tabulator-cell[tabulator-field=address]')
            .click()
            .type("{backspace}{enter}")
        cy.get('.tabulator-cell[tabulator-field=address]')
            .invoke("text")
            .then((text) => text.trim())
            .should("equal", "")

        // Changes should persist through reload
        cy.reload()
        cy.get('.tabulator-cell[tabulator-field=fid]') // new element - re-get
            .should("have.text", "2")
        cy.get('.tabulator-cell[tabulator-field=name]')
            .should("have.text", "bizaar")
        cy.get('.tabulator-cell[tabulator-field=universe]')
            .should("have.text", "0")
        cy.get('.tabulator-cell[tabulator-field=address]')
            .invoke("text")
            .then((text) => text.trim())
            .should("equal", "")

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