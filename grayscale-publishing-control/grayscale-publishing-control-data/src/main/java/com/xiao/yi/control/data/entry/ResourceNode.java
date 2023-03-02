package com.xiao.yi.control.data.entry;

import java.util.Objects;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
public class ResourceNode {

    private String resourceName;

    private Integer resourceType;

    private String ip;

    private Integer port;

    private Integer nodeType;

    private Long startTime;

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ResourceNode)) {
            return false;
        }
        ResourceNode node = (ResourceNode) obj;
        return Objects.equals(this.ip, node.ip) && Objects.equals(this.port, node.port);
    }

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
