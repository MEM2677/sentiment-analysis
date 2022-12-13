import PropTypes from 'prop-types';

export default PropTypes.shape({
  id: PropTypes.number,
  username: PropTypes.string,
  pageid: PropTypes.string,
  contentid: PropTypes.string,
  langcode: PropTypes.string,
  created: PropTypes.string,
  text: PropTypes.string,
  sentences: PropTypes.number,
  score: PropTypes.number,
  result: PropTypes.string,
});

export const formValues = PropTypes.shape({
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  username: PropTypes.string,
  pageid: PropTypes.string,
  contentid: PropTypes.string,
  langcode: PropTypes.string,
  created: PropTypes.oneOfType([PropTypes.string, PropTypes.instanceOf(Date)]),
  text: PropTypes.string,
  sentences: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  score: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  result: PropTypes.string,
});

export const formTouched = PropTypes.shape({
  id: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  username: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  pageid: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  contentid: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  langcode: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  created: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  text: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  sentences: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  score: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  result: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
});

export const formErrors = PropTypes.shape({
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  username: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  pageid: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  contentid: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  langcode: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  created: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  text: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  sentences: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  score: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  result: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
});
