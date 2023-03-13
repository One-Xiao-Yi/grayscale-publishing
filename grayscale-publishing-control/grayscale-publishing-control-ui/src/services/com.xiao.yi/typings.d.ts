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

  type StringResponseModel = ResponseModel & {
    data: string
  }

  type LoginParams = {
    username?: string;
    password?: string;
  };

  type LoginResult = ResponseModel & {
    data: string
  };

  type ApiResource = {
    resourceName: string,
    resourceType: type,
    nodes?: ApiResourceNode[]
  }

  type ApiResourceNode = {
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
    data?: ApiResource
  }

  type StaticResource = {
    name: string,
    directory: boolean,

    children?: StaticResource[]
  }

  type RootResources = {
    normal: StaticResource[],
    grayscale: StaticResource[],
  }

  type StaticResourceObjectResponse = ResponseModel & {
    data: StaticResource
  }

  type StaticResourceListResponse = ResponseModel & {
    rows: StaticResource[]
  }

  type RootResourcesResponse = ResponseModel & {
    data: RootResources
  }
}
