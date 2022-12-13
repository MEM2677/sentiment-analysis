import React from 'react';
import { render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import opinionMocks from 'components/__mocks__/opinionMocks';
import { apiOpinionsGet } from 'api/opinions';
import 'i18n/__mocks__/i18nMock';
import OpinionTableContainer from 'components/OpinionTableContainer';

jest.mock('api/opinions');

jest.mock('auth/withKeycloak', () => {
  const withKeycloak = Component => {
    return props => (
      <Component
        {...props} // eslint-disable-line react/jsx-props-no-spreading
        keycloak={{
          initialized: true,
          authenticated: true,
        }}
      />
    );
  };

  return withKeycloak;
});

jest.mock('components/pagination/withPagination', () => {
  const withPagination = Component => {
    return props => (
      <Component
        {...props} // eslint-disable-line react/jsx-props-no-spreading
        pagination={{
          onChangeItemsPerPage: () => {},
          onChangeCurrentPage: () => {},
        }}
      />
    );
  };

  return withPagination;
});

describe('OpinionTableContainer', () => {
  const errorMessageKey = 'error.dataLoading';

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('calls API', async () => {
    apiOpinionsGet.mockImplementation(() => Promise.resolve({ opinions: opinionMocks, count: 2 }));
    const { queryByText } = render(<OpinionTableContainer />);

    await wait(() => {
      expect(apiOpinionsGet).toHaveBeenCalledTimes(1);
      expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
    });
  });

  it('shows an error if the API call is not successful', async () => {
    apiOpinionsGet.mockImplementation(() => {
      throw new Error();
    });
    const { getByText } = render(<OpinionTableContainer />);

    wait(() => {
      expect(apiOpinionsGet).toHaveBeenCalledTimes(1);
      expect(getByText(errorMessageKey)).toBeInTheDocument();
    });
  });
});
