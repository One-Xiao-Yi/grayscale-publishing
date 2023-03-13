package com.xiao.yi.control.service;

import com.xiao.yi.control.entry.StaticResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
public interface StaticResourceService {

    Map<String, List<StaticResource>> rootResources();

    List<StaticResource> childResources(String relativePath, String type);

    void deployGrayscaleStaticResource(MultipartFile file);
}
