package com.lgmshare.component.app;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.logger.LoggerConfiguration;


/**
 * @author: lim.
 * @email: lgmshare@gmail.com
 * @Version V1.0
 * @Description
 * @datetime : 2015/5/11 11:32
 */
public abstract class FrameApplication extends Application {

    protected static final String TAG = "FrameApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //框架配置项
        FrameConfiguration config = onBuildFrameConfiguration();
        if (config == null) {
            config = new FrameConfiguration.Builder().build();
        }
        FrameContext.setContext(this, config);
        FrameCrashHandler.init(this);

        //日志模块初始化(必须初始化)
        LoggerConfiguration loggerConfig = Logger.init(FrameContext.getDebugLogTag());
        loggerConfig.setPrint(FrameContext.isDebugModel());//是否在控制台打印日志
        loggerConfig.setFormat(false);//日志格式化
        loggerConfig.setLevel(Log.VERBOSE);//日志打印级别
        loggerConfig.hideThreadInfo();//线程信息
        loggerConfig.setMethodCount(1);//方法层级
        loggerConfig.setMethodOffset(0);//方法缩进
        loggerConfig.setWriteLogFile(FrameContext.isDebugLogSave());//是否将日志保持到文件中
        loggerConfig.setLogFilePath(FrameContext.getDebugLogFilePath());//日志文件保存路径
        loggerConfig.setLogFileName(FrameContext.getDebugLogTag());//日志tag
        Logger.d(TAG, "Framework Init Complete; Version:" + FrameConfig.VERSION);
    }

    public abstract FrameConfiguration onBuildFrameConfiguration();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.d(TAG, "Configuration Changed");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.e(TAG, "Warning! Memory Cache Low");
        System.gc();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //应用需要释放更多内存空间
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //应用结束时调用
        Logger.d(TAG, "Terminate");
        System.exit(0);
    }
}
