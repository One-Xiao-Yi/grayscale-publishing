package com.xiao.yi.constants.enums;

import java.util.Arrays;

public enum NodeTypeEnum {

    /**
     * 0 未就绪
     */
    UNREADY(0, "未就绪"),
    /**
     * 1 测试节点
     */
    DEV(1, "测试节点"),
    /**
     * 2 正式节点
     */
    PROD(2, "正式节点");


    NodeTypeEnum(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public static NodeTypeEnum of(int index) {
        return Arrays.stream(values()).filter(v -> v.index == index).findAny().orElseThrow(() -> new RuntimeException("不存在的节点类型"));
    }

    private int index;
    private String text;

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
