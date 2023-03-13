package com.xiao.yi.control.controller;

import com.xiao.yi.control.entry.ApiResource;
import com.xiao.yi.control.entry.Rule;
import com.xiao.yi.control.model.reponse.ResponseModel;
import com.xiao.yi.control.service.RuleService;
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

    @Autowired
    private RuleService ruleService;

    @PostMapping("/setRules")
    public ResponseModel<ApiResource> setRules(@RequestBody Rule rule) {
        ruleService.setRules(rule);
        return ResponseModel.success();
    }

}
