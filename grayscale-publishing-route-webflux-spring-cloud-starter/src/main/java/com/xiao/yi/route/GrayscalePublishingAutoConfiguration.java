package com.xiao.yi.route;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoyi
 * @since 2023/3/1
 */
@Configuration(proxyBeanMethods = false)
@RibbonClients(defaultConfiguration = GrayscalePublishingRibbonClient.class)
public class GrayscalePublishingAutoConfiguration {

}
