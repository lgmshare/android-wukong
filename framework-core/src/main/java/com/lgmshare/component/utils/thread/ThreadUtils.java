package com.lgmshare.component.utils.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @author: lim
 * @ClassName: ThreadPool
 * @description: 转到UI线执行刷新操作
 * @Email limshare@gmail.com
 * @datetime 2014-11-4 下午1:09:14
 */
public class ThreadUtils {

    private static HandlerThread handerThread = null;
    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    /**
     * 创建一个异步线程Looper
     *
     * @return
     */
    public static Looper getNUILooper() {
        if (handerThread == null) {
            synchronized (ThreadUtils.class) {
                if (handerThread == null) {
                    handerThread = new HandlerThread("TheadUtils.handerThread");
                    handerThread.start();
                }
            }
        }
        return handerThread.getLooper();
    }

    /**
     * 在UI线程中运行任务
     *
     * @param runnable
     */
    public static void runOnUITask(Runnable runnable) {
        uiHandler.post(runnable);
    }

    /**
     * 在UI线程中运行任务
     *
     * @param runnable
     * @param delayTime
     */
    public static void runOnUITask(Runnable runnable, long delayTime) {
        uiHandler.postDelayed(runnable, delayTime);
    }

    /**
     * 在异步线程中运行任务
     *
     * @param runnable
     */
    public static void runOnNonUITask(Runnable runnable) {
        ThreadPool.runOnNonUITask(runnable);
    }
}