package com.xiao.yi.route;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ConditionalOnRibbonNacos;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoyi
 * @since 2023/3/1
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnRibbonNacos
public class GrayscalePublishingRibbonClient {

    @Autowired
    private PropertiesFactory propertiesFactory;

    @Bean
    public ServerList<?> ribbonServerList(IClientConfig config,
                                          NacosDiscoveryProperties nacosDiscoveryProperties) {
        if (this.propertiesFactory.isSet(ServerList.class, config.getClientName())) {
            ServerList serverList = this.propertiesFactory.get(ServerList.class, config,
                    config.getClientName());
            return serverList;
        }
        GrayscalePublishingNacosServerList serverList = new GrayscalePublishingNacosServerList(config, nacosDiscoveryProperties);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }
}
