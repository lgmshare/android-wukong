package com.lgmshare.component.app.activity;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * @author: lim
 * @description: 自定义Activity管理栈
 * @email: lgmshare@gmail.com
 * @datetime 2014年6月24日  下午4:39:59
 */
public final class ActivityStackerManager {

    private static String TAG = "ActivityStackerManager";

    private static ActivityStackerManager mInstance;
    private static Stack<Activity> mActivityStack = new Stack<Activity>();

    public static ActivityStackerManager getInstance() {
        if (mInstance == null) {
            mInstance = new ActivityStackerManager();
        }
        return mInstance;
    }

    private ActivityStackerManager() {
    }

    /**
     * 获取当前Activity（栈中最后一个压入的[栈顶]）
     */
    public Activity getCurrentActivity() {
        if (!mActivityStack.isEmpty()) {
            return mActivityStack.lastElement();
        }
        return null;
    }

    /**
     * 添加Activity到栈中
     */
    public void pushActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.push(activity);
            Log.v(TAG, "activity size:" + mActivityStack.size() + " of pushActivity");
        }
    }

    /**
     * 移除指定的Activity
     */
    public void popActivity(Activity activity) {
        if (activity != null && !mActivityStack.isEmpty()) {
            mActivityStack.remove(activity);
            Log.v(TAG, "activity size:" + mActivityStack.size() + " of popActivity");
        }
    }

    /**
     * 结束当前Activity（栈中最后一个压入的[栈顶]）
     */
    public void finishCurrentActivity() {
        if (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束指定Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            popActivity(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param clazz
     */
    public void finishActivity(Class<?> clazz) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(clazz)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束此Activity在栈前的所有Activity,不包含此Activity
     *
     * @param clazz 标记activity
     */
    public void finishTopActivity(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null || activity.getClass().equals(clazz)) {
                break;
            }
            finishActivity(activity);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        while (!mActivityStack.isEmpty()) {
            finishActivity(getCurrentActivity());
        }
        mActivityStack.clear();
    }
}