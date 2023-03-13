package com.xiao.yi.control.service.impl;

import com.xiao.yi.control.entry.ApiResourceNode;
import com.xiao.yi.control.service.ApiResourceNodeService;
import com.xiao.yi.control.utils.NacosOpenApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
@Service
public class ApiResourceNodeServiceImpl implements ApiResourceNodeService {

    @Autowired
    private NacosOpenApiUtils nacosOpenApiUtils;

    @Override
    public void changeType(ApiResourceNode apiResourceNode) {
        nacosOpenApiUtils.updateServiceInstanceMetadata(apiResourceNode);
    }
}
