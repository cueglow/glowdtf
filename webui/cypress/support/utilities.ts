// File for custom Cypress testing utilities
//
// Add Custom Cypress Commands in `commands.ts` instead

export function pathEquals(x: string) {
    return cy.location("pathname").should("equal", x)
}

/**
 * Search for a label text and return its associated input. 
 */
export function getInputByLabel(label: string) {
    return cy
      .contains('label', label)
      .invoke('attr', 'for')
      .then((id) => {
        cy.get('#' + id)
      })
  }