import React from 'react';
import PropTypes from 'prop-types';
import { withTranslation } from 'react-i18next';
import Box from '@material-ui/core/Box';

import opinionType from 'components/__types__/opinion';
import OpinionFieldTable from 'components/opinion-field-table/OpinionFieldTable';

const OpinionDetails = ({ t, opinion }) => {
  return (
    <Box>
      <h3 data-testid="details_title">
        {t('common.widgetName', {
          widgetNamePlaceholder: 'Opinion',
        })}
      </h3>
      <OpinionFieldTable opinion={opinion} />
    </Box>
  );
};

OpinionDetails.propTypes = {
  opinion: opinionType,
  t: PropTypes.func.isRequired,
};

OpinionDetails.defaultProps = {
  opinion: {},
};

export default withTranslation()(OpinionDetails);
