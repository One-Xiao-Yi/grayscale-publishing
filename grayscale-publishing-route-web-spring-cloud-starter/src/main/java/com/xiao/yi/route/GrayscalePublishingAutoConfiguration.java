package com.xiao.yi.route;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyi
 * @since 2023/3/1
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class})
@AutoConfigureAfter(NacosDiscoveryAutoConfiguration.class)
@RibbonClients(defaultConfiguration = GrayscalePublishingRibbonClient.class)
public class GrayscalePublishingAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(GrayscalePublishingAutoConfiguration.class);

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                logger.info("Feign request: {}", request.getRequestURI());
                String grayscaleHeader = request.getHeader(GrayscalePublishingConstants.GRAYSCALE_HEADER);
                if (grayscaleHeader != null && !"".equals(grayscaleHeader)) {
                    requestTemplate.header(GrayscalePublishingConstants.GRAYSCALE_HEADER, grayscaleHeader);
                }
            }
        };
    }

}
