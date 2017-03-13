package com.lgmshare.base.ui.activity.base;

import android.app.ActionBar;
import android.os.Bundle;

import com.lgmshare.base.AppContext;
import com.lgmshare.base.ui.dialog.LoadingDialog;
import com.lgmshare.base.util.analytics.AnalyticsUtil;

import com.lgmshare.component.ui.acticity.ActivityState;
import com.lgmshare.component.ui.acticity.FrameFragmentActivity;
import com.lgmshare.component.utils.ToastUtil;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/1 15:42
 * @email: lgmshare@gmail.com
 */
public abstract class BaseFragmentActivity extends FrameFragmentActivity {

    /* 加载等待框 */
    private LoadingDialog mLoadingDialog;
    /* 页面头部 */
    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    private void initActionBar() {
        mActionBar = getActionBar();
        if (mActionBar == null) {
            return;
        }
        // 隐藏Home图标和Title文字
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setHomeButtonEnabled(false);
        // 隐藏Label标签
        mActionBar.setDisplayShowTitleEnabled(false);
        // 对ActionBar启用自定义View
        mActionBar.setDisplayShowCustomEnabled(true);
        if (isHideActionbar()) {
            mActionBar.hide();
        }
    }

    protected abstract boolean isHideActionbar();

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsUtil.onPause(this);
    }

    @Override
    protected void onStop() {
        AppContext.getRequestQueue().cancelAll(this);
        dismissProgressDialog();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        dismissProgressDialog();
        super.onBackPressed();
    }

    protected void showToastMessage(String msg) {
        if (!isFinishing() && getActivityState() == ActivityState.RESUME) {
            ToastUtil.show(ATHIS, msg);
        }
    }

    /**
     * 显示加载框
     */
    protected void showProgressDialog() {
        if (!isFinishing() && getActivityState() == ActivityState.RESUME) {
            mLoadingDialog = LoadingDialog.create(ATHIS, "");
            mLoadingDialog.show();
        }
    }

    /**
     * 显示加载框
     *
     * @param message
     */
    protected void showProgressDialog(CharSequence message) {
        if (!isFinishing() && getActivityState() == ActivityState.RESUME) {
            mLoadingDialog = LoadingDialog.create(ATHIS, message);
            mLoadingDialog.show();
        }
    }

    /**
     * 显示加载框
     *
     * @param message
     * @param cancelable 是否可以取消
     */
    protected void showProgressDialog(CharSequence message, boolean cancelable) {
        if (!isFinishing() && getActivityState() == ActivityState.RESUME) {
            mLoadingDialog = LoadingDialog.create(ATHIS, message);
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.setCanceledOnTouchOutside(cancelable);
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏加载等待框
     */
    protected void dismissProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
