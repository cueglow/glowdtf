// File for custom Cypress testing utilities
//
// Add Custom Cypress Commands in `commands.ts` instead

export function pathEquals(x: string) {
    return cy.location("pathname").should("equal", x)
}