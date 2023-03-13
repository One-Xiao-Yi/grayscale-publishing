package com.xiao.yi.control.controller;

import com.xiao.yi.control.entry.ApiResource;
import com.xiao.yi.control.model.reponse.ResponseModel;
import com.xiao.yi.control.service.ApiResourceService;
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
@RequestMapping("/api/resource")
public class ApiResourceController {

    @Autowired
    private ApiResourceService apiResourceService;

    @PostMapping("/list")
    public ResponseModel<ApiResource> list(@RequestBody ApiResource apiResource) {
        return apiResourceService.resources(apiResource);
    }

    @PostMapping("/resource")
    public ResponseModel<ApiResource> resource(@RequestBody ApiResource apiResource) {
        return ResponseModel.success(apiResource);
    }


}
