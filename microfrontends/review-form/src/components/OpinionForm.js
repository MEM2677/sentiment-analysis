import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { formValues, formTouched, formErrors } from 'components/__types__/opinion';
import { withFormik } from 'formik';
import { withTranslation } from 'react-i18next';
import { withStyles } from '@material-ui/core/styles';
import { compose } from 'recompose';
import * as Yup from 'yup';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import ConfirmationDialogTrigger from 'components/common/ConfirmationDialogTrigger';

const styles = theme => ({
  root: {
    margin: theme.spacing(3),
  },
  textField: {
    width: '100%',
  },
});
class OpinionForm extends PureComponent {
  constructor(props) {
    super(props);
    this.handleConfirmationDialogAction = this.handleConfirmationDialogAction.bind(this);
  }

  handleConfirmationDialogAction(action) {
    const { onDelete, values } = this.props;
    switch (action) {
      case ConfirmationDialogTrigger.CONFIRM: {
        onDelete(values);
        break;
      }
      default:
        break;
    }
  }

  render() {
    const {
      classes,
      values,
      touched,
      errors,
      handleChange,
      handleBlur,
      handleSubmit: formikHandleSubmit,
      onDelete,
      onCancelEditing,
      isSubmitting,
      t,
    } = this.props;

    const getHelperText = field => (errors[field] && touched[field] ? errors[field] : '');

    const handleSubmit = e => {
      e.stopPropagation(); // avoids double submission caused by react-shadow-dom-retarget-events
      formikHandleSubmit(e);
    };

    return (
      <form onSubmit={handleSubmit} className={classes.root} data-testid="opinion-form">
        <Grid container spacing={2}>
          <input type="hidden" id="opinion-id" data-testid="opinion-id" value={values.id} />
          <Grid item xs={12} sm={6}>
            <TextField
              id="opinion-pageid"
              error={errors.pageid && touched.pageid}
              helperText={getHelperText('pageid')}
              className={classes.textField}
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.pageid}
              name="pageid"
              label={t('entities.opinion.pageid')}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              id="opinion-contentid"
              error={errors.contentid && touched.contentid}
              helperText={getHelperText('contentid')}
              className={classes.textField}
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.contentid}
              name="contentid"
              label={t('entities.opinion.contentid')}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              id="opinion-username"
              error={errors.username && touched.username}
              helperText={getHelperText('username')}
              className={classes.textField}
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.username}
              name="username"
              label={t('entities.opinion.username')}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              id="opinion-langcode"
              error={errors.langcode && touched.langcode}
              helperText={getHelperText('langcode')}
              className={classes.textField}
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.langcode}
              name="langcode"
              label={t('entities.opinion.langcode')}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              id="opinion-text"
              error={errors.text && touched.text}
              helperText={getHelperText('text')}
              className={classes.textField}
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.text}
              name="text"
              label={t('entities.opinion.text')}
            />
          </Grid>
          {onDelete && (
            <ConfirmationDialogTrigger
              onCloseDialog={this.handleConfirmationDialogAction}
              dialog={{
                title: t('entities.opinion.deleteDialog.title'),
                description: t('entities.opinion.deleteDialog.description'),
                confirmLabel: t('common.yes'),
                discardLabel: t('common.no'),
              }}
              Renderer={({ onClick }) => (
                <Button onClick={onClick} disabled={isSubmitting}>
                  {t('common.delete')}
                </Button>
              )}
            />
          )}

          <Button onClick={onCancelEditing} disabled={isSubmitting} data-testid="cancel-btn">
            {t('common.cancel')}
          </Button>

          <Button type="submit" color="primary" disabled={isSubmitting} data-testid="submit-btn">
            {t('common.save')}
          </Button>
        </Grid>
      </form>
    );
  }
}

OpinionForm.propTypes = {
  classes: PropTypes.shape({
    root: PropTypes.string,
    textField: PropTypes.string,
    submitButton: PropTypes.string,
    button: PropTypes.string,
    downloadAnchor: PropTypes.string,
  }),
  values: formValues,
  touched: formTouched,
  errors: formErrors,
  handleChange: PropTypes.func.isRequired,
  handleBlur: PropTypes.func.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  onDelete: PropTypes.func,
  onCancelEditing: PropTypes.func,
  isSubmitting: PropTypes.bool.isRequired,
  t: PropTypes.func.isRequired,
  i18n: PropTypes.shape({ language: PropTypes.string }).isRequired,
};

OpinionForm.defaultProps = {
  onCancelEditing: () => {},
  classes: {},
  values: {},
  touched: {},
  errors: {},
  onDelete: null,
};

const emptyOpinion = {
  id: '',
  pageid: '',
  contentid: '',
  username: '',
  langcode: '',
  text: '',
};

const validationSchema = Yup.object().shape({
  id: Yup.number(),
  pageid: Yup.string(),
  contentid: Yup.string(),
  username: Yup.string(),
  langcode: Yup.string(),
  text: Yup.string(),
});

const formikBag = {
  mapPropsToValues: ({ opinion }) => opinion || emptyOpinion,

  enableReinitialize: true,

  validationSchema,

  handleSubmit: (values, { setSubmitting, props: { onSubmit } }) => {
    onSubmit(values);
    setSubmitting(false);
  },

  displayName: 'OpinionForm',
};

export default compose(
  withStyles(styles, { withTheme: true }),
  withTranslation(),
  withFormik(formikBag)
)(OpinionForm);
