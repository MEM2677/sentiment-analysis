import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';

import 'components/__mocks__/i18n';
import OpinionDetails from 'components/OpinionDetails';
import opinionMock from 'components/__mocks__/opinionMocks';

describe('OpinionDetails component', () => {
  test('renders data in details widget', () => {
    const { getByText } = render(<OpinionDetails opinion={opinionMock} />);

    expect(getByText('entities.opinion.id')).toBeInTheDocument();
  });
});
