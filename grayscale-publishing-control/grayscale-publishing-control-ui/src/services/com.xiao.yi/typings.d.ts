// @ts-ignore
/* eslint-disable */

declare namespace API {
  type PageParams = {
    current?: number;
    pageSize?: number;
    total?: number
  };

  type ResponseModel = PageParams & {
    msg?: string,
    success: boolean,
    code?: number,
  }

  type LoginParams = {
    username?: string;
    password?: string;
  };

  type LoginResult = ResponseModel & {
    data: string
  };

  type Resource = {
    resourceName: string,
    resourceType: type,
    nodes?: Node[]
  }

  type Node = {
    resourceName?: string;
    resourceType?: number;
    ip?: string;
    startTime?: number;
    port?: number;
    state?: number;
    nodeType?: number;
  }

  type Rule = {
    ruleType: number,
    resources: string[]
  }

  type ClientIpResponse = ResponseModel & {
    data: string
  }

  type ResourceListResult = ResponseModel & {
    rows?: any
  }

  type ResourceObjectResult = ResponseModel & {
    data?: Resource
  }
}
