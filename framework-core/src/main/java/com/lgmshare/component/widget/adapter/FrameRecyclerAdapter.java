package com.lgmshare.component.widget.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/19 18:07
 */
public abstract class FrameRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG = FrameRecyclerAdapter.this.getClass().getSimpleName();

    protected static final int VIEW_TYPE_HEADER = 0x00111;
    protected static final int VIEW_TYPE_FOOTER = 0x00112;

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mDataList;

    private LinearLayout mHeaderViewContainer;
    private LinearLayout mFooterViewContainer;
    /*外部Header View的个数（如：XRecyclerView），在使用notifyItemRemoved方法时需要用到*/
    private int mExternalHeaderViewsCount;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemChildClickListener mChildClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(View view, int position);
    }

    public class OnAdapterItemChildClickListener implements View.OnClickListener {
        public int position;

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null)
                mChildClickListener.onItemChildClick(v, position);
        }
    }

    public FrameRecyclerAdapter(Context context) {
        this(context, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context The context.
     * @param data    A new list is created out of this one to avoid mutable list
     */
    public FrameRecyclerAdapter(Context context, List<T> data) {
        this.mDataList = (data == null ? new ArrayList<T>() : new ArrayList<T>(data));
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        ensureHeaderViewContainer();
        this.mHeaderViewContainer.addView(header);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        ensureFooterViewContainer();
        this.mFooterViewContainer.addView(footer);
        this.notifyDataSetChanged();
    }

    public void setExternalHeaderViewsCount(int externalHeaderViewsCount) {
        mExternalHeaderViewsCount = externalHeaderViewsCount;
    }

    private void ensureHeaderViewContainer() {
        if (mHeaderViewContainer == null) {
            mHeaderViewContainer = new LinearLayout(mContext);
            mHeaderViewContainer.setOrientation(LinearLayout.VERTICAL);
            mHeaderViewContainer.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void ensureFooterViewContainer() {
        if (mFooterViewContainer == null) {
            mFooterViewContainer = new LinearLayout(mContext);
            mFooterViewContainer.setOrientation(LinearLayout.VERTICAL);
            mFooterViewContainer.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    ////////////////change data/////////////

    /**
     * setting up a new instance to dataList;
     *
     * @param data
     */
    public void setList(List<T> data) {
        this.mDataList.clear();
        if (data != null) {
            this.mDataList.addAll(data);
        }
        this.notifyDataSetChanged();
    }

    /**
     * additional data;
     *
     * @param data
     */
    public void addList(List<T> data) {
        if (data != null) {
            this.mDataList.addAll(data);
        }
        this.notifyItemRangeInserted(mDataList.size() + getHeaderViewsCount() + mExternalHeaderViewsCount - 1, data.size());
    }

    public List<T> getList() {
        return this.mDataList;
    }

    public void addItem(T item) {
        if (item != null) {
            this.mDataList.add(item);
        }
        this.notifyItemInserted(mDataList.size() + getHeaderViewsCount() + mExternalHeaderViewsCount - 1);
    }

    public void addItem(int position, T item) {
        if (item != null) {
            this.mDataList.add(position, item);
        }
        this.notifyItemInserted(position + getHeaderViewsCount() + mExternalHeaderViewsCount);
        this.notifyItemRangeChanged(position + getHeaderViewsCount() + mExternalHeaderViewsCount, mDataList.size() - position);
    }

    public void removeItem(int position) {
        this.mDataList.remove(position);
        this.notifyItemRemoved(position + getHeaderViewsCount() + mExternalHeaderViewsCount);
        this.notifyItemRangeChanged(position + getHeaderViewsCount() + mExternalHeaderViewsCount, mDataList.size() - position);
    }

    public void removeItem(T t) {
        int position = mDataList.indexOf(t);
        if (position >= 0) {
            this.mDataList.remove(t);
            this.notifyItemRemoved(position + getHeaderViewsCount() + mExternalHeaderViewsCount);
            this.notifyItemRangeChanged(position + getHeaderViewsCount() + mExternalHeaderViewsCount, mDataList.size() - position);
        }
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return this.mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return this.mDataList.size() + getHeaderViewsCount() + getFooterViewsCount();
    }

    public int getHeaderViewsCount() {
        return mHeaderViewContainer == null ? 0 : 1;
    }

    public int getFooterViewsCount() {
        return mFooterViewContainer == null ? 0 : 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = mDataList.size();
        if (getHeaderViewsCount() != 0 && position < getHeaderViewsCount()) {
            //头部View
            return VIEW_TYPE_HEADER;
        } else if (getFooterViewsCount() != 0 && position >= (getHeaderViewsCount() + dataItemCount)) {
            //底部View
            return VIEW_TYPE_FOOTER;
        } else {
            //内容View
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder baseViewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                baseViewHolder = new RecyclerViewHolder(mContext, mHeaderViewContainer);
                break;
            case VIEW_TYPE_FOOTER:
                baseViewHolder = new RecyclerViewHolder(mContext, mFooterViewContainer);
                break;
            default:
                baseViewHolder = new RecyclerViewHolder(mContext, getItemView(onBindItemViewResId(), parent));

        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int dataPosition = position - getHeaderViewsCount();
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.setDataPosition(dataPosition);
        switch (holder.getItemViewType()) {
            case 0:
                onBindItemViewData((RecyclerViewHolder) holder, mDataList.get(dataPosition));
                bindItemViewClickListener(recyclerViewHolder, dataPosition);
                break;
            case VIEW_TYPE_HEADER:
                break;
            case VIEW_TYPE_FOOTER:
                break;
            default:
                onBindItemViewData((RecyclerViewHolder) holder, mDataList.get(dataPosition));
                bindItemViewClickListener(recyclerViewHolder, dataPosition);
                break;
        }
    }

    protected void bindItemViewClickListener(final RecyclerViewHolder baseViewHolder, final int position) {
        if (onItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //TODO 长按操作，如果返回false，onItemClick事件将会响应，返回true，则不会。
                    return mOnItemLongClickListener.onItemLongClick(v, position);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (isFullSpanType(type)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (isFullSpanType(type)) {
            setFullSpan(holder);
        }
    }

    protected boolean isFullSpanType(int type) {
        return type == VIEW_TYPE_HEADER || type == VIEW_TYPE_FOOTER;
    }

    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    protected abstract int onBindItemViewResId();

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param holder A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void onBindItemViewData(RecyclerViewHolder holder, T item);

}
