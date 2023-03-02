package com.xiao.yi.control.controller;

import cn.hutool.json.JSONUtil;
import com.xiao.yi.control.data.entry.ResourceNode;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import com.xiao.yi.control.service.ResourceNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoyi
 * @since 2023/2/15
 */
@RestController
@RequestMapping("/resource/node")
public class ResourceNodeController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceNodeController.class);

    @Autowired
    private ResourceNodeService resourceNodeService;

    @PostMapping("/registry")
    public ResponseModel<Void> registry(@RequestBody ResourceNode node) {
        logger.info("/resource/node/registry: {}", JSONUtil.toJsonStr(node));
        try {
            resourceNodeService.registry(node);
            return ResponseModel.success();
        } catch (Exception e) {
            logger.error("/resource/node/registry error", e);
            return ResponseModel.error(e);
        }
    }

    @PostMapping("/changeType")
    public ResponseModel<Void> changeType(@RequestBody ResourceNode node) {
        logger.info("/resource/node/changeType: {}", JSONUtil.toJsonStr(node));
        try {
            resourceNodeService.changeType(node);
            return ResponseModel.success();
        } catch (Exception e) {
            logger.error("/resource/node/changeType error", e);
            return ResponseModel.error(e);
        }
    }

}
