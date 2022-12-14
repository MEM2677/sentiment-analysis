import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render, wait } from '@testing-library/react';
import i18n from 'i18n/__mocks__/i18nMock';
import { opinionMockEdit as opinionMock } from 'components/__mocks__/opinionMocks';
import OpinionForm from 'components/OpinionForm';
import { createMuiTheme } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/styles';

const theme = createMuiTheme();

describe('Opinion Form', () => {
  it('shows form', () => {
    const { getByLabelText, getByTestId } = render(
      <ThemeProvider theme={theme}>
        <OpinionForm opinion={opinionMock} />
      </ThemeProvider>
    );

    expect(getByTestId('opinion-id').value).toBe(opinionMock.id.toString());
    expect(getByLabelText('entities.opinion.pageid').value).toBe(opinionMock.pageid);
    expect(getByLabelText('entities.opinion.contentid').value).toBe(opinionMock.contentid);
    expect(getByLabelText('entities.opinion.username').value).toBe(opinionMock.username);
    expect(getByLabelText('entities.opinion.langcode').value).toBe(opinionMock.langcode);
    expect(getByLabelText('entities.opinion.text').value).toBe(opinionMock.text);
  });

  it('submits form', async () => {
    const handleSubmit = jest.fn();
    const { getByTestId } = render(
      <ThemeProvider theme={theme}>
        <OpinionForm opinion={opinionMock} onSubmit={handleSubmit} />
      </ThemeProvider>
    );

    const form = getByTestId('opinion-form');
    fireEvent.submit(form);

    await wait(() => {
      expect(handleSubmit).toHaveBeenCalledTimes(1);
    });
  });
});
