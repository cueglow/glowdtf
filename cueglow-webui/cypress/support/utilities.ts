export function pathEquals(x: string) {
    return cy.location("pathname").should("equal", x)
}