package com.xiao.yi.control.controller;

import cn.hutool.json.JSONUtil;
import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.entry.Rule;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import com.xiao.yi.control.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoyi
 * @since 2023/3/2
 */
@RestController
@RequestMapping("/rules")
public class RuleController {

    private static final Logger logger = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleService ruleService;

    @PostMapping("/setRules")
    public ResponseModel<Resource> setRules(@RequestBody Rule rule) {
        logger.info("/resource/setRules: {}", JSONUtil.toJsonStr(rule));
        try {
            ruleService.setRules(rule);
            return ResponseModel.success();
        } catch (Exception e) {
            logger.error("/resource/setRules error", e);
            return ResponseModel.error(e);
        }
    }

}
