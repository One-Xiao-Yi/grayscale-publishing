package com.xiao.yi.gateway.filter;

import com.xiao.yi.route.GrayscalePublishingConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author xiaoyi
 * @since 2023/3/2
 */
@Component
@EnableConfigurationProperties(LoadBalancerProperties.class)
public class GrayscalePublishingLoadBalancerClientFilter extends LoadBalancerClientFilter {
    public GrayscalePublishingLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        String host = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> headerList = headers.get(GrayscalePublishingConstants.GRAYSCALE_HEADER);
        if (null != headerList && !headerList.isEmpty() && GrayscalePublishingConstants.GRAYSCALE_HEADER_VALUE.equals(headerList.get(0))) {
            host += GrayscalePublishingConstants.GRAYSCALE_RESOURCE_NAME_PLACEHOLDER;
        }
        return loadBalancer.choose(host);
    }
}
