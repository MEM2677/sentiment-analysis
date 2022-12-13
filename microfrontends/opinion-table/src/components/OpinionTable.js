import React from 'react';
import PropTypes from 'prop-types';
import { withTranslation } from 'react-i18next';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';

import opinionType from 'components/__types__/opinion';

const styles = {
  tableRoot: {
    marginTop: '10px',
  },
  rowRoot: {
    cursor: 'pointer',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
  },
  noItems: {
    margin: '15px',
  },
};

const OpinionTable = ({ items, onSelect, classes, t, i18n, Actions }) => {
  const tableRows = items.map(item => (
    <TableRow hover className={classes.rowRoot} key={item.id} onClick={() => onSelect(item)}>
      <TableCell>
        <span>{item.id}</span>
      </TableCell>
      <TableCell>
        <span>{item.username}</span>
      </TableCell>
      <TableCell>
        <span>{item.pageid}</span>
      </TableCell>
      <TableCell>
        <span>{item.contentid}</span>
      </TableCell>
      <TableCell>
        <span>{item.langcode}</span>
      </TableCell>
      <TableCell>
        <span>{new Date(item.created).toLocaleString(i18n.language)}</span>
      </TableCell>
      <TableCell>
        <span>{item.text}</span>
      </TableCell>
      <TableCell>
        <span>{item.sentences}</span>
      </TableCell>
      <TableCell>
        <span>{item.score}</span>
      </TableCell>
      <TableCell>
        <span>{item.result}</span>
      </TableCell>
      {Actions && (
        <TableCell>
          <Actions item={item} />
        </TableCell>
      )}
    </TableRow>
  ));

  return items.length ? (
    <Table className={classes.tableRoot} stickyHeader>
      <TableHead>
        <TableRow>
          <TableCell>
            <span>{t('entities.opinion.id')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.username')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.pageid')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.contentid')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.langcode')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.created')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.text')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.sentences')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.score')}</span>
          </TableCell>
          <TableCell>
            <span>{t('entities.opinion.result')}</span>
          </TableCell>
          {Actions && <TableCell />}
        </TableRow>
      </TableHead>
      <TableBody>{tableRows}</TableBody>
    </Table>
  ) : (
    <div className={classes.noItems}>{t('entities.opinion.noItems')}</div>
  );
};

OpinionTable.propTypes = {
  items: PropTypes.arrayOf(opinionType).isRequired,
  onSelect: PropTypes.func,
  classes: PropTypes.shape({
    rowRoot: PropTypes.string,
    tableRoot: PropTypes.string,
    noItems: PropTypes.string,
  }).isRequired,
  t: PropTypes.func.isRequired,
  i18n: PropTypes.shape({ language: PropTypes.string }).isRequired,
  Actions: PropTypes.oneOfType([PropTypes.string, PropTypes.func]),
};

OpinionTable.defaultProps = {
  onSelect: () => {},
  Actions: null,
};

export default withStyles(styles)(withTranslation()(OpinionTable));
