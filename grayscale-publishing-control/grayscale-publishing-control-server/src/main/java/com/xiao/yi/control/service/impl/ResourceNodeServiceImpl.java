package com.xiao.yi.control.service.impl;

import com.xiao.yi.control.data.entry.ResourceNode;
import com.xiao.yi.control.data.enums.NodeTypeEnum;
import com.xiao.yi.control.data.enums.ResourceTypeEnum;
import com.xiao.yi.control.resource.ResourceFactory;
import com.xiao.yi.control.service.ResourceNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
@Service
public class ResourceNodeServiceImpl implements ResourceNodeService {

    @Autowired
    private ResourceFactory resourceFactory;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void registry(ResourceNode resourceNode) {
        resourceNode.setNodeType(NodeTypeEnum.UNREADY.getIndex());
        resourceNode.setResourceType(ResourceTypeEnum.API.getIndex());
        resourceFactory.registryNode(resourceNode);
    }

    @Override
    public void changeType(ResourceNode resourceNode) {
        resourceFactory.updateNode(resourceNode);
    }
}
