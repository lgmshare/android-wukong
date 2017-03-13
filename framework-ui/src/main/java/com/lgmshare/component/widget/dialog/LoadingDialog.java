package com.lgmshare.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgmshare.component.widget.R;


/**
 * @author: lim
 * @ClassName: LoadingDialog
 * @description: 加载等待框
 * @email: lgmshare@gmail.com
 * @datetime 2014-6-3  上午11:03:42
 */
public class LoadingDialog extends Dialog {

    private TextView mContentView;

    private ProgressBar mProgressBar;
    private CharSequence mMessage;

    private Drawable mIndeterminateDrawable;

    public LoadingDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    public LoadingDialog(Context context, CharSequence message) {
        super(context, R.style.CommonDialog);
        this.mMessage = message;
    }

    public LoadingDialog(Context context, int theme, CharSequence message) {
        super(context, theme);
        this.mMessage = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_loading);
        mContentView = (TextView) findViewById(R.id.tips_msg);
        mProgressBar = (ProgressBar) findViewById(R.id.pressbarupgrade);
        if (!TextUtils.isEmpty(mMessage)) {
            mContentView.setVisibility(View.VISIBLE);
            mContentView.setText(mMessage);
        }
        if (mIndeterminateDrawable != null) {
            mProgressBar.setIndeterminateDrawable(mIndeterminateDrawable);
        }
    }

    public void setMessage(int resId) {
        this.mMessage = getContext().getResources().getString(resId);
        if (mContentView != null) {
            mContentView.setVisibility(View.VISIBLE);
            mContentView.setText(resId);
        }
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
        if (mContentView != null) {
            mContentView.setVisibility(View.VISIBLE);
            mContentView.setText(message);
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (mProgressBar != null) {
            mProgressBar.setIndeterminateDrawable(drawable);
        } else {
            mIndeterminateDrawable = drawable;
        }
    }

    /**
     * Create progress dialog
     *
     * @param context
     * @param resId
     * @return dialog
     */
    public static LoadingDialog create(Context context, int resId) {
        return create(context, context.getResources().getString(resId));
    }

    /**
     * Create progress dialog
     *
     * @param context
     * @param message
     * @return dialog
     */
    public static LoadingDialog create(Context context, CharSequence message) {
        LoadingDialog dialog = new LoadingDialog(context, message);
        dialog.setMessage(message);
        return dialog;
    }
}