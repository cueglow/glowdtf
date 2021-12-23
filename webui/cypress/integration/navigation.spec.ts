import { pathEquals } from "../support/utilities"

describe("Navigation", () => {
    it("navigates through specified URLs when clicking Buttons", () => {
        cy.visit("/")

        cy.contains("Patch").click()
        pathEquals("/patch")

        // check that right tab is selected, even after reload
        cy.contains("Fixtures").should("have.attr", "aria-selected", "true")
        cy.contains("Fixture Types").should("have.attr", "aria-selected", "false")
        cy.reload()
        cy.contains("Fixtures").should("have.attr", "aria-selected", "true")

        cy.contains("Fixture Types").click()
        pathEquals("/patch/fixtureTypes")

        cy.contains("Fixture Types").should("have.attr", "aria-selected", "true")
        cy.reload()
        cy.contains("Fixture Types").should("have.attr", "aria-selected", "true")

        cy.contains("Fixtures").click()
        cy.contains("Fixtures").should("have.attr", "aria-selected", "true")
        pathEquals("/patch")

        cy.contains("Add New Fixture").click()
        pathEquals("/patch/newFixture")

        cy.contains("Esc").click()
        pathEquals("/patch")
        
        cy.contains("Esc").click()
        pathEquals("/")
    })
})