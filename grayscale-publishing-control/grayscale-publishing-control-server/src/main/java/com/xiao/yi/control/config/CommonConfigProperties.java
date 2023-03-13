package com.xiao.yi.control.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaoyi
 * @since 2023/3/13
 */
@ConfigurationProperties(prefix = "common.properties")
@Component
public class CommonConfigProperties {

    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
