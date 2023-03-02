package com.xiao.yi.gateway;

import com.xiao.yi.route.GrayscalePublishingRibbonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @author xiaoyi
 * @since 2023/2/28
 */
@SpringBootApplication(excludeName = "org.springframework.cloud.gateway.config.GatewayLoadBalancerClientAutoConfiguration")
@EnableDiscoveryClient
//@RibbonClients(defaultConfiguration = GrayscalePublishingRibbonClient.class)
public class GrayscalePublishingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrayscalePublishingGatewayApplication.class);
    }

}
