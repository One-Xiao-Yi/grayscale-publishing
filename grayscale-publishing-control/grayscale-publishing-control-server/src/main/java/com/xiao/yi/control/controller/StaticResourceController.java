package com.xiao.yi.control.controller;

import com.xiao.yi.control.entry.StaticResource;
import com.xiao.yi.control.model.reponse.ResponseModel;
import com.xiao.yi.control.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
@RestController
@RequestMapping("/static/resource")
public class StaticResourceController {

    @Autowired
    private StaticResourceService staticResourceService;

    @GetMapping("/rootResources")
    public ResponseModel<Map<String, List<StaticResource>>> rootResources() {
        Map<String, List<StaticResource>> rootResources = staticResourceService.rootResources();
        return ResponseModel.success(rootResources);
    }

    @GetMapping("/childResources")
    public ResponseModel<StaticResource> childResources(@RequestParam("relativePath") String relativePath, @RequestParam("type") String type) {
        List<StaticResource> resources = staticResourceService.childResources(relativePath, type);
        return ResponseModel.success(resources);
    }

    @PostMapping("/deployGrayscaleStaticResource")
    @CrossOrigin(value = {"http://127.0.0.1:8000"})
    public ResponseModel<StaticResource> deployGrayscaleStaticResource(MultipartFile file) {
        staticResourceService.deployGrayscaleStaticResource(file);
        return ResponseModel.success();
    }

}
