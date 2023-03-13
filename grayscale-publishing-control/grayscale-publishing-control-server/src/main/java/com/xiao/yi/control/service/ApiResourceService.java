package com.xiao.yi.control.service;

import com.xiao.yi.control.entry.ApiResource;
import com.xiao.yi.control.model.reponse.ResponseModel;

public interface ApiResourceService {

    ResponseModel<ApiResource> resources(ApiResource apiResource);

    ApiResource resource(ApiResource apiResource);

}
