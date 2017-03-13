package com.lgmshare.component.widget.xrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView间距
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/4/6 15:26
 */
public class XDividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private int mOrientation;
    private int mSpace;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public XDividerItemDecoration(Context context, int space) {
        this(context, VERTICAL, space);
    }

    public XDividerItemDecoration(Context context, int orientation, int space) {
        mOrientation = orientation;
        mSpace = dipToPx(context, space);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        XRecyclerView xRecyclerView = (XRecyclerView) parent;
        int layoutPosition = xRecyclerView.getChildLayoutPosition(view) - xRecyclerView.getHeaderViewsCount();
        if (layoutPosition >= 0) {
            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                //不是第一个的格子都设一个左边和底部的间距
                outRect.left = mSpace;
                outRect.right = mSpace;
                outRect.top = mSpace;
            } else if (manager instanceof LinearLayoutManager) {
                if (mOrientation == VERTICAL) {
                    outRect.bottom = mSpace;
                } else {
                    outRect.right = mSpace;
                }
            }
        }
    }

    private int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
