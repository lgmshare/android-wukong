package com.lgmshare.base.model;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/10/27 14:45
 * @email: lgmshare@gmail.com
 */
public class ListGroup<T> implements BaseBean {

    private static final long serialVersionUID = 1L;

    private int pageTotal;
    private int pageIndex;
    private int totalSize;
    private List<T> list;

    public ListGroup() {
        super();
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}