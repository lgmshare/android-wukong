package com.lgmshare.base.widget;


import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

import com.lgmshare.base.model.ListGroup;
import com.lgmshare.base.ui.adapter.InnerBaseAdapter;

public class XListViewManage<T> {

    public static final int MODE_REFRESH = 0;
    public static final int MODE_LOAD = 1;

    private XListView mXListView;
    private InnerBaseAdapter<T> mAdapter;

    public void onRefresh(ListGroup<T> group) {
        if (group.getList() != null){
            mAdapter.setList(group.getList());
        }
        if (group.getTotalSize() == mAdapter.getCount()) {
            mXListView.setPullLoadEnable(false);
        } else {
            mXListView.setPullLoadEnable(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void onLoadMore(ListGroup<T> group) {
        mAdapter.addList(group.getList());
        if (group.getTotalSize() == mAdapter.getCount()) {
            mXListView.setPullLoadEnable(false);
        } else {
            mXListView.setPullLoadEnable(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void onRefreshComplete() {
        mXListView.onRefreshComplete();
    }

    public void onLoadMoreComplete() {
        mXListView.onLoadMoreComplete();
    }

    public void setXListView(XListView xListView, InnerBaseAdapter adapter) {
        this.mXListView = xListView;
        /*ListAdapter listAdapter = xListView.getAdapter();
        if (listAdapter instanceof HeaderViewListAdapter){
            this.mAdapter = (InnerBaseAdapter<T>) ((HeaderViewListAdapter)listAdapter).getWrappedAdapter();
        }*/
        this.mAdapter = adapter;
    }
}
