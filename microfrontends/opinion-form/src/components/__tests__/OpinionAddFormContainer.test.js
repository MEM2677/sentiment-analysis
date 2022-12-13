import React from 'react';
import { fireEvent, render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { apiOpinionPost } from 'api/opinions';
import OpinionAddFormContainer from 'components/OpinionAddFormContainer';
import 'i18n/__mocks__/i18nMock';
import { opinionMockAdd as opinionMock } from 'components/__mocks__/opinionMocks';

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
jest.mock('@material-ui/pickers', () => {
  // eslint-disable-next-line react/prop-types
  const MockPicker = ({ id, value, name, label, onChange }) => {
    const handleChange = event => onChange(event.currentTarget.value);
    return (
      <span>
        <label htmlFor={id}>{label}</label>
        <input id={id} name={name} value={value || ''} onChange={handleChange} />
      </span>
    );
  };
  return {
    ...jest.requireActual('@material-ui/pickers'),
    DateTimePicker: MockPicker,
    DatePicker: MockPicker,
  };
});

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

describe('OpinionAddFormContainer', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const errorMessageKey = 'error.dataLoading';
  const successMessageKey = 'common.dataSaved';

  const onErrorMock = jest.fn();
  const onCreateMock = jest.fn();

  it('saves data', async () => {
    apiOpinionPost.mockImplementation(data => Promise.resolve(data));

    const { findByTestId, findByLabelText, queryByText, rerender } = render(
      <OpinionAddFormContainer onError={onErrorMock} onUpdate={onCreateMock} config={configMock} />
    );

    const usernameField = await findByLabelText('entities.opinion.username');
    fireEvent.change(usernameField, { target: { value: opinionMock.username } });
    const pageidField = await findByLabelText('entities.opinion.pageid');
    fireEvent.change(pageidField, { target: { value: opinionMock.pageid } });
    const contentidField = await findByLabelText('entities.opinion.contentid');
    fireEvent.change(contentidField, { target: { value: opinionMock.contentid } });
    const langcodeField = await findByLabelText('entities.opinion.langcode');
    fireEvent.change(langcodeField, { target: { value: opinionMock.langcode } });
    const createdField = await findByLabelText('entities.opinion.created');
    fireEvent.change(createdField, { target: { value: opinionMock.created } });
    const textField = await findByLabelText('entities.opinion.text');
    fireEvent.change(textField, { target: { value: opinionMock.text } });
    const sentencesField = await findByLabelText('entities.opinion.sentences');
    fireEvent.change(sentencesField, { target: { value: opinionMock.sentences } });
    const scoreField = await findByLabelText('entities.opinion.score');
    fireEvent.change(scoreField, { target: { value: opinionMock.score } });
    const resultField = await findByLabelText('entities.opinion.result');
    fireEvent.change(resultField, { target: { value: opinionMock.result } });
    rerender(
      <OpinionAddFormContainer onError={onErrorMock} onUpdate={onCreateMock} config={configMock} />
    );

    const saveButton = await findByTestId('submit-btn');

    fireEvent.click(saveButton);

    await wait(() => {
      expect(apiOpinionPost).toHaveBeenCalledTimes(1);
      expect(apiOpinionPost).toHaveBeenCalledWith('', opinionMock);

      expect(queryByText(successMessageKey)).toBeInTheDocument();

      expect(onErrorMock).toHaveBeenCalledTimes(0);
      expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
    });
  }, 7000);

  it('shows an error if data is not successfully saved', async () => {
    apiOpinionPost.mockImplementation(() => Promise.reject());

    const { findByTestId, findByLabelText, queryByText, rerender } = render(
      <OpinionAddFormContainer onError={onErrorMock} onUpdate={onCreateMock} config={configMock} />
    );

    const usernameField = await findByLabelText('entities.opinion.username');
    fireEvent.change(usernameField, { target: { value: opinionMock.username } });
    const pageidField = await findByLabelText('entities.opinion.pageid');
    fireEvent.change(pageidField, { target: { value: opinionMock.pageid } });
    const contentidField = await findByLabelText('entities.opinion.contentid');
    fireEvent.change(contentidField, { target: { value: opinionMock.contentid } });
    const langcodeField = await findByLabelText('entities.opinion.langcode');
    fireEvent.change(langcodeField, { target: { value: opinionMock.langcode } });
    const createdField = await findByLabelText('entities.opinion.created');
    fireEvent.change(createdField, { target: { value: opinionMock.created } });
    const textField = await findByLabelText('entities.opinion.text');
    fireEvent.change(textField, { target: { value: opinionMock.text } });
    const sentencesField = await findByLabelText('entities.opinion.sentences');
    fireEvent.change(sentencesField, { target: { value: opinionMock.sentences } });
    const scoreField = await findByLabelText('entities.opinion.score');
    fireEvent.change(scoreField, { target: { value: opinionMock.score } });
    const resultField = await findByLabelText('entities.opinion.result');
    fireEvent.change(resultField, { target: { value: opinionMock.result } });
    rerender(
      <OpinionAddFormContainer onError={onErrorMock} onUpdate={onCreateMock} config={configMock} />
    );

    const saveButton = await findByTestId('submit-btn');

    fireEvent.click(saveButton);

    await wait(() => {
      expect(apiOpinionPost).toHaveBeenCalledTimes(1);
      expect(apiOpinionPost).toHaveBeenCalledWith('', opinionMock);

      expect(queryByText(successMessageKey)).not.toBeInTheDocument();

      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(queryByText(errorMessageKey)).toBeInTheDocument();
    });
  }, 7000);
});
