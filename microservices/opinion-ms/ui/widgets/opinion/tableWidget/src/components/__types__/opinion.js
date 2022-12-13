import PropTypes from 'prop-types';

const opinionType = PropTypes.shape({
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

export default opinionType;
