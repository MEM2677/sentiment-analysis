import React from 'react';
import { render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import 'components/__mocks__/i18n';
import { apiOpinionGet } from 'api/opinion';
import opinionApiGetResponseMock from 'components/__mocks__/opinionMocks';
import OpinionDetailsContainer from 'components/OpinionDetailsContainer';

jest.mock('api/opinion');

jest.mock('auth/withKeycloak', () => {
  const withKeycloak = (Component) => {
    return (props) => (
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

beforeEach(() => {
  apiOpinionGet.mockClear();
});

describe('OpinionDetailsContainer component', () => {
  test('requests data when component is mounted', async () => {
    apiOpinionGet.mockImplementation(() => Promise.resolve(opinionApiGetResponseMock));

    render(<OpinionDetailsContainer id="1" />);

    await wait(() => {
      expect(apiOpinionGet).toHaveBeenCalledTimes(1);
    });
  });

  test('data is shown after mount API call', async () => {
    apiOpinionGet.mockImplementation(() => Promise.resolve(opinionApiGetResponseMock));

    const { getByText } = render(<OpinionDetailsContainer id="1" />);

    await wait(() => {
      expect(apiOpinionGet).toHaveBeenCalledTimes(1);
      expect(getByText('entities.opinion.id')).toBeInTheDocument();
      expect(getByText('entities.opinion.username')).toBeInTheDocument();
      expect(getByText('entities.opinion.pageid')).toBeInTheDocument();
      expect(getByText('entities.opinion.contentid')).toBeInTheDocument();
      expect(getByText('entities.opinion.langcode')).toBeInTheDocument();
      expect(getByText('entities.opinion.created')).toBeInTheDocument();
      expect(getByText('entities.opinion.text')).toBeInTheDocument();
      expect(getByText('entities.opinion.sentences')).toBeInTheDocument();
      expect(getByText('entities.opinion.score')).toBeInTheDocument();
      expect(getByText('entities.opinion.result')).toBeInTheDocument();
    });
  });

  test('error is shown after failed API call', async () => {
    const onErrorMock = jest.fn();
    apiOpinionGet.mockImplementation(() => Promise.reject());

    const { getByText } = render(<OpinionDetailsContainer id="1" onError={onErrorMock} />);

    await wait(() => {
      expect(apiOpinionGet).toHaveBeenCalledTimes(1);
      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(getByText('error.dataLoading')).toBeInTheDocument();
    });
  });
});
