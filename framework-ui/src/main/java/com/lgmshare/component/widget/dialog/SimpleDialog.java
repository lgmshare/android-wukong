package com.lgmshare.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lgmshare.component.widget.R;


/**
 * 通用自定义样式
 *
 * @author: lim
 * @ClassName: SimpleDialog.java
 * @datetime 2014-11-11 上午9:38:40
 */
public class SimpleDialog extends Dialog {

    public static final int MODE_NORMAL = 1;
    //单按钮模式（无左按钮）
    public static final int MODE_ONLY_POSITIVE = 2;

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

    public SimpleDialog(Context context, int mode) {
        super(context, R.style.CommonDialog);
        this.mMode = mode;
    }

    public SimpleDialog(Context context, int mode, int title, int content) {
        this(context, mode, context.getString(title), context.getString(content));
    }

    public SimpleDialog(Context context, int mode, CharSequence title, CharSequence content) {
        this(context, R.style.CommonDialog, mode, title, content);
    }

    public SimpleDialog(Context context, int theme, int mode, CharSequence title, CharSequence content) {
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
        setContentView(R.layout.dialog_simple);
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
        if (mMode == MODE_ONLY_POSITIVE) {
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
