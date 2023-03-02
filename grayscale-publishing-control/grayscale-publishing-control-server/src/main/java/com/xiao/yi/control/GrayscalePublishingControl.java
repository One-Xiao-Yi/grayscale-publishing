package com.xiao.yi.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GrayscalePublishingControl {

    public static void main(String[] args) {
        SpringApplication.run(GrayscalePublishingControl.class, args);
    }

}
