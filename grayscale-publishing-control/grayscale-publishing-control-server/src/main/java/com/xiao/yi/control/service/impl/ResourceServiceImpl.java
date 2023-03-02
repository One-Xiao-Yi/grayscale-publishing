package com.xiao.yi.control.service.impl;


import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import com.xiao.yi.control.resource.ResourceFactory;
import com.xiao.yi.control.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaoyi
 * @since 2023/2/16
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceFactory resourceFactory;

    @Override
    public ResponseModel<Resource> resources(Resource resource) {
        return resourceFactory.resources(resource);
    }

    @Override
    public Resource resource(Resource resource) {
        return resourceFactory.getResource(resource.getResourceName(), resource.getResourceType());
    }
}
