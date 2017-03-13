package com.lgmshare.component.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础适配类
 *
 * @author: lim
 * @description: 基础适配类
 * @email: lgmshare@gmail.com
 * @datetime 2014年7月7日  上午11:04:44
 */
public abstract class FrameAdapter<T> extends BaseAdapter {

    protected List<T> mDataList;
    protected Context mContext;

    private OnItemChildClickListener mChildClickListener;

    public void setOnItemChildClickListener(OnItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(Adapter adapter, View view, int position);
    }

    public class OnAdapterItemChildClickListener implements View.OnClickListener {
        public int position;

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null)
                mChildClickListener.onItemChildClick(FrameAdapter.this, v, position);
        }
    }

    public FrameAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public FrameAdapter(Context context, List<T> data) {
        this.mDataList = (data == null ? new ArrayList<T>() : new ArrayList<T>(data));
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mDataList == null ? 0 : this.mDataList.size();
    }

    @Override
    public T getItem(int i) {
        return this.mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 添加 Item<T>
     *
     * @param t
     */
    public void addItem(T t) {
        if (t != null) {
            this.mDataList.add(t);
        }
        this.notifyDataSetChanged();
    }

    public void addItem(int position, T t) {
        if (t != null) {
            this.mDataList.add(position, t);
        }
        this.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.mDataList.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeItem(T t) {
        int index = mDataList.indexOf(t);
        if (index >= 0) {
            this.mDataList.remove(t);
            this.notifyDataSetChanged();
        }
    }

    /**
     * 添加ArrayList<T>
     *
     * @param list
     */
    public void addList(List<T> list) {
        if (list != null) {
            this.mDataList.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    /**
     * 设置ArrayList<T>
     *
     * @param list
     */
    public void setList(List<T> list) {
        this.mDataList.clear();
        if (list != null) {
            this.mDataList.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    /**
     * 获取
     *
     * @return ArrayList<T>
     */
    public List<T> getList() {
        return this.mDataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        onBindItemViewData(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, onBindItemViewResId(), position);
    }

    protected abstract int onBindItemViewResId();

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param holder A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void onBindItemViewData(ViewHolder holder, T item);
}
