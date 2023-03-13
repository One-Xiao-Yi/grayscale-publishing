package com.xiao.yi.constants.enums;

import java.util.Arrays;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
public enum StaticRootChildDirEnums {
    /**
     * 正式目录
     */
    NORMAL("normal"),
    /**
     * 测试目录、灰度目录
     */
    GRAYSCALE("grayscale");

    StaticRootChildDirEnums(String name) {
        this.name = name;
    }

    public static StaticRootChildDirEnums of(String name) {
        return Arrays.stream(values()).filter(v -> v.name.equals(name)).findAny().orElseThrow(() -> new RuntimeException("不存在的目录"));
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
