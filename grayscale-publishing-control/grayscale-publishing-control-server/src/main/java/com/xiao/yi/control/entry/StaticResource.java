package com.xiao.yi.control.entry;

import com.xiao.yi.control.model.page.PageModel;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
public class StaticResource extends PageModel {

    private String name;

    private Boolean directory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDirectory() {
        return directory;
    }

    public void setDirectory(Boolean directory) {
        this.directory = directory;
    }
}
