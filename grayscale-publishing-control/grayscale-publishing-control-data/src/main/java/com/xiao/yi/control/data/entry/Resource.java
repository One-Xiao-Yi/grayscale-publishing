package com.xiao.yi.control.data.entry;

import com.xiao.yi.control.data.model.page.PageModel;

import java.util.List;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
public class Resource extends PageModel {

    private String resourceName;

    private Integer resourceType;

    private List<ResourceNode> nodes;

    private List<Rule> rules;

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

    public List<ResourceNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ResourceNode> nodes) {
        this.nodes = nodes;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
