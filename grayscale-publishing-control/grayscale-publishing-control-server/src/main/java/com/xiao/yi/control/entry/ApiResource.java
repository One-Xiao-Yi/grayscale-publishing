package com.xiao.yi.control.entry;

import com.xiao.yi.control.model.page.PageModel;

import java.util.List;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
public class ApiResource extends PageModel {

    private String resourceName;

    private Integer resourceType;

    private List<ApiResourceNode> nodes;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public List<ApiResourceNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ApiResourceNode> nodes) {
        this.nodes = nodes;
    }
}
