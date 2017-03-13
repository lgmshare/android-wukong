package com.lgmshare.component.app;

import android.app.Application;
import android.content.Context;

import com.lgmshare.component.annotation.PluginApi;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/8/11 17:39
 * @email: lgmshare@gmail.com
 */
@PluginApi
public final class FrameContext {

    private static FrameContext mInstance;

    /* App Context */
    private static Context mContext;
    /* App功能配置项 */
    private static FrameConfiguration mConfiguration;

    public static FrameContext setContext(Application application, FrameConfiguration configuration) {
        if (mInstance == null) {
            mInstance = new FrameContext(application, configuration);
        }
        return mInstance;
    }

    /**
     * 功能初始化
     */
    public FrameContext(Application application, FrameConfiguration configuration) {
        mContext = application.getApplicationContext();
        mConfiguration = configuration;
    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean isDebugModel() {
        return mConfiguration.isEnableDebugModel();
    }

    public static boolean isDebugLogSave() {
        return mConfiguration.isEnableDebugLogSave();
    }

    public static boolean isStrictMode() {
        return mConfiguration.isEnableStrictMode();
    }

    public static String getDebugLogTag() {
        return mConfiguration.getDebugLogTag();
    }

    public static String getDebugLogFilePath() {
        return mConfiguration.getDebugLogFilePath();
    }

    public static String getCrashFilePath() {
        return mConfiguration.getCrashFilePath();
    }

    public static String getDownloadFilePath() {
        return mConfiguration.getDownloadFilePath();
    }
}
