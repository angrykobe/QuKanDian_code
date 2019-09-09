package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/5/19.
 */

public class SearchBean {
    /**
     * pageSize : 10
     * pageNumber : 2
     * totalCount : 10
     */

    private int pageSize;
    private int pageNumber;
    private int totalCount;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
