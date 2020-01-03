package com.hj.app.utils;

import java.util.List;

public class PageInfo<T> {
    private int pageNow = 1;  //当前页
    private int pageSize = 2;  //每页多少条
    private int totalPage;  //总页数
    private int count;  //总条数
    private List<T> list;  //数据

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        if (count > 0) {
            this.totalPage = count % this.pageSize == 0 ? count / this.pageSize : count / this.pageSize + 1;
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
