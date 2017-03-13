package com.lgmshare.component.widget.xrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/6/13 11:12
 */
public class XRecyclerView extends RecyclerView {

    private static final String TAG = XRecyclerView.class.getSimpleName();

    private static final float PULL_OFFSET_RADIO = 1.8f;
    private static final int PULL_RESET_DURATION = 400;
    private static final int PULL_LOAD_DELTA = 90; // when pull up >= 90px

    private Context mContext;

    private XRecyclerViewHeader mXRecyclerHeader;
    private XRecyclerViewFooter mXRecyclerFooter;

    private LinearLayout mHeaderViewContainer;
    private LinearLayout mFooterViewContainer;

    private boolean mEnablePullRefresh = true;
    private boolean mEnablePullLoad;
    private boolean mEnablePullLoadAuto = true;// auto load more

    private boolean mPullRefreshing = false; // is refreashing.
    private boolean mPullLoading;//is loading

    private Adapter mAdapter;
    private Adapter mWrapAdapter;

    private XRecyclerViewListener mXRecyclerViewListener;

    public XRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        // init header view
        mXRecyclerHeader = new XRecyclerViewHeader(context);
        // init Footer view
        mXRecyclerFooter = new XRecyclerViewFooter(context);

        //ensureHeaderViewContainer();
        //ensureFooterViewContainer();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mDataObserver);
        mWrapAdapter = new WrapAdapter(mXRecyclerHeader, mXRecyclerFooter, mHeaderViewContainer, mFooterViewContainer, adapter);
        super.setAdapter(mWrapAdapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mWrapAdapter != null) {
            mWrapAdapter.onDetachedFromRecyclerView(this);
            mWrapAdapter.onAttachedToRecyclerView(this);
        }
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mXRecyclerHeader.setVisibility(View.INVISIBLE);
        } else {
            mXRecyclerHeader.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mXRecyclerFooter.hide();
            mXRecyclerFooter.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mXRecyclerFooter.show();
            mXRecyclerFooter.setState(XRecyclerViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mXRecyclerFooter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPullRefreshing && !mPullLoading) {
                        startLoadMore();
                    }
                }
            });
        }
    }

    public void setPullLoadAutoEnable(boolean enable) {
        mEnablePullLoadAuto = enable;
    }

    /**
     * 必须在@setAdapter之前添加
     *
     * @param header
     */
    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        ensureHeaderViewContainer();
        this.mHeaderViewContainer.addView(header);
        if (mWrapAdapter != null) {
            this.mWrapAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 必须在@setAdapter之前添加
     *
     * @param footer
     */
    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        ensureFooterViewContainer();
        this.mFooterViewContainer.addView(footer);
        if (mWrapAdapter != null) {
            this.mWrapAdapter.notifyDataSetChanged();
        }
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

    public int getHeaderViewsCount() {
        return mHeaderViewContainer == null ? 1 : 2;
    }

    public int getFooterViewsCount() {
        return mFooterViewContainer == null ? 1 : 2;
    }

    /**
     * stop refresh, reset header view.
     */
    public void onRefreshComplete() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void onLoadMoreComplete() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mXRecyclerFooter.setState(XRecyclerViewFooter.STATE_NORMAL);
        }
    }

    /**
     * 开始刷新数据
     */
    private void startRefresh() {
        if (mPullRefreshing) {
            return;
        }
        mPullRefreshing = true;
        mXRecyclerHeader.setState(XRecyclerViewHeader.STATE_REFRESHING);
        if (mXRecyclerViewListener != null) {
            mXRecyclerViewListener.onRefresh();
        }
    }

    /**
     * 开始加载更多
     */
    private void startLoadMore() {
        if (mPullLoading) {
            return;
        }
        mPullLoading = true;
        mXRecyclerFooter.setState(XRecyclerViewFooter.STATE_LOADING);
        if (mXRecyclerViewListener != null) {
            mXRecyclerViewListener.onLoadMore();
        }
    }

    private int mActivePointerId = -1;
    private int mLastTouchX = 0;
    private int mLastTouchY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mActivePointerId = MotionEventCompat.getPointerId(e, 0);
                mLastTouchX = getMotionEventX(e, actionIndex);
                mLastTouchY = getMotionEventY(e, actionIndex);
            }
            break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                mActivePointerId = MotionEventCompat.getPointerId(e, actionIndex);
                mLastTouchX = getMotionEventX(e, actionIndex);
                mLastTouchY = getMotionEventY(e, actionIndex);
            }
            break;
            case MotionEventCompat.ACTION_POINTER_UP: {
                onPointerUp(e);
            }
            break;
        }

        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        final int action = MotionEventCompat.getActionMasked(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int index = MotionEventCompat.getActionIndex(e);
                mActivePointerId = MotionEventCompat.getPointerId(e, 0);
                mLastTouchX = getMotionEventX(e, index);
                mLastTouchY = getMotionEventY(e, index);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                final int index = MotionEventCompat.findPointerIndex(e, mActivePointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id " + index + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x = getMotionEventX(e, index);
                final int y = getMotionEventY(e, index);

                final int dx = x - mLastTouchX;
                final int dy = y - mLastTouchY;

                mLastTouchX = x;
                mLastTouchY = y;

                final boolean triggerRefresh = mEnablePullRefresh && !mPullRefreshing && !mPullLoading && isFingerDragging() && isTriggerRefresh();
                if (triggerRefresh && (mXRecyclerHeader.getVisiableHeight() > 0 || dy > 0)) {
                    updateHeaderHeight(dy / PULL_OFFSET_RADIO);

                    final MotionEvent event = MotionEvent.obtainNoHistory(e);
                    event.offsetLocation(0, 0);
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                    return false;
                }

                final boolean triggerFooter = mEnablePullLoad && !mPullRefreshing && !mPullLoading && isFingerDragging() && isTriggerLoad();
                if (triggerFooter && (mXRecyclerFooter.getBottomMargin() > 0 || dy < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-dy / PULL_OFFSET_RADIO);

                    if (dy > 0) {
                        final MotionEvent event = MotionEvent.obtainNoHistory(e);
                        event.offsetLocation(0, 0);
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        return false;
                    }
                }
            }
            break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(e);
                mActivePointerId = MotionEventCompat.getPointerId(e, index);
                mLastTouchX = getMotionEventX(e, index);
                mLastTouchY = getMotionEventY(e, index);
            }
            break;
            case MotionEventCompat.ACTION_POINTER_UP: {
                onPointerUp(e);
            }
            default: {
                final boolean triggerRefresh = mEnablePullRefresh && !mPullRefreshing && !mPullLoading && isTriggerRefresh();
                if (triggerRefresh) {
                    if (mXRecyclerHeader.getVisiableHeight() > mXRecyclerHeader.getFactHeight()) {
                        startRefresh();
                    }
                    resetHeaderHeight();
                }

                final boolean triggerFooter = mEnablePullLoad && !mPullRefreshing && !mPullLoading && isTriggerLoad();
                if (triggerFooter) {
                    if (mXRecyclerFooter.getBottomMargin() > PULL_LOAD_DELTA) {
                        // last item, already pulled up or want to pull up.
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
            }
            break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 判断滚动到底部
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            final boolean triggerFooter = mEnablePullLoad && !mPullRefreshing && !mPullLoading && isTriggerLoad();
            if (triggerFooter && mEnablePullLoadAuto) {
                startLoadMore();
            }
        }
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        if (MotionEventCompat.getPointerId(e, actionIndex) == mActivePointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(e, newIndex);
            mLastTouchX = getMotionEventX(e, newIndex);
            mLastTouchY = getMotionEventY(e, newIndex);
        }
    }

    private int getMotionEventX(MotionEvent e, int pointerIndex) {
        return (int) (MotionEventCompat.getX(e, pointerIndex) + 0.5f);
    }

    private int getMotionEventY(MotionEvent e, int pointerIndex) {
        return (int) (MotionEventCompat.getY(e, pointerIndex) + 0.5f);
    }

    /**
     * 手指是否在拖拽中
     *
     * @return
     */
    private boolean isFingerDragging() {
        return getScrollState() == SCROLL_STATE_DRAGGING;
    }

    /**
     * 是否可触发刷新
     *
     * @return
     */
    private boolean isTriggerRefresh() {
        final Adapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() <= 0) {
            return true;
        }
        View firstChild = getChildAt(0);
        int position = getChildLayoutPosition(firstChild);
        if (position == 0) {
            //距离顶部的距离，需要计算maring
            int decoratedTop = getLayoutManager().getDecoratedTop(firstChild);
            if (mXRecyclerHeader.getParent() != null && decoratedTop == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否可触发加载
     *
     * @return
     */
    private boolean isTriggerLoad() {
        final Adapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() <= 0) {
            return true;
        }
        View lastChild = getChildAt(getChildCount() - 1);
        int position = getChildLayoutPosition(lastChild);
        if (position == mWrapAdapter.getItemCount() - 1) {
            if (lastChild.getBottom() == mXRecyclerFooter.getBottom()) {
                return true;
            }
        }
        return false;
    }

    private void updateHeaderHeight(float delta) {
        mXRecyclerHeader.setVisiableHeight((int) delta + mXRecyclerHeader.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mXRecyclerHeader.getVisiableHeight() > mXRecyclerHeader.getFactHeight()) {
                mXRecyclerHeader.setState(XRecyclerViewHeader.STATE_READY);
            } else {
                mXRecyclerHeader.setState(XRecyclerViewHeader.STATE_NORMAL);
            }
        }
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mXRecyclerHeader.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mXRecyclerHeader.getFactHeight()) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mXRecyclerHeader.getFactHeight()) {
            finalHeight = mXRecyclerHeader.getFactHeight();
        }

        ValueAnimator animator = ValueAnimator.ofInt(height, finalHeight);
        animator.setDuration(PULL_RESET_DURATION).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mXRecyclerHeader.setVisiableHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void updateFooterHeight(float delta) {
        if (mEnablePullLoadAuto || !mEnablePullLoad) {
            return;
        }
        int height = mXRecyclerFooter.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_DELTA) { // height enough to invoke load more.
                mXRecyclerFooter.setState(XRecyclerViewFooter.STATE_READY);
            } else {
                mXRecyclerFooter.setState(XRecyclerViewFooter.STATE_NORMAL);
            }
        }
        mXRecyclerFooter.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = mXRecyclerFooter.getBottomMargin();
        if (bottomMargin > 0) {
            ValueAnimator animator = ValueAnimator.ofInt(bottomMargin, -bottomMargin);
            animator.setDuration(PULL_RESET_DURATION).start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mXRecyclerFooter.setBottomMargin((int) animation.getAnimatedValue());
                }
            });
            animator.start();
        }
    }

    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private class WrapAdapter extends Adapter<ViewHolder> {

        private static final int TYPE_PULL_HEADER = -1;
        private static final int TYPE_PULL_FOOTER = -2;
        private static final int TYPE_HEADER = -3;
        private static final int TYPE_FOOTER = -4;
        private static final int TYPE_CONTENT = 0;

        private Adapter adapter;

        private XRecyclerViewHeader xRecyclerHeader;
        private XRecyclerViewFooter xRecyclerFooter;

        private LinearLayout headerViewContainer;
        private LinearLayout footerViewContainer;

        public WrapAdapter(XRecyclerViewHeader mRefreshHeaderView, XRecyclerViewFooter mLoadFooterView, LinearLayout headerViews, LinearLayout footerViews, Adapter adapter) {
            this.adapter = adapter;
            this.xRecyclerHeader = mRefreshHeaderView;
            this.xRecyclerFooter = mLoadFooterView;
            this.headerViewContainer = headerViews;
            this.footerViewContainer = footerViews;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position)) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            int position = holder.getLayoutPosition();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(position) || isFooter(position))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        public int getHeadersCount() {
            return headerViewContainer == null ? 1 : 2;
        }

        public int getFootersCount() {
            return footerViewContainer == null ? 1 : 2;
        }

        public boolean isPullRefreshHeader(int position) {
            return position == 0;
        }

        public boolean isPullLoadFooter(int position) {
            return position == getItemCount() - 1;
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < getHeadersCount();
        }

        public boolean isFooter(int position) {
            return position >= getItemCount() - getFootersCount() && position <= getItemCount() - 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_PULL_HEADER) {
                return new SimpleViewHolder(xRecyclerHeader);
            } else if (viewType == TYPE_HEADER) {
                return new SimpleViewHolder(headerViewContainer);
            } else if (viewType == TYPE_PULL_FOOTER) {
                return new SimpleViewHolder(xRecyclerFooter);
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(footerViewContainer);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isPullRefreshHeader(position)) {
                return;
            }
            if (isHeader(position)) {
                return;
            }
            if (isPullLoadFooter(position)) {
                return;
            }
            if (isFooter(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isPullRefreshHeader(position)) {
                return TYPE_PULL_HEADER;
            }
            if (isPullLoadFooter(position)) {
                return TYPE_PULL_FOOTER;
            }
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;

            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_CONTENT;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public interface XRecyclerViewListener {
        void onRefresh();

        void onLoadMore();
    }

    public void setXRecyclerViewListener(XRecyclerViewListener xRecyclerViewListener) {
        mXRecyclerViewListener = xRecyclerViewListener;
    }
}
