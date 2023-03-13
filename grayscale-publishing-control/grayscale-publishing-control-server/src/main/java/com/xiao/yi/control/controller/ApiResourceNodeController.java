package com.xiao.yi.control.controller;

import com.xiao.yi.control.entry.ApiResourceNode;
import com.xiao.yi.control.model.reponse.ResponseModel;
import com.xiao.yi.control.service.ApiResourceNodeService;
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
@RequestMapping("/api/resource/node")
public class ApiResourceNodeController {

    @Autowired
    private ApiResourceNodeService apiResourceNodeService;

    @PostMapping("/changeType")
    public ResponseModel<Void> changeType(@RequestBody ApiResourceNode node) {
        apiResourceNodeService.changeType(node);
        return ResponseModel.success();
    }

}
