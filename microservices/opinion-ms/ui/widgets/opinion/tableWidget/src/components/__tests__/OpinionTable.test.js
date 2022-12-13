import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import i18n from 'components/__mocks__/i18n';
import opinionMocks from 'components/__mocks__/opinionMocks';
import OpinionTable from 'components/OpinionTable';

describe('OpinionTable', () => {
  it('shows opinions', () => {
    const { getByText } = render(<OpinionTable items={opinionMocks} />);

    expect(getByText(opinionMocks[0].id.toString())).toBeInTheDocument();
    expect(getByText(opinionMocks[1].id.toString())).toBeInTheDocument();

    expect(getByText(opinionMocks[0].username)).toBeInTheDocument();
    expect(getByText(opinionMocks[1].username)).toBeInTheDocument();

    expect(getByText(opinionMocks[0].pageid)).toBeInTheDocument();
    expect(getByText(opinionMocks[1].pageid)).toBeInTheDocument();

    expect(getByText(opinionMocks[0].contentid)).toBeInTheDocument();
    expect(getByText(opinionMocks[1].contentid)).toBeInTheDocument();

    expect(getByText(opinionMocks[0].langcode)).toBeInTheDocument();
    expect(getByText(opinionMocks[1].langcode)).toBeInTheDocument();

    expect(
      getByText(new Date(opinionMocks[0].created).toLocaleString(i18n.language))
    ).toBeInTheDocument();
    expect(
      getByText(new Date(opinionMocks[1].created).toLocaleString(i18n.language))
    ).toBeInTheDocument();

    expect(getByText(opinionMocks[0].text)).toBeInTheDocument();
    expect(getByText(opinionMocks[1].text)).toBeInTheDocument();

    expect(getByText(opinionMocks[0].sentences.toString())).toBeInTheDocument();
    expect(getByText(opinionMocks[1].sentences.toString())).toBeInTheDocument();

    expect(getByText(opinionMocks[0].score.toString())).toBeInTheDocument();
    expect(getByText(opinionMocks[1].score.toString())).toBeInTheDocument();
  });

  it('shows no opinions message', () => {
    const { queryByText } = render(<OpinionTable items={[]} />);

    expect(queryByText(opinionMocks[0].id.toString())).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].id.toString())).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].username)).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].username)).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].pageid)).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].pageid)).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].contentid)).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].contentid)).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].langcode)).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].langcode)).not.toBeInTheDocument();

    expect(
      queryByText(new Date(opinionMocks[0].created).toLocaleString(i18n.language))
    ).not.toBeInTheDocument();
    expect(
      queryByText(new Date(opinionMocks[1].created).toLocaleString(i18n.language))
    ).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].text)).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].text)).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].sentences.toString())).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].sentences.toString())).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].score.toString())).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].score.toString())).not.toBeInTheDocument();

    expect(queryByText(opinionMocks[0].result)).not.toBeInTheDocument();
    expect(queryByText(opinionMocks[1].result)).not.toBeInTheDocument();

    expect(queryByText('entities.opinion.noItems')).toBeInTheDocument();
  });

  it('calls onSelect when the user clicks a table row', () => {
    const onSelectMock = jest.fn();
    const { getByText } = render(<OpinionTable items={opinionMocks} onSelect={onSelectMock} />);

    fireEvent.click(getByText(opinionMocks[0].id.toString()));
    expect(onSelectMock).toHaveBeenCalledTimes(1);
  });
});
