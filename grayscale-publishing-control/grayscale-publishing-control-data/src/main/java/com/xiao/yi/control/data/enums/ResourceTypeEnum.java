package com.xiao.yi.control.data.enums;

import java.util.Arrays;

/**
 * @author xiaoyi
 * @since 2023/2/21
 */
public enum ResourceTypeEnum {

    /**
     * 1 api接口资源
     */
    API(1, "API"),
    /**
     * 2 静态资源文件
     */
    STATIC(2, "静态资源");

    private int index;
    private String text;

    ResourceTypeEnum(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public static ResourceTypeEnum of(int index) {
        return Arrays.stream(values()).filter(v -> v.index == index).findAny().orElseThrow(() -> new RuntimeException("不存在的资源类型"));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
