import { customElementName } from '../support';

describe('Main', () => {
  beforeEach(() => {
    cy.getOauth2Data();
    cy.get('@oauth2Data').then((oauth2Data) => {
      cy.keycloackLogin(oauth2Data, 'user');
    });
  });

  afterEach(() => {
    cy.get('@oauth2Data').then((oauth2Data) => {
      cy.keycloackLogout(oauth2Data);
    });
    cy.clearCache();
  });

  describe('Form widget', () => {
    it('should load the page', () => {
      cy.get(customElementName).should('exist');
    });

    it('should display all the entity fields in the component', () => {
      cy.contains('entities.opinion.username').should('be.visible');
      cy.contains('entities.opinion.pageid').should('be.visible');
      cy.contains('entities.opinion.contentid').should('be.visible');
      cy.contains('entities.opinion.langcode').should('be.visible');
      cy.contains('entities.opinion.created').should('be.visible');
      cy.contains('entities.opinion.text').should('be.visible');
      cy.contains('entities.opinion.sentences').should('be.visible');
      cy.contains('entities.opinion.score').should('be.visible');
      cy.contains('entities.opinion.result').should('be.visible');
    });
  });
});
