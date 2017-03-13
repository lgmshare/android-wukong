package com.lgmshare.base.ui.fragment.base;

import android.app.Activity;

import com.lgmshare.base.AppContext;
import com.lgmshare.base.ui.dialog.LoadingDialog;
import com.lgmshare.base.util.analytics.AnalyticsUtil;

import com.lgmshare.component.app.fragment.FrameSupportFragment;
import com.lgmshare.component.utils.ToastUtil;

public abstract class BaseFragment extends FrameSupportFragment {

    /**
     * 加载等待框
     */
    private LoadingDialog mLoadingDialog;
    /**
     * Fragment动作监听
     */
    protected OnFragmentMessageListener mOnFragmentMessageListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentMessageListener) {
            mOnFragmentMessageListener = (OnFragmentMessageListener) activity;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsUtil.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        AnalyticsUtil.onPageEnd(TAG);
    }

    @Override
    public void onStop() {
        AppContext.getRequestQueue().cancelAll(this);
        dismissProgressDialog();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        dismissProgressDialog();
        super.onDestroyView();
    }

    protected void showToastMessage(String msg) {
        if (getActivity() != null && !getActivity().isFinishing())
            ToastUtil.show(getActivity(), msg);
    }

    /**
     * 显示加载框
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            mLoadingDialog = LoadingDialog.create(getActivity(), msg);
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
        if (getActivity() != null && !getActivity().isFinishing()) {
            mLoadingDialog = LoadingDialog.create(getActivity(), message);
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.setCanceledOnTouchOutside(cancelable);
            mLoadingDialog.show();
        }
    }

    /**
     * 隐藏加载等待框
     */
    public void dismissProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * Activity与Fragment传递数据
     */
    public interface OnFragmentMessageListener {

        void onFragmentSendMessage(int position, String message);

        void onFragmentChange(int oldPosition, int newPosition);
    }
}
