package com.lgmshare.component.utils.thread;

import android.os.Process;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个线程工厂
 */
public class PriorityThreadFactory implements ThreadFactory {

    private final AtomicInteger mNumber = new AtomicInteger();
    private final String mName; // 线程的名称
    private final int mPriority;// 线程的优先级

    public PriorityThreadFactory(String name, int priority) {
        mName = name;
        mPriority = priority;
    }

    public Thread newThread(Runnable r) {
        return new Thread(r, mName + '-' + mNumber.getAndIncrement()) {
            public void run() {
                // 设置线程的优先级
                Process.setThreadPriority(mPriority);
                super.run();
            }
        };
    }
}