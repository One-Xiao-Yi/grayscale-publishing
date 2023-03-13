package com.xiao.yi.control.model.page;

public class PageModel {

    private Integer pageNo;

    private Integer pageSize;

    private Long total;

    public void clearParam() {
        pageNo = null;
        pageSize = null;
        total = null;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public boolean pageCheck() {
        return null != pageNo && null != pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
