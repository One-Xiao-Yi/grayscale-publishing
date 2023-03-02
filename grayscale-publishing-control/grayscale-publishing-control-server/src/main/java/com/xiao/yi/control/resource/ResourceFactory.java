package com.xiao.yi.control.resource;

import cn.hutool.core.lang.Assert;
import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.entry.ResourceNode;
import com.xiao.yi.control.data.enums.ResourceTypeEnum;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaoyi
 * @since 2023/2/22
 */
@Component
public class ResourceFactory {

    private final Map<ResourceTypeEnum, ResourceApi> resourceApiMap;

    public ResourceFactory(List<ResourceApi> resourceApis) {
        this.resourceApiMap = resourceApis.stream().collect(Collectors.toMap(ResourceApi::type, item -> item));
    }

    public ResponseModel<Resource> resources(Resource resource) {
        Assert.notNull(resource.getResourceType(), "资源类型为空");
        return resourceApiMap.get(ResourceTypeEnum.of(resource.getResourceType())).resources(resource);
    }

    public void updateResource(Resource resource) {
        Assert.notNull(resource.getResourceType(), "资源类型为空");
        resourceApiMap.get(ResourceTypeEnum.of(resource.getResourceType())).updateResource(resource);
    }

    public void registryNode(ResourceNode resourceNode) {
        Assert.notNull(resourceNode.getResourceType(), "资源类型为空");
        Assert.notEmpty(resourceNode.getResourceName(), "资源名为空");
        resourceApiMap.get(ResourceTypeEnum.of(resourceNode.getResourceType())).registryNode(resourceNode);
    }

    public void updateNode(ResourceNode resourceNode) {
        Assert.notNull(resourceNode.getResourceType(), "资源类型为空");
        Assert.notEmpty(resourceNode.getResourceName(), "资源名为空");
        resourceApiMap.get(ResourceTypeEnum.of(resourceNode.getResourceType())).updateNode(resourceNode);
    }

    public Resource getResource(String resourceName, Integer resourceType) {
        Assert.notNull(resourceType, "资源类型为空");
        return resourceApiMap.get(ResourceTypeEnum.of(resourceType)).getResource(resourceName);
    }
}
