package com.lgmshare.component.widget.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgmshare.component.widget.R;

/**
 * 自定义ActionBar
 *
 * @author: lim
 * @ClassName: ActionBarLayout
 * @datetime 2014-10-28 上午11:02:00
 */
public class ActionBarLayout extends RelativeLayout {

    private View actionbar_layout;
    /* 中间logo */
    private ImageView actionbar_logo;
    /* 中间文字标题 */
    private TextView actionbar_title;
    /* 左边文字按钮 */
    private Button actionbar_left_btn;
    /* 左边图标按钮 */
    private ImageButton actionbar_left_img;
    /* 右边文字按钮 */
    private Button actionbar_right_btn;
    /* 右边图标按钮 */
    private ImageButton actionbar_right_img;
    /* 右边图标按钮2 */
    private ImageButton actionbar_right_img2;
    /* 右边提示数目 */
    private TextView actionbar_right_tips;

    private View actionbar_bottom_line;

    public ActionBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    public ActionBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ActionBarLayout(Context context) {
        super(context);
        initViews(context);
    }

    private void initViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_actionbar, this);
        actionbar_layout = findViewById(R.id.actionbar_layout);
        actionbar_logo = (ImageView) view.findViewById(R.id.actionbar_logo);
        actionbar_title = (TextView) view.findViewById(R.id.actionbar_title);
        actionbar_left_btn = (Button) view.findViewById(R.id.actionbar_left_btn);
        actionbar_left_img = (ImageButton) view.findViewById(R.id.actionbar_left_img);
        actionbar_right_btn = (Button) view.findViewById(R.id.actionbar_right_btn);
        actionbar_right_img = (ImageButton) view.findViewById(R.id.actionbar_right_img);
        actionbar_right_img2 = (ImageButton) view.findViewById(R.id.actionbar_right_img2);
        actionbar_right_tips = (TextView) view.findViewById(R.id.actionbar_right_tips);
        actionbar_bottom_line = view.findViewById(R.id.actionbar_bottom_line);
        clean();
    }

    private void clean() {
        setViewGone(actionbar_logo);
        setViewGone(actionbar_title);
        setViewGone(actionbar_left_btn);
        setViewGone(actionbar_left_img);
        setViewGone(actionbar_right_btn);
        setViewGone(actionbar_right_img);
        setViewGone(actionbar_right_img2);
        setViewGone(actionbar_right_tips);
        setViewGone(actionbar_bottom_line);
    }

    private void setViewGone(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
    }

    private void setText(TextView view, CharSequence text) {
        if (view != null) {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setIcon(ImageButton view, int imgId) {
        if (view != null) {
            view.setImageResource(imgId);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setOnClickListener(View view, OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置标题
     *
     * @param txtId 标题
     */
    public void setTitle(int txtId) {
        setTitle(getResources().getString(txtId));
    }

    /**
     * 设置标题
     *
     * @param txt 标题
     */
    public void setTitle(CharSequence txt) {
        if (actionbar_title != null) {
            actionbar_title.setText(txt);
            actionbar_title.setVisibility(View.VISIBLE);
        }
        setViewGone(actionbar_logo);
    }

    /**
     * 设置标题及返回按钮监听事件
     *
     * @param txtId        标题
     * @param leftListener 返回事件
     */
    public void setTitle(int txtId, OnClickListener leftListener) {
        setTitle(txtId);
        setLeftImageIcon(R.drawable.actionbar_back_selector, leftListener);
    }

    /**
     * 设置标题及返回按钮监听事件
     *
     * @param txt
     * @param listener 返回事件
     */
    public void setTitle(CharSequence txt, OnClickListener listener) {
        setTitle(txt);
        setLeftImageIcon(R.drawable.actionbar_back_selector, listener);
    }

    /**
     * 设置图片LOGO
     *
     * @param resId
     */
    public void setLogo(int resId) {
        if (actionbar_logo != null) {
            actionbar_logo.setImageResource(resId);
            actionbar_logo.setVisibility(View.VISIBLE);
        }
        setViewGone(actionbar_title);
    }

    /**
     * 设置图片LOGO及返回按钮监听事件
     *
     * @param resId
     * @param listener 返回事件
     */
    public void setLogo(int resId, OnClickListener listener) {
        setLogo(resId);
        setLeftImageIcon(R.drawable.actionbar_back_selector, listener);
    }

    public void setLeftButton(int txtId) {
        setLeftButton(getResources().getString(txtId));
    }

    public void setLeftButton(CharSequence txt) {
        if (actionbar_left_btn != null) {
            actionbar_left_btn.setVisibility(View.VISIBLE);
            actionbar_left_btn.setText(txt);
        }
        setViewGone(actionbar_left_img);
    }

    public void setLeftButton(int txtId, OnClickListener listener) {
        setLeftButton(getResources().getString(txtId), listener);
    }

    public void setLeftButton(CharSequence txt, OnClickListener listener) {
        if (actionbar_left_btn != null) {
            actionbar_left_btn.setVisibility(View.VISIBLE);
            actionbar_left_btn.setText(txt);
            actionbar_left_btn.setOnClickListener(listener);
        }
        setViewGone(actionbar_left_img);
    }

    /**
     * @param left   value must >=0
     * @param top    value must >=0
     * @param right  value must >=0
     * @param bottom value must >=0
     */
    public void setLeftButtonDrawable(int left, int top, int right, int bottom) {
        if (actionbar_left_btn != null) {
            actionbar_left_btn.setVisibility(View.VISIBLE);
            actionbar_left_btn.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            actionbar_left_btn.setCompoundDrawablePadding(3);
        }
        setViewGone(actionbar_left_btn);
    }

    public void setLeftImageIcon(int imgId) {
        if (actionbar_left_img != null) {
            actionbar_left_img.setVisibility(View.VISIBLE);
            actionbar_left_img.setImageResource(imgId);
        }
        setViewGone(actionbar_left_btn);
    }

    public void setLeftImageIcon(int imgId, OnClickListener listener) {
        if (actionbar_left_img != null) {
            actionbar_left_img.setVisibility(View.VISIBLE);
            actionbar_left_img.setImageResource(imgId);
            actionbar_left_img.setOnClickListener(listener);
        }
        setViewGone(actionbar_left_btn);
    }

    ///////////////////////右按钮/////////////////////////
    public void setRightButton(int txtId) {
        setRightButton(getResources().getString(txtId));
    }

    public void setRightButton(CharSequence txt) {
        if (actionbar_right_btn != null) {
            actionbar_right_btn.setVisibility(View.VISIBLE);
            actionbar_right_btn.setText(txt);
        }
        setViewGone(actionbar_right_img);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param txtId    资源ID
     * @param listener 事件
     */
    public void setRightButton(int txtId, OnClickListener listener) {
        setRightButton(getResources().getString(txtId), listener);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param txt
     * @param onClickListener
     */
    public void setRightButton(CharSequence txt, OnClickListener onClickListener) {
        if (actionbar_right_btn != null) {
            actionbar_right_btn.setVisibility(View.VISIBLE);
            actionbar_right_btn.setText(txt);
            actionbar_right_btn.setOnClickListener(onClickListener);
        }
        setViewGone(actionbar_right_img);
    }

    /**
     * @param left   value must >=0
     * @param top    value must >=0
     * @param right  value must >=0
     * @param bottom value must >=0
     */
    public void setRightButtonDrawable(int left, int top, int right, int bottom) {
        if (actionbar_right_btn != null) {
            actionbar_right_btn.setVisibility(View.VISIBLE);
            actionbar_right_btn.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            actionbar_right_btn.setCompoundDrawablePadding(3);
        }
        setViewGone(actionbar_right_btn);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param imgId 资源ID
     */
    public void setRightImageIcon(int imgId) {
        if (actionbar_right_img != null) {
            actionbar_right_img.setVisibility(View.VISIBLE);
            actionbar_right_img.setImageResource(imgId);
        }
        setViewGone(actionbar_right_btn);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param imgId    资源ID
     * @param listener 事件
     */
    public void setRightImageIcon(int imgId, OnClickListener listener) {
        if (actionbar_right_img != null) {
            actionbar_right_img.setVisibility(View.VISIBLE);
            actionbar_right_img.setImageResource(imgId);
            actionbar_right_img.setOnClickListener(listener);
        }
        setViewGone(actionbar_right_btn);
    }

    /**
     * 设置右边提示数目
     *
     * @param num
     */
    public void setRightTips(int num) {
        if (actionbar_right_tips != null) {
            if (num > 0) {
                actionbar_right_tips.setText(String.valueOf(num));
                actionbar_right_tips.setVisibility(View.VISIBLE);
            } else {
                actionbar_right_tips.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置右边按钮及事件
     *
     * @param imgId 资源ID
     */
    public void setRightImage2Icon(int imgId) {
        if (actionbar_right_img2 != null) {
            actionbar_right_img2.setVisibility(View.VISIBLE);
            actionbar_right_img2.setImageResource(imgId);
        }
    }

    /**
     * 设置右边按钮及事件
     *
     * @param imgId    资源ID
     * @param listener 事件
     */
    public void setRightImage2Icon(int imgId, OnClickListener listener) {
        if (actionbar_right_img2 != null) {
            actionbar_right_img2.setVisibility(View.VISIBLE);
            actionbar_right_img2.setImageResource(imgId);
            actionbar_right_img2.setOnClickListener(listener);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        actionbar_layout.setBackgroundResource(resId);
    }

    public TextView getActionbarTitle() {
        return actionbar_title;
    }

    public void hideLeftImage() {
        actionbar_left_img.setVisibility(View.GONE);
    }

    public void hideLeftButton() {
        actionbar_left_btn.setVisibility(View.GONE);
    }

    public void hideRightImage() {
        actionbar_right_img.setVisibility(View.GONE);
    }

    public void hideRightButton() {
        actionbar_right_btn.setVisibility(View.GONE);
    }

    public void hideRightTips() {
        actionbar_right_tips.setVisibility(View.GONE);
    }

    public void showLeftImage() {
        actionbar_left_img.setVisibility(View.VISIBLE);
    }

    public void showLeftButton() {
        actionbar_left_btn.setVisibility(View.VISIBLE);
    }

    public void showRightImage() {
        actionbar_right_img.setVisibility(View.VISIBLE);
    }

    public void showRightButton() {
        actionbar_right_btn.setVisibility(View.VISIBLE);
    }

    public void showRightTips() {
        actionbar_right_tips.setVisibility(View.VISIBLE);
    }

    public void showBottomLine() {
        actionbar_bottom_line.setVisibility(VISIBLE);
    }
}
