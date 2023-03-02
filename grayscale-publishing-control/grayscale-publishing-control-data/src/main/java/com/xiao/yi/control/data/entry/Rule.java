package com.xiao.yi.control.data.entry;

import java.util.List;
import java.util.Objects;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
public class Rule {

    private Integer ruleType;

    private List<String> resources;

    @Override
    public int hashCode() {
        return Objects.hash(ruleType, resources);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rule)) {
            return false;
        }
        Rule rule = (Rule) obj;
        return Objects.equals(ruleType, rule.ruleType)
                && Objects.equals(resources, rule.resources);
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
