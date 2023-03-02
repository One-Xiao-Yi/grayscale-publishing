package com.xiao.yi.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiaoyi
 * @since 2023/2/24
 */
@FeignClient(value = "${consumer.feign.service}")
public interface ExampleFeign {

    @GetMapping("/nodeType")
    String consumerNodeType();

}
