package com.lgmshare.base.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lgmshare.base.R;

/**
 * 通用自定义样式
 *
 * @author: lim
 * @ClassName: CommonDialog.java
 * @datetime 2014-11-11 上午9:38:40
 */
public class CommonDialog extends Dialog {

    /**
     * Create message dialog
     *
     * @param context
     * @return
     */
    public static CommonDialog create(Context context) {
        return create(context, null, null);
    }

    /**
     * Create message dialog
     *
     * @param context
     * @param mode
     * @return
     */
    public static CommonDialog create(Context context, int mode) {
        return create(context, mode, null, null);
    }

    /**
     * Create message dialog
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static CommonDialog create(Context context, CharSequence title, CharSequence content) {
        return create(context, CommonDialog.MODE_NORMAL, title, content);
    }

    /**
     * Create message dialog
     *
     * @param context
     * @param mode
     * @param resTitle
     * @param resContent
     * @return
     */
    public static CommonDialog create(Context context, int mode, int resTitle, int resContent) {
        return create(context, mode, context.getResources().getString(resTitle), context.getResources().getString(resContent));
    }

    /**
     * Create message dialog
     *
     * @param context
     * @param mode
     * @param title
     * @param content
     * @return
     */
    public static CommonDialog create(Context context, int mode, CharSequence title, CharSequence content) {
        CommonDialog dialog = new CommonDialog(context, mode, title, content);
        return dialog;
    }

    public static final int MODE_NORMAL = 1;
    //单按钮模式（无取消按钮）
    public static final int MODE_NONE_CANCEL = 2;

    private View mLine;
    private TextView mTitleView;
    private TextView mContentView;
    private Button mPositiveButton;
    private Button mNegativeButton;

    private int mMode = MODE_NORMAL;
    private CharSequence mTitle;
    private CharSequence mContent;
    private CharSequence mPositive;
    private CharSequence mNegative;
    private View.OnClickListener mPositiveClickListener;
    private View.OnClickListener mNegativeClickListener;

    public CommonDialog(Context context, int mode) {
        super(context, R.style.CommonDialog);
        this.mMode = mode;
    }

    public CommonDialog(Context context, int mode, CharSequence title, CharSequence content) {
        super(context, R.style.CommonDialog);
        this.mMode = mode;
        this.mTitle = title;
        this.mContent = content;
    }

    public CommonDialog(Context context, int theme, int mode, CharSequence title, CharSequence content) {
        super(context, theme);
        this.mMode = mode;
        this.mTitle = title;
        this.mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_commom);
        mTitleView = (TextView) findViewById(R.id.tv_dialog_title);
        mContentView = (TextView) findViewById(R.id.tv_dialog_message);

        mNegativeButton = (Button) findViewById(R.id.btn_dialog_negative);
        mLine = findViewById(R.id.dialog_line);
        mPositiveButton = (Button) findViewById(R.id.btn_dialog_positive);

        //标题
        if (!TextUtils.isEmpty(mTitle)) {
            mTitleView.setText(mTitle);
            mTitleView.setVisibility(View.VISIBLE);
        } else {
            mTitleView.setVisibility(View.GONE);
        }

        mContentView.setText(mContent);

        if (!TextUtils.isEmpty(mPositive)) {
            mPositiveButton.setText(mPositive);
        }
        if (!TextUtils.isEmpty(mNegative)) {
            mNegativeButton.setText(mNegative);
        }
        //左按钮（取消）
        if (mNegativeClickListener != null) {
            mNegativeButton.setOnClickListener(mNegativeClickListener);
        } else {
            mNegativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        //右按钮(确定)
        if (mPositiveClickListener != null) {
            mPositiveButton.setOnClickListener(mPositiveClickListener);
        } else {
            mPositiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        //单按钮模式（无取消按钮）
        if (mMode == MODE_NONE_CANCEL) {
            mLine.setVisibility(View.GONE);
            mNegativeButton.setVisibility(View.GONE);
            mPositiveButton.setBackgroundResource(R.drawable.dialog_btn_single_bg);
        }
    }

    @Override
    public void setTitle(int resId) {
        String title = getContext().getResources().getString(resId);
        setTitle(title);
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        if (mTitleView != null) {
            mTitleView.setText(title);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    public void setContent(int resId) {
        String content = getContext().getResources().getString(resId);
        setContent(content);
    }

    public void setContent(CharSequence content) {
        mContent = content;
        if (mContentView != null) {
            mContentView.setText(content);
        }
    }

    /**
     * 设置按钮（否定）
     *
     * @param resId
     * @param onClickListener
     */
    public void setOnNegativeClick(int resId, final View.OnClickListener onClickListener) {
        mNegative = getContext().getResources().getString(resId);
        mNegativeClickListener = onClickListener;
        if (mNegativeButton != null) {
            mNegativeButton.setText(resId);
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置按钮（否定）
     *
     * @param name
     * @param onClickListener
     */
    public void setOnNegativeClick(CharSequence name, final View.OnClickListener onClickListener) {
        mNegative = name;
        mNegativeClickListener = onClickListener;
        if (mNegativeButton != null) {
            mNegativeButton.setText(name);
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置按钮（确定）
     *
     * @param resId
     * @param onClickListener
     */
    public void setOnPositiveClick(int resId, final View.OnClickListener onClickListener) {
        mPositive = getContext().getResources().getString(resId);
        mPositiveClickListener = onClickListener;
        if (mPositiveButton != null) {
            mPositiveButton.setText(resId);
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置按钮（确定）
     *
     * @param name
     * @param onClickListener
     */
    public void setOnPositiveClick(CharSequence name, final View.OnClickListener onClickListener) {
        mPositive = name;
        mPositiveClickListener = onClickListener;
        if (mPositiveButton != null) {
            mPositiveButton.setText(name);
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }
}
