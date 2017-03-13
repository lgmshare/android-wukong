package com.lgmshare.component.widget.xrecyclerview;

import android.content.Context;
import android.graphics.Rect;
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
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private int mOrientation;
    private int mSpace;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public DividerItemDecoration(Context context, int space) {
        this(context, VERTICAL, space);
    }

    public DividerItemDecoration(Context context, int orientation, int space) {
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
        if (parent.getChildLayoutPosition(view) != 0)
            if (mOrientation == VERTICAL) {
                outRect.top = mSpace;
            } else {
                outRect.right = mSpace;
            }
    }

    private int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
