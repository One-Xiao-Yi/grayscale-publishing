package com.xiao.yi.control.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiao.yi.constants.enums.NodeTypeEnum;
import com.xiao.yi.constants.enums.ResourceTypeEnum;
import com.xiao.yi.control.entry.ApiResource;
import com.xiao.yi.control.entry.ApiResourceNode;
import com.xiao.yi.control.model.reponse.ResponseModel;
import com.xiao.yi.control.service.ApiResourceService;
import com.xiao.yi.control.utils.NacosOpenApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoyi
 * @since 2023/2/16
 */
@Service
public class ApiResourceServiceImpl implements ApiResourceService {

    private static final Logger log = LoggerFactory.getLogger(ApiResourceServiceImpl.class);

    @Autowired
    private NacosOpenApiUtils nacosOpenApiUtils;

    @Override
    public ResponseModel<ApiResource> resources(ApiResource apiResource) {
        ResponseModel<ApiResource> responseModel = ResponseModel.success();
        BeanUtil.copyProperties(apiResource, responseModel);
        JSONObject jsonObject = nacosOpenApiUtils.serviceList(apiResource);
        Integer total = jsonObject.getInt("count");
        JSONArray doms = jsonObject.getJSONArray("doms");
        List<ApiResource> apiResources = doms.stream().map(item -> {
            com.xiao.yi.control.entry.ApiResource create = new com.xiao.yi.control.entry.ApiResource();
            create.setResourceName(item.toString());
            create.setResourceType(ResourceTypeEnum.API.getIndex());
            return create;
        }).collect(Collectors.toList());
        responseModel.setRows(apiResources);
        responseModel.setTotal(Long.valueOf(total));
        responseModel.setPageNo(apiResource.getPageNo());
        responseModel.setPageSize(apiResource.getPageSize());
        return responseModel;
    }

    @Override
    public ApiResource resource(ApiResource apiResource) {
        JSONObject jsonObject = nacosOpenApiUtils.service(apiResource.getResourceName());
        JSONArray hosts = jsonObject.getJSONArray("hosts");
        List<ApiResourceNode> nodes = hosts.stream().map(item -> {
            JSONObject host = ((JSONObject) item);
            ApiResourceNode apiResourceNode = JSONUtil.toBean(host, ApiResourceNode.class);
            apiResourceNode.setResourceName(apiResource.getResourceName());
            apiResourceNode.setResourceType(ResourceTypeEnum.API.getIndex());
            JSONObject metadata = host.getJSONObject("metadata");
            if (metadata.containsKey("nodeType")) {
                apiResourceNode.setNodeType(metadata.getInt("nodeType"));
            } else {
                apiResourceNode.setNodeType(NodeTypeEnum.UNREADY.getIndex());
            }
            return apiResourceNode;
        }).collect(Collectors.toList());
        apiResource.setResourceType(ResourceTypeEnum.API.getIndex());
        apiResource.setNodes(nodes);
        return apiResource;
    }
}
