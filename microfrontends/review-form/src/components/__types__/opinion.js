import PropTypes from 'prop-types';

export default PropTypes.shape({
  id: PropTypes.number,
  pageid: PropTypes.string,
  contentid: PropTypes.string,
  username: PropTypes.string,
  langcode: PropTypes.string,
  text: PropTypes.string,
});

export const formValues = PropTypes.shape({
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  pageid: PropTypes.string,
  contentid: PropTypes.string,
  username: PropTypes.string,
  langcode: PropTypes.string,
  text: PropTypes.string,
});

export const formTouched = PropTypes.shape({
  id: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  pageid: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  contentid: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  username: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  langcode: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
  text: PropTypes.oneOfType([PropTypes.bool, PropTypes.shape()]),
});

export const formErrors = PropTypes.shape({
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  pageid: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  contentid: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  username: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  langcode: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  text: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
});
