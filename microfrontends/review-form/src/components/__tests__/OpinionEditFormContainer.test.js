import React from 'react';
import { fireEvent, render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { apiOpinionGet, apiOpinionPut } from 'api/opinions';
import OpinionEditFormContainer from 'components/OpinionEditFormContainer';
import 'i18n/__mocks__/i18nMock';
import { opinionMockEdit as opinionMock } from 'components/__mocks__/opinionMocks';

const configMock = {
  systemParams: {
    api: {
      'opinion-api': {
        url: '',
      },
    },
  },
};

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

describe('OpinionEditFormContainer', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const errorMessageKey = 'error.dataLoading';
  const successMessageKey = 'common.dataSaved';

  const onErrorMock = jest.fn();
  const onUpdateMock = jest.fn();

  it('loads data', async () => {
    apiOpinionGet.mockImplementation(() => Promise.resolve(opinionMock));
    const { queryByText } = render(
      <OpinionEditFormContainer
        id="1"
        onError={onErrorMock}
        onUpdate={onUpdateMock}
        config={configMock}
      />
    );

    await wait(() => {
      expect(apiOpinionGet).toHaveBeenCalledTimes(1);
      expect(apiOpinionGet).toHaveBeenCalledWith('', '1');
      expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
      expect(onErrorMock).toHaveBeenCalledTimes(0);
    });
  }, 7000);

  it('saves data', async () => {
    apiOpinionGet.mockImplementation(() => Promise.resolve(opinionMock));
    apiOpinionPut.mockImplementation(() => Promise.resolve(opinionMock));

    const { findByTestId, queryByText } = render(
      <OpinionEditFormContainer
        id="1"
        onError={onErrorMock}
        onUpdate={onUpdateMock}
        config={configMock}
      />
    );

    const saveButton = await findByTestId('submit-btn');

    fireEvent.click(saveButton);

    await wait(() => {
      expect(apiOpinionPut).toHaveBeenCalledTimes(1);
      expect(apiOpinionPut).toHaveBeenCalledWith('', opinionMock.id, opinionMock);
      expect(queryByText(successMessageKey)).toBeInTheDocument();
      expect(onErrorMock).toHaveBeenCalledTimes(0);
      expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
    });
  }, 7000);

  it('shows an error if data is not successfully loaded', async () => {
    apiOpinionGet.mockImplementation(() => Promise.reject());
    const { queryByText } = render(
      <OpinionEditFormContainer
        id="1"
        onError={onErrorMock}
        onUpdate={onUpdateMock}
        config={configMock}
      />
    );

    await wait(() => {
      expect(apiOpinionGet).toHaveBeenCalledTimes(1);
      expect(apiOpinionGet).toHaveBeenCalledWith('', '1');
      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(queryByText(errorMessageKey)).toBeInTheDocument();
      expect(queryByText(successMessageKey)).not.toBeInTheDocument();
    });
  }, 7000);

  it('shows an error if data is not successfully saved', async () => {
    apiOpinionGet.mockImplementation(() => Promise.resolve(opinionMock));
    apiOpinionPut.mockImplementation(() => Promise.reject());
    const { findByTestId, getByText } = render(
      <OpinionEditFormContainer id="1" onError={onErrorMock} config={configMock} />
    );

    const saveButton = await findByTestId('submit-btn');

    fireEvent.click(saveButton);

    await wait(() => {
      expect(apiOpinionGet).toHaveBeenCalledTimes(1);
      expect(apiOpinionGet).toHaveBeenCalledWith('', '1');

      expect(apiOpinionPut).toHaveBeenCalledTimes(1);
      expect(apiOpinionPut).toHaveBeenCalledWith('', opinionMock.id, opinionMock);

      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(getByText(errorMessageKey)).toBeInTheDocument();
    });
  }, 7000);
});
