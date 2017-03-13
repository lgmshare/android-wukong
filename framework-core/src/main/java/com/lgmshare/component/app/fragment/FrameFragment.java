package com.lgmshare.component.app.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.lgmshare.component.logger.Logger;

/**
 * 框架fragment基类 （版本3.0及以上）
 *
 * @author: lim
 * @description: 框架fragment类 （版本3.0及以上）
 * @email: lgmshare@gmail.com
 * @datetime 2014年7月7日  上午11:04:44
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class FrameFragment extends Fragment {

    /* 调试TAG */
    protected final String TAG = FrameFragment.this.getClass().getSimpleName();
    /* 系统软键盘 */
    protected InputMethodManager mInputMethodManager;

    protected FragmentState mState = FragmentState.NONE;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.v(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.v(TAG, "onCreate");
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mState = FragmentState.CREATE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.v(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.v(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.v(TAG, "onStart");
        mState = FragmentState.START;
    }

    @Override
    public void onResume() {
        super.onResume();
        mState = FragmentState.RESUME;
        Logger.v(TAG, "onResume");
        //TODO 打印内存状态
        Logger.debugMemory(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        mState = FragmentState.PAUSE;
        Logger.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        mState = FragmentState.STOP;
        Logger.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mState = FragmentState.DESTROY;
        Logger.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mState = FragmentState.DETACH;
        Logger.v(TAG, "onDetach");
    }

    /**
     * 隐藏输入法
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected boolean hideSoftInput(View v) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CUPCAKE) {
            return mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    /**
     * 开关输入法
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected void toggleSoftInput() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CUPCAKE) {
            mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
