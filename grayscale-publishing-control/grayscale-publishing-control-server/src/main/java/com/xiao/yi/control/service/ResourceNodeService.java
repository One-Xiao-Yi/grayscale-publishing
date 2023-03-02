package com.xiao.yi.control.service;

import com.xiao.yi.control.data.entry.ResourceNode;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
public interface ResourceNodeService {

    void registry(ResourceNode resourceNode);

    void changeType(ResourceNode resourceNode);

}
