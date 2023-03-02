package com.xiao.yi.control.controller;

import cn.hutool.json.JSONUtil;
import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import com.xiao.yi.control.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoyi
 * @since 2023/2/16
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceNodeController.class);

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/list")
    public ResponseModel<Resource> list(@RequestBody Resource resource) {
        logger.info("/resource/list: {}", JSONUtil.toJsonStr(resource));
        try {
            return resourceService.resources(resource);
        } catch (Exception e) {
            logger.error("/resource/list error", e);
            return ResponseModel.error(e);
        }
    }

    @PostMapping("/resource")
    public ResponseModel<Resource> resource(@RequestBody Resource resource) {
        logger.info("/resource/resource: {}", JSONUtil.toJsonStr(resource));
        try {
            resource = resourceService.resource(resource);
            return ResponseModel.success(resource);
        } catch (Exception e) {
            logger.error("/resource/resource error", e);
            return ResponseModel.error(e);
        }
    }


}
