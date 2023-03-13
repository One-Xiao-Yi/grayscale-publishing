// @ts-ignore
/* eslint-disable */
import {request} from '@umijs/max';

enum HttpMethod {
  Get, POST
}

enum ContentTypeHeader {
  JSON, FORM_DATA, FORM
}

const headers = (contextType: ContentTypeHeader) => {
  switch (contextType) {
    case ContentTypeHeader.JSON:
      return {'Content-Type': 'application/json',};
    case ContentTypeHeader.FORM_DATA:
      return {'Content-Type': 'multipart/form-data',};
    case ContentTypeHeader.FORM:
      return {'Content-Type': 'application/x-www-form-urlencoded',};
    default:
      return {};
  }
}

export async function executeRequest<T extends API.ResponseModel>(url: string,
                                                                  method: HttpMethod,
                                                                  body: any,
                                                                  headers: { [key: string]: any } | null,
                                                                  options?: { [key: string]: any }) {
  if (headers == null) {
    headers = {};
  }
  return request<T>('/control' + url, {
    method: HttpMethod[method],
    headers: headers,
    data: body,
    ...(options || {}),
  })
}

export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return executeRequest<API.LoginResult>('/login', HttpMethod.POST, body, headers(ContentTypeHeader.JSON), options);
}

export async function getResourceList(body: API.ApiResource | {}, options?: { [key: string]: any }) {
  return executeRequest<API.ResourceListResult>('/api/resource/list', HttpMethod.POST, body, headers(ContentTypeHeader.JSON), options);
}

export async function getResource(body: API.ApiResource | {}, options?: { [key: string]: any }) {
  return executeRequest<API.ResourceObjectResult>('/api/resource/resource', HttpMethod.POST, body, headers(ContentTypeHeader.JSON), options);
}

export async function changeResourceType(body: API.ApiResourceNode, options?: { [key: string]: any }) {
  return executeRequest<API.ResponseModel>('/api/resource/node/changeType', HttpMethod.POST, body, headers(ContentTypeHeader.JSON), options);
}

export async function setRules(body: API.Rule, options?: { [key: string]: any }) {
  return executeRequest<API.ResponseModel>('/rules/setRules', HttpMethod.POST, body, headers(ContentTypeHeader.JSON), options);
}

export async function rootResources(options?: { [key: string]: any }) {
  return executeRequest<API.RootResourcesResponse>('/static/resource/rootResources', HttpMethod.Get, {}, headers(ContentTypeHeader.JSON), options);
}

export async function childResources(relativePath: string, type: string, options?: { [key: string]: any }) {
  return executeRequest<API.StaticResourceListResponse>('/static/resource/childResources?relativePath=' + relativePath + "&type=" + type, HttpMethod.Get, {}, headers(ContentTypeHeader.JSON), options);
}

export async function getServerHost(options?: { [key: string]: any }) {
  return executeRequest<API.StringResponseModel>('/common/properties?propertyKey=serverHost', HttpMethod.Get, {}, headers(ContentTypeHeader.JSON), options);
}
