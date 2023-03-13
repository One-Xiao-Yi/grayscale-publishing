package com.xiao.yi.control.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.xiao.yi.constants.enums.StaticRootChildDirEnums;
import com.xiao.yi.control.entry.StaticResource;
import com.xiao.yi.control.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
@Service
public class StaticResourceServiceImpl implements StaticResourceService {

    @Value("${grayscale.static.path}")
    private String grayscaleStaticPath;

    private File staticFileRootDir;

    @PostConstruct
    public void init() {
        Assert.notEmpty(grayscaleStaticPath, "静态资源文件目录未配置");
        staticFileRootDir = new File(grayscaleStaticPath);
        Assert.isTrue(staticFileRootDir.exists() && staticFileRootDir.isDirectory(), "静态资源文件目录配置错误: {}", grayscaleStaticPath);
    }

    @Override
    public Map<String, List<StaticResource>> rootResources() {
        File[] files = staticFileRootDir.listFiles();
        Assert.isTrue(null != files && files.length >= StaticRootChildDirEnums.values().length, "静态资源文件夹子文件夹有误");
        return Arrays.stream(files).collect(Collectors.toMap(item -> StaticRootChildDirEnums.of(item.getName()).getName(), this::fileChildrenToStaticResources));
    }

    @Override
    public List<StaticResource> childResources(String relativePath, String type) {
        relativePath = relativePath.replaceAll("/", Matcher.quoteReplacement(File.separator));
        File dir = new File(grayscaleStaticPath + File.separator + StaticRootChildDirEnums.of(type).getName() + File.separator + relativePath);
        Assert.isTrue(dir.exists() && dir.isDirectory(), "{}不存在或不是文件夹类型", relativePath);
        return fileChildrenToStaticResources(dir);
    }

    @Override
    public void deployGrayscaleStaticResource(MultipartFile file) {
        Assert.isTrue(StrUtil.isNotEmpty(file.getOriginalFilename()) && file.getOriginalFilename().endsWith(".zip"), "只支持上传zip文件");
        try (InputStream inputStream = file.getInputStream()) {
            ZipUtil.unzip(inputStream, staticFileRootDir, null);
        } catch (IOException e) {
            throw new RuntimeException("解压文件失败", e);
        }
    }

    private List<StaticResource> fileChildrenToStaticResources(File file) {
        File[] children = file.listFiles();
        if (null == children || children.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(children).map(this::fileToStaticResource).collect(Collectors.toList());
    }

    private StaticResource fileToStaticResource(File file) {
        StaticResource staticResource = new StaticResource();
        staticResource.setDirectory(file.isDirectory());
        staticResource.setName(file.getName());
        return staticResource;
    }
}
