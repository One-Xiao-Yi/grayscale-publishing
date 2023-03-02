package com.xiao.yi.control.resource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.entry.ResourceNode;
import com.xiao.yi.control.data.enums.NodeTypeEnum;
import com.xiao.yi.control.data.enums.ResourceTypeEnum;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import com.xiao.yi.control.utils.NacosOpenApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoyi
 * @since 2023/2/27
 */
@Component
public class ApiResource implements ResourceApi {

    @Autowired
    private NacosOpenApiUtils nacosOpenApiUtils;

    @Override
    public ResourceTypeEnum type() {
        return ResourceTypeEnum.API;
    }

    @Override
    public ResponseModel<Resource> resources(Resource resource) {
        ResponseModel<Resource> responseModel = ResponseModel.success();
        BeanUtil.copyProperties(resource, responseModel);
        JSONObject jsonObject = nacosOpenApiUtils.serviceList(resource);
        Integer total = jsonObject.getInt("count");
        JSONArray doms = jsonObject.getJSONArray("doms");
        List<Resource> resources = doms.stream().map(item -> {
            Resource create = new Resource();
            create.setResourceName(item.toString());
            create.setResourceType(type().getIndex());
            return create;
        }).collect(Collectors.toList());
        responseModel.setRows(resources);
        responseModel.setTotal(Long.valueOf(total));
        responseModel.setPageNo(resource.getPageNo());
        responseModel.setPageSize(resource.getPageSize());
        return responseModel;
    }

    @Override
    public void updateResource(Resource resource) {
        nacosOpenApiUtils.updateServiceMetadata(resource);
    }

    @Override
    public void registryNode(ResourceNode resourceNode) {
    }

    @Override
    public void updateNode(ResourceNode resourceNode) {
        nacosOpenApiUtils.updateServiceInstanceMetadata(resourceNode);
    }

    @Override
    public Resource getResource(String resourceName) {
        JSONObject jsonObject = nacosOpenApiUtils.service(resourceName);
        JSONArray hosts = jsonObject.getJSONArray("hosts");
        List<ResourceNode> nodes = hosts.stream().map(item -> {
            JSONObject host = ((JSONObject) item);
            ResourceNode resourceNode = JSONUtil.toBean(host, ResourceNode.class);
            resourceNode.setResourceName(resourceName);
            resourceNode.setResourceType(ResourceTypeEnum.API.getIndex());
            JSONObject metadata = host.getJSONObject("metadata");
            if (metadata.containsKey("nodeType")) {
                resourceNode.setNodeType(metadata.getInt("nodeType"));
            } else {
                resourceNode.setNodeType(NodeTypeEnum.UNREADY.getIndex());
            }
            return resourceNode;
        }).collect(Collectors.toList());
        Resource resource = new Resource();
        resource.setResourceName(resourceName);
        resource.setResourceType(ResourceTypeEnum.API.getIndex());
        resource.setNodes(nodes);
        return resource;
    }
}
