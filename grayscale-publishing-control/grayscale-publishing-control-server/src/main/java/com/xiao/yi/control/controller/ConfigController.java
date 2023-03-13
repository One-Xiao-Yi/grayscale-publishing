package com.xiao.yi.control.controller;

import com.xiao.yi.control.config.CommonConfigProperties;
import com.xiao.yi.control.model.reponse.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoyi
 * @since 2023/3/13
 */
@RestController
@RequestMapping("/common")
public class ConfigController {

    @Autowired
    private CommonConfigProperties commonConfigProperties;

    @GetMapping("/properties")
    public ResponseModel<String> properties(@RequestParam("propertyKey") String propertyKey) {
        return ResponseModel.success(commonConfigProperties.getParams().get(propertyKey));
    }

}
