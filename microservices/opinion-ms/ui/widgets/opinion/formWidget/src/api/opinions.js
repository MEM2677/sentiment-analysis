import { getDefaultOptions, request } from 'api/helpers';

const resource = 'api/opinions';

export const apiOpinionGet = async (serviceUrl, id) => {
  const url = `${serviceUrl}/${resource}/${id}`;
  const options = {
    ...getDefaultOptions(),
    method: 'GET',
  };
  return request(url, options);
};

export const apiOpinionPost = async (serviceUrl, opinion) => {
  const url = `${serviceUrl}/${resource}`;
  const options = {
    ...getDefaultOptions(),
    method: 'POST',
    body: opinion ? JSON.stringify(opinion) : null,
  };
  return request(url, options);
};

export const apiOpinionPut = async (serviceUrl, id, opinion) => {
  const url = `${serviceUrl}/${resource}/${id}`;
  const options = {
    ...getDefaultOptions(),
    method: 'PUT',
    body: opinion ? JSON.stringify(opinion) : null,
  };
  return request(url, options);
};

export const apiOpinionDelete = async (serviceUrl, id) => {
  const url = `${serviceUrl}/${resource}/${id}`;
  const options = {
    ...getDefaultOptions(),
    method: 'DELETE',
  };
  return request(url, options);
};
