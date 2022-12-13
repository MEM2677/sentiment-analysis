import { customElementName } from '../support';

describe('Main', () => {
  beforeEach(() => {
    cy.getOauth2Data();
    cy.get('@oauth2Data').then(oauth2Data => {
      cy.keycloackLogin(oauth2Data, 'user');
    });
  });

  afterEach(() => {
    cy.get('@oauth2Data').then(oauth2Data => {
      cy.keycloackLogout(oauth2Data);
    });
    cy.clearCache();
  });

  describe('Table widget', () => {
    it('should load the page', () => {
      cy.get(customElementName).should('exist');
    });

    it('should display all the entity fields in the component', () => {
      cy.contains('entities.opinion.id')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.username')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.pageid')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.contentid')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.langcode')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.created')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.text')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.sentences')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.score')
        .scrollIntoView()
        .should('be.visible');
      cy.contains('entities.opinion.result')
        .scrollIntoView()
        .should('be.visible');
    });
  });
});
