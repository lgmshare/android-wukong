package com.lgmshare.component.app.dialog;

import android.app.Dialog;
import android.content.Context;

import com.lgmshare.component.logger.Logger;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/19 17:56
 */
public abstract class FrameDialog extends Dialog {

    protected final String TAG = FrameDialog.this.getClass().getSimpleName();

    private Context mContext;

    private boolean mAttached = false;

    public FrameDialog(Context context) {
        super(context);
        init(context);
    }

    public FrameDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public FrameDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    public void show() {
        Logger.d(TAG, "show");
        if (mAttached || mContext == null || isShowing()) {
            return;
        }
        mAttached = true;
        super.show();
    }

    @Override
    public void dismiss() {
        Logger.d(TAG, "dismiss");
        if (!mAttached || mContext == null) {
            return;
        }
        super.dismiss();
    }

    @Override
    public void onAttachedToWindow() {
        Logger.d(TAG, "onAttachedToWindow");
        mAttached = true;
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        Logger.d(TAG, "onDetachedFromWindow");
        mAttached = false;
        super.onDetachedFromWindow();
    }
}
