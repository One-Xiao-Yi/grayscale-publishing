package com.xiao.yi.control.data.api;


import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.entry.Rule;
import com.xiao.yi.control.data.model.reponse.ResponseModel;

import java.util.List;

/**
 * @author xiaoyi
 * @since 2023/2/21
 */
public interface ResourceDataService {

    /**
     * 分页获取资源列表
     *
     * @param resource 分页
     * @return 资源列表
     */
    ResponseModel<Resource> resources(Resource resource);

    /**
     * 存储资源
     *
     * @param resource 分页
     */
    void put(Resource resource);

    /**
     * 获取单个资源
     *
     * @param resourceName 资源名
     * @param resourceType 资源类型
     * @return 资源
     */
    Resource get(String resourceName, Integer resourceType);

}
