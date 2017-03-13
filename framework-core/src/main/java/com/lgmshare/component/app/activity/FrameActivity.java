package com.lgmshare.component.app.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lgmshare.component.annotation.PluginApi;
import com.lgmshare.component.logger.Logger;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description: 基础activity
 * @datetime: 2014-6-3 10:14
 * @email: lgmshare@gmail.com
 */
public abstract class FrameActivity extends Activity {

    protected final String TAG = FrameActivity.this.getClass().getSimpleName();

    @PluginApi
    protected Activity ATHIS;
    /* 系统软键盘 */
    @PluginApi
    protected InputMethodManager mInputMethodManager;
    /* 当前Activity状态 */
    protected ActivityState mActivityState = ActivityState.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.v(TAG, "onCreate");
        ATHIS = this;
        mActivityState = ActivityState.CREATE;
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ActivityStackerManager.getInstance().pushActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityState = ActivityState.START;
        Logger.v(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityState = ActivityState.RESUME;
        Logger.v(TAG, "onResume");
        //TODO 打印内存状态
        Logger.debugMemory(TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityState = ActivityState.PAUSE;
        Logger.v(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityState = ActivityState.STOP;
        Logger.v(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        ActivityStackerManager.getInstance().popActivity(this);
        super.onDestroy();
        mActivityState = ActivityState.DESTROY;
        Logger.v(TAG, "onDestroy");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Logger.v(TAG, "onUserInteraction");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.v(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.v(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.v(TAG, "onRestart");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Logger.v(TAG, "onBackPressed");
    }

    @Override
    public Resources getResources() {
        //FIXME 处理应用的字体大小不受系统字体大小影响
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();//使用默认样式
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击空白区域隐藏输入法
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.CUPCAKE) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                hideSoftInput();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 隐藏系统软键盘
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected void hideSoftInput() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏系统软键盘
     *
     * @param view
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected void hideSoftInput(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.CUPCAKE) {
            if (view != null) {
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                hideSoftInput();
            }
        }
    }

    /**
     * 输入法开关
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected void toggleSoftInput() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.CUPCAKE) {
            mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
