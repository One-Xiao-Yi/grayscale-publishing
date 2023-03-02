package com.xiao.yi.example.controller;

import com.xiao.yi.example.feign.ExampleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyi
 * @since 2023/2/22
 */
@RestController
public class GrayscalePublishingController {

    @Value("${grayscale.publishing.node.type:normal}")
    private String nodeType;

    @Autowired
    private ExampleFeign exampleFeign;

    @GetMapping("/nodeType")
    public String nodeType(HttpServletRequest request) {
        String grayscalePublishing = request.getHeader("GRAYSCALE_PUBLISHING");
        if (null == grayscalePublishing || grayscalePublishing.isEmpty()) {
            grayscalePublishing = "normal";
        }
        return grayscalePublishing + "--" + nodeType;
    }

    @GetMapping("/consumerNodeType")
    public String consumerNodeType() {
        return exampleFeign.consumerNodeType();
    }

}
