package com.xiao.yi.control.resource;

import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.entry.ResourceNode;
import com.xiao.yi.control.data.enums.NodeTypeEnum;
import com.xiao.yi.control.data.enums.ResourceTypeEnum;
import com.xiao.yi.control.data.model.reponse.ResponseModel;

/**
 * @author xiaoyi
 * @since 2023/2/27
 */
public interface ResourceApi {

    ResourceTypeEnum type();

    ResponseModel<Resource> resources(Resource resource);

    void updateResource(Resource resource);

    void registryNode(ResourceNode resourceNode);

    void updateNode(ResourceNode resourceNode);

    Resource getResource(String resourceName);

}
