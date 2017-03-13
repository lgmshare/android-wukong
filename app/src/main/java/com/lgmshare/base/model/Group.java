package com.lgmshare.base.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/10/27 14:45
 * @email: lgmshare@gmail.com
 */
public abstract class Group<T> extends ArrayList<T> implements BaseBean {

    private static final long serialVersionUID = 1L;

    private int pageTotal;
    private int pageIndex;
    private int totalSize;

    public Group() {
        super();
    }

    public Group(Collection<T> collection) {
        super(collection);
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
}