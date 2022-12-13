import React from 'react';
import PropTypes from 'prop-types';
import { withTranslation } from 'react-i18next';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import opinionType from 'components/__types__/opinion';

const OpinionFieldTable = ({ t, i18n: { language }, opinion }) => (
  <Table>
    <TableHead>
      <TableRow>
        <TableCell>{t('common.name')}</TableCell>
        <TableCell>{t('common.value')}</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.id')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.id}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.username')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.username}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.pageid')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.pageid}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.contentid')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.contentid}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.langcode')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.langcode}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.created')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.created && new Date(opinion.created).toLocaleString(language)}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.text')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.text}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.sentences')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.sentences}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.score')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.score}</span>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell>
          <span>{t('entities.opinion.result')}</span>
        </TableCell>
        <TableCell>
          <span>{opinion.result}</span>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>
);

OpinionFieldTable.propTypes = {
  opinion: opinionType,
  t: PropTypes.func.isRequired,
  i18n: PropTypes.shape({
    language: PropTypes.string,
  }).isRequired,
};

OpinionFieldTable.defaultProps = {
  opinion: [],
};

export default withTranslation()(OpinionFieldTable);
