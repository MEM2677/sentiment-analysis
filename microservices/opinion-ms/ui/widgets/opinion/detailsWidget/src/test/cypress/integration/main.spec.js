import { customElementName, detailsTitle, entityIdCell } from '../support';

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

  describe('Details widget', () => {
    it('should load the page', () => {
      cy.get(customElementName).should('exist');
    });

    it('should display the right values', () => {
      cy.get(detailsTitle).should('be.visible').should('have.text', "Details about 'Opinion'");
      cy.get(entityIdCell).should('not.be.empty');
      cy.contains('entities.opinion.id').should('be.visible');
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
