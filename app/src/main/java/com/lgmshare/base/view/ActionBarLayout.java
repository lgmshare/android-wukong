package com.lgmshare.base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgmshare.base.R;

/**
 * 通用ActionBar
 *
 * @author: lim
 * @ClassName: ActionBarLayout
 * @datetime 2014-10-28 上午11:02:00
 */
public class ActionBarLayout extends RelativeLayout {

    /* 中间文字标题 */
    private TextView mTitleTextView;
    /* 左边文字按钮 */
    private Button mLeftButotn;
    /* 左边图标按钮 */
    private ImageButton mLeftImageButton;
    /* 右边文字按钮 */
    private Button mRightButotn;
    /* 右边图标按钮 */
    private ImageButton mRightImageButton;
    /* 右边提示数目 */
    private TextView mRightTipsNumTextView;

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
        mTitleTextView = (TextView) view.findViewById(R.id.tv_title);
        mLeftButotn = (Button) view.findViewById(R.id.btn_left_txt);
        mLeftImageButton = (ImageButton) view.findViewById(R.id.btn_left_img);
        mRightButotn = (Button) view.findViewById(R.id.btn_right_txt);
        mRightImageButton = (ImageButton) view.findViewById(R.id.btn_right_img);
        mRightTipsNumTextView = (TextView) view.findViewById(R.id.tv_tips_num);

        initViewGone(mTitleTextView);
        initViewGone(mLeftButotn);
        initViewGone(mLeftImageButton);
        initViewGone(mRightButotn);
        initViewGone(mRightImageButton);
        initViewGone(mRightTipsNumTextView);
    }

    private void initViewGone(View view){
        if (view != null)
            view.setVisibility(GONE);
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public Button getLeftTextButotn() {
        return mLeftButotn;
    }

    public ImageButton getLeftImageButton() {
        return mLeftImageButton;
    }

    public Button getRightTextButotn() {
        return mRightButotn;
    }

    public ImageButton getRightImageButton() {
        return mRightImageButton;
    }

    /**
     * 设置标题
     *
     * @param txtResId
     */
    public void setTitle(int txtResId) {
        mTitleTextView.setText(txtResId);
        mTitleTextView.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitleTextView.setText(title);
        mTitleTextView.setVisibility(View.VISIBLE);
    }

    public void setTitle(int txtResId, OnClickListener onLeftClickListener) {
        mTitleTextView.setText(txtResId);
        mTitleTextView.setVisibility(View.VISIBLE);
        mLeftImageButton.setOnClickListener(onLeftClickListener);
        mLeftImageButton.setVisibility(View.VISIBLE);
    }

    public void setTitle(String title, OnClickListener onLeftClickListener) {
        mTitleTextView.setText(title);
        mTitleTextView.setVisibility(View.VISIBLE);
        mLeftImageButton.setOnClickListener(onLeftClickListener);
        mLeftImageButton.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边图标按钮及事件
     *
     * @param imageResId      资源ID
     * @param onClickListener 事件
     */
    public void setLeftImageButton(int imageResId, OnClickListener onClickListener) {
        mLeftImageButton.setVisibility(View.VISIBLE);
        mLeftImageButton.setImageResource(imageResId);
        mLeftImageButton.setOnClickListener(onClickListener);
    }

    /**
     * 设置左边按钮及事件
     *
     * @param txtResId        资源ID
     * @param onClickListener 事件
     */
    public void setLeftTextButton(int txtResId, OnClickListener onClickListener) {
        mLeftButotn.setVisibility(View.VISIBLE);
        mLeftButotn.setText(txtResId);
        mLeftButotn.setOnClickListener(onClickListener);
    }

    /**
     * 设置左边按钮及事件
     *
     * @param txt             资源ID
     * @param onClickListener 事件
     */
    public void setLeftTextButton(String txt, OnClickListener onClickListener) {
        mLeftButotn.setVisibility(View.VISIBLE);
        mLeftButotn.setText(txt);
        mLeftButotn.setOnClickListener(onClickListener);
    }

    /**
     * 设置左边按钮及事件
     *
     * @param txt             资源ID
     * @param onClickListener 事件
     */
    public void setLeftTextButton(int imgResId, String txt, OnClickListener onClickListener) {
        mLeftButotn.setVisibility(View.VISIBLE);
        mLeftButotn.setText(txt);
        mLeftButotn.setCompoundDrawablesWithIntrinsicBounds(imgResId, 0, 0, 0);
        mLeftButotn.setCompoundDrawablePadding(3);
        mLeftButotn.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param imgResId        资源ID
     * @param onClickListener 事件
     */
    public void setRightImageButton(int imgResId, OnClickListener onClickListener) {
        mRightImageButton.setVisibility(View.VISIBLE);
        mRightImageButton.setImageResource(imgResId);
        mRightImageButton.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param txtResId        资源ID
     * @param onClickListener 事件
     */
    public void setRightTextButton(int txtResId, OnClickListener onClickListener) {
        mRightButotn.setVisibility(View.VISIBLE);
        mRightButotn.setText(txtResId);
        mRightButotn.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边按钮及事件
     *
     * @param txt
     * @param onClickListener
     */
    public void setRightTextButton(String txt, OnClickListener onClickListener) {
        mRightButotn.setVisibility(View.VISIBLE);
        mRightButotn.setText(txt);
        mRightButotn.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边提示数目
     *
     * @param num
     */
    public void setRightTipsNum(int num) {
        if (num > 0) {
            mRightTipsNumTextView.setText(String.valueOf(num));
            mRightTipsNumTextView.setVisibility(View.VISIBLE);
        } else {
            mRightTipsNumTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边提示数目
     *
     * @param num
     */
    public void setRightTipsNum(String num) {
        if (!TextUtils.isEmpty(num)) {
            mRightTipsNumTextView.setText(num);
            mRightTipsNumTextView.setVisibility(View.VISIBLE);
        } else {
            mRightTipsNumTextView.setVisibility(View.GONE);
        }
    }

    public void hideLeftImageButton() {
        mLeftImageButton.setVisibility(View.GONE);
    }

    public void hideLeftTextButton() {
        mLeftButotn.setVisibility(View.GONE);
    }

    public void hideRightImageButton() {
        mRightImageButton.setVisibility(View.GONE);
    }

    public void hideRightTextButton() {
        mRightButotn.setVisibility(View.GONE);
    }

    public void hideRightTipsNumTextView() {
        mRightTipsNumTextView.setVisibility(View.GONE);
    }

    public void showLeftImageButton() {
        mLeftImageButton.setVisibility(View.VISIBLE);
    }

    public void showLeftTextButton() {
        mLeftButotn.setVisibility(View.VISIBLE);
    }

    public void showRightImageButton() {
        mRightImageButton.setVisibility(View.VISIBLE);
    }

    public void showRightTextButton() {
        mRightButotn.setVisibility(View.VISIBLE);
    }

    public void showRightTipsNumTextView() {
        mRightTipsNumTextView.setVisibility(View.VISIBLE);
    }
}
