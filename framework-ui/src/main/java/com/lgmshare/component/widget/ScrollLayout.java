package com.lgmshare.component.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author: lim
 * @Version V1.0
 * @Description 像viewpager的内容切换
 * @email: lgmshare@gmail.com
 * @datetime : 2015/5/19 15:15
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class ScrollLayout extends ViewGroup {

    private static final String TAG = "ScrollLayout";

    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;

    /* 滑动方向，默认横向滑动*/
    private int mOrientation = HORIZONTAL;

    private Scroller mScroller;
    /* 速度追踪器，主要是为了通过当前滑动速度判断当前滑动是否为fling */
    private VelocityTracker mVelocityTracker;
    /* 记录当前屏幕下标，取值范围是：0 到 getChildCount()-1 */
    private int mCurScreen;
    private int mDefaultScreen = 0;
    /* Touch状态值 0：静止 1：滑动 */
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    /* 记录当前touch事件状态--滑动（TOUCH_STATE_SCROLLING）、静止（TOUCH_STATE_REST 默认） */
    private int mTouchState = TOUCH_STATE_REST;
    private static final int SNAP_VELOCITY = 300;
    /* 记录touch事件中被认为是滑动事件前的最大可滑动距离 */
    private int mTouchSlop;
    /* 记录滑动时上次手指所处的位置 */
    private float mLastMotionX;
    private float mLastMotionY;

    private OnScrollToScreenListener mOnScrollToScreenListener = null;

    public ScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context);
        mCurScreen = mDefaultScreen;
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childOffset = 0;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                if (mOrientation == HORIZONTAL) {
                    final int childWidth = childView.getMeasuredWidth();
                    childView.layout(childOffset, 0, childOffset + childWidth, childView.getMeasuredHeight());
                    childOffset += childWidth;
                } else {
                    final int childHeight = childView.getMeasuredHeight();
                    Log.d(TAG, "child index=" + i + " height=" + childHeight);
                    childView.layout(0, childOffset, childView.getMeasuredWidth(), childOffset + childHeight);
                    childOffset += childHeight;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY && mOrientation == HORIZONTAL) {
            throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
        }
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY && mOrientation == VERTICAL) {
            throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
        }

        // The children are given the same width and height as the scrollLayout
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        if (mOrientation == HORIZONTAL) {
            scrollTo(mCurScreen * width, 0);
        } else {
            scrollTo(0, mCurScreen * height);
        }
        doScrollAction(mCurScreen);
    }

    /**
     * Should the layout be a column or a row.
     *
     * @param orientation Pass {@link #HORIZONTAL} or {@link #VERTICAL}. Default
     *                    value is {@link #HORIZONTAL}.
     * @attr ref android.R.styleable#LinearLayout_orientation
     */
    public void setOrientation(@LinearLayoutCompat.OrientationMode int orientation) {
        if (mOrientation != orientation) {
            mOrientation = orientation;
            requestLayout();
        }
    }

    /**
     * 根据当前位置滑动到相应界面
     */
    public void snapToDestination() {
        if (mOrientation == HORIZONTAL) {
            final int screenWidth = getWidth();
            final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
            snapToScreen(destScreen);
        } else {
            final int screenHeight = getHeight();
            final int destScreen = (getScrollY() + screenHeight / 2) / screenHeight;
            snapToScreen(destScreen);
        }
    }

    /**
     * 滑动到到第whichScreen（从0开始）个界面，有过渡效果
     *
     * @param whichScreen
     */
    public void snapToScreen(int whichScreen) {
        // get the valid layout page
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (mOrientation == HORIZONTAL) {
            if (getScrollX() != (whichScreen * getWidth())) {
                final int delta = whichScreen * getWidth() - getScrollX();
                mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta));
                mCurScreen = whichScreen;
                doScrollAction(mCurScreen);
                invalidate(); // Redraw the layout
            }
        } else {
            if (getScrollY() != (whichScreen * getHeight())) {
                final int delta = whichScreen * getHeight() - getScrollY();
                mScroller.startScroll(0, getScrollY(), 0, delta, Math.abs(delta));
                mCurScreen = whichScreen;
                doScrollAction(mCurScreen);
                invalidate(); // Redraw the layout
            }
        }
    }

    /**
     * 指定并跳转到第whichScreen（从0开始）个界面
     *
     * @param whichScreen
     */
    public void setToScreen(int whichScreen) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        mCurScreen = whichScreen;
        scrollTo(whichScreen * getWidth(), 0);
        doScrollAction(whichScreen);
    }

    public int getCurScreen() {
        return mCurScreen;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            //返回true，执行事件拦截，不再接受事件，后续事件将在onTouchEvent中处理
            return true;
        }
        final float x = ev.getX();
        final float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mOrientation == HORIZONTAL) {
                    final int xDiff = (int) Math.abs(mLastMotionX - x);
                    if (xDiff > mTouchSlop) {
                        if (Math.abs(mLastMotionY - y) / Math.abs(mLastMotionX - x) < 1)
                            mTouchState = TOUCH_STATE_SCROLLING;
                    }
                } else {
                    final int yDiff = (int) Math.abs(mLastMotionY - y);
                    if (yDiff > mTouchSlop) {
                        if (Math.abs(mLastMotionX - x) / Math.abs(mLastMotionY - y) < 1)
                            mTouchState = TOUCH_STATE_SCROLLING;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        final int action = MotionEventCompat.getActionMasked(event);
        final float x = event.getX();
        final float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (mLastMotionX - x);
                int deltaY = (int) (mLastMotionY - y);
                mLastMotionX = x;
                mLastMotionY = y;
                if (mOrientation == HORIZONTAL) {
                    //不允许拉出边界值
                    if (mCurScreen == 0 && deltaX < 0) {
                        return false;
                    } else if (mCurScreen == getChildCount() - 1 && deltaX > 0) {
                        return false;
                    }
                    scrollBy(deltaX, 0);
                } else {
                    //不允许拉出边界值
                    if (mCurScreen == 0 && deltaY < 0) {
                        return false;
                    } else if (mCurScreen == getChildCount() - 1 && deltaY > 0) {
                        return false;
                    }
                    scrollBy(0, deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(300);
                if (mOrientation == HORIZONTAL) {
                    int velocityX = (int) velocityTracker.getXVelocity();
                    if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
                        // Fling enough to move left
                        snapToScreen(mCurScreen - 1);
                    } else if (velocityX < -SNAP_VELOCITY && mCurScreen < getChildCount() - 1) {
                        // Fling enough to move right
                        snapToScreen(mCurScreen + 1);
                    } else {
                        snapToDestination();
                    }
                } else {
                    int velocityY = (int) velocityTracker.getYVelocity();
                    if (velocityY > SNAP_VELOCITY && mCurScreen > 0) {
                        // Fling enough to move top
                        snapToScreen(mCurScreen - 1);
                    } else if (velocityY < -SNAP_VELOCITY && mCurScreen < getChildCount() - 1) {
                        // Fling enough to move bottom
                        snapToScreen(mCurScreen + 1);
                    } else {
                        snapToDestination();
                    }
                }

                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return true;
    }

    /**
     * 当滑动切换界面时执行相应操作
     */
    private void doScrollAction(int whichScreen) {
        if (mOnScrollToScreenListener != null) {
            mOnScrollToScreenListener.doAction(whichScreen);
        }
    }

    /**
     * 设置内部接口的实现类实例
     */
    public void setOnScrollToScreen(OnScrollToScreenListener onScrollToScreen) {
        this.mOnScrollToScreenListener = onScrollToScreen;
    }

    /**
     * 当滑动到某个界面时可以调用该接口下的doAction()方法执行某些操作
     */
    public interface OnScrollToScreenListener {
        void doAction(int whichScreen);
    }

    /**
     * 指定默认页面位置
     *
     * @param position
     */
    public void setDefaultScreen(int position) {
        mCurScreen = position;
    }
}