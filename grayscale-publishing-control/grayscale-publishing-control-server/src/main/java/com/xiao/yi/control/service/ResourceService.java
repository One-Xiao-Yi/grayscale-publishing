package com.xiao.yi.control.service;

import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.model.reponse.ResponseModel;

public interface ResourceService {

    ResponseModel<Resource> resources(Resource resource);

    Resource resource(Resource resource);

}
