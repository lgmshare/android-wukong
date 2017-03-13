package com.lgmshare.component.utils.thread;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Looper;

import com.lgmshare.component.annotation.PluginApi;
import com.lgmshare.component.logger.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: lim
 * @ClassName: ThreadPool
 * @description: 开启一个线程来执行UI之外的耗时操作
 * @Email limshare@gmail.com
 * @datetime 2014-11-4 下午1:09:14
 */
public class ThreadPool {

    private static final String TAG = "ThreadPool";

    private static final int CORE_POOL_SIZE = 4;  // 线程池的大小最好设置成为<CUP核数*2>
    private static final int MAX_POOL_SIZE = 8;   // 设置线程池的最大线程数
    private static final int KEEP_ALIVE_TIME = 10;// 设置线程的存活时间

    public static final int MODE_NONE = 0;
    public static final int MODE_CPU = 1;
    public static final int MODE_NETWORK = 2;

    public static final JobContext JOB_CONTEXT_STUB = new JobContextStub();

    private ResourceCounter mCpuCounter = new ResourceCounter(2);
    private ResourceCounter mNetworkCounter = new ResourceCounter(2);
    private final Executor mExecutor;
    private static final AtomicLong SEQ = new AtomicLong(0L);

    public static ThreadPool getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，
     * 而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class InstanceHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        public static final ThreadPool INSTANCE = new ThreadPool();
    }

    @PluginApi
    public ThreadPool() {
        this(TAG, CORE_POOL_SIZE, MAX_POOL_SIZE);
    }

    @PluginApi
    public ThreadPool(String name, int coreSize, int maxSize) {
        this(name, coreSize, maxSize, new PriorityBlockingQueue<Runnable>());
    }

    /**
     * @param name
     * @param coreSize 线程池的大小
     * @param maxSize  最大线程数
     * @param queue    工作队列
     */
    @PluginApi
    public ThreadPool(String name, int coreSize, int maxSize, BlockingQueue<Runnable> queue) {
        if (coreSize <= 0) {
            coreSize = 1;
        }
        if (maxSize <= coreSize) {
            maxSize = coreSize;
        }
        // 创建线程池工厂
        ThreadFactory factory = new PriorityThreadFactory(name, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        mExecutor = new ThreadPoolExecutor(coreSize, maxSize, KEEP_ALIVE_TIME, TimeUnit.SECONDS, queue, factory);
    }

    @PluginApi
    public <T> Future<T> submit(Job<T> job) {
        return submit(job, null, Priority.NORMAL);
    }

    @PluginApi
    public <T> Future<T> submit(Job<T> job, Priority priority) {
        return submit(job, null, priority);
    }

    @PluginApi
    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener) {
        return submit(job, listener, Priority.NORMAL);
    }

    @PluginApi
    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener, Priority priority) {
        Worker<T> w = generateWorker(job, listener, priority);
        this.mExecutor.execute(w);
        return w;
    }

    private <T> Worker<T> generateWorker(Job<T> job, FutureListener<T> listener, Priority priority) {
        Worker<T> worker;
        switch (priority) {
            case HIGH:
                worker = new PriorityWorker<T>(job, listener, priority.priorityInt, false);
                break;
            case LOW:
                worker = new PriorityWorker<T>(job, listener, priority.priorityInt, false);
                break;
            case NORMAL:
                worker = new PriorityWorker<T>(job, listener, priority.priorityInt, true);
                break;
            default:
                worker = new PriorityWorker<T>(job, listener, priority.priorityInt, false);
        }

        return worker;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @PluginApi
    public static void runOnNonUITask(final Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            getInstance().submit(new Job<Object>() {
                public Object run(JobContext jc) {
                    runnable.run();
                    return null;
                }
            });
        } else {
            runnable.run();
        }
    }


    @PluginApi
    public interface Job<T> {
        T run(JobContext jobContext);
    }

    @PluginApi
    public interface JobContext {
        @PluginApi
        boolean isCancelled();

        @PluginApi
        boolean setMode(int mode);
    }

    private static class JobContextStub implements JobContext {
        public boolean isCancelled() {
            return false;
        }

        public boolean setMode(int mode) {
            return true;
        }
    }

    public enum Priority {
        LOW(1),
        NORMAL(2),
        HIGH(3);

        int priorityInt;

        Priority(int priority) {
            this.priorityInt = priority;
        }
    }

    private class PriorityWorker<T> extends Worker<T> implements Comparable<PriorityWorker<T>> {
        private final int mPriority;
        private final boolean mFilo;
        private final long mSeqNum;

        public PriorityWorker(Job<T> job, FutureListener<T> listener, int priority, boolean filo) {
            super(job, listener);
            mPriority = priority;
            mFilo = filo;
            mSeqNum = ThreadPool.SEQ.getAndIncrement();
        }

        public int compareTo(PriorityWorker<T> another) {
            return mPriority < another.mPriority ? 1 : mPriority > another.mPriority ? -1 : subCompareTo(another);
        }

        private int subCompareTo(PriorityWorker<T> another) {
            int result = mSeqNum > another.mSeqNum ? 1 : mSeqNum < another.mSeqNum ? -1 : 0;
            return mFilo ? -result : result;
        }
    }

    private static class ResourceCounter {
        public int value;

        public ResourceCounter(int v) {
            this.value = v;
        }
    }

    private class Worker<T> implements Runnable, Future<T>, JobContext {

        private Job<T> mJob;
        private FutureListener<T> mListener;
        private CancelListener mCancelListener;
        private ResourceCounter mWaitOnResource;
        private volatile boolean mIsCancelled;
        private boolean mIsDone;
        private T mResult;
        private int mMode;

        public Worker(Job<T> job, FutureListener<T> listener) {
            mJob = job;
            mListener = listener;
        }

        public void run() {
            if (mListener != null) {
                mListener.onFutureBegin(this);
            }
            T result = null;

            if (setMode(MODE_CPU)) {
                try {
                    result = this.mJob.run(this);
                } catch (Throwable ex) {
                    Logger.e(TAG, "Exception in running a job", ex);
                }
            }

            synchronized (this) {
                setMode(MODE_NONE);
                mResult = result;
                mIsDone = true;
                notifyAll();
            }
            if (mListener != null)
                mListener.onFutureDone(this);
        }

        public synchronized void cancel() {
            if (mIsCancelled)
                return;
            this.mIsCancelled = true;
            if (mWaitOnResource != null) {
                synchronized (mWaitOnResource) {
                    mWaitOnResource.notifyAll();
                }
            }
            if (mCancelListener != null)
                mCancelListener.onCancel();
        }

        public boolean isCancelled() {
            return mIsCancelled;
        }

        public synchronized boolean isDone() {
            return mIsDone;
        }

        public synchronized T get() {
            while (!mIsDone) {
                try {
                    wait();
                } catch (Exception ex) {
                    Logger.e(TAG, "ignore exception", ex);
                }
            }

            return this.mResult;
        }

        public void waitDone() {
            get();
        }

        public synchronized void setCancelListener(
                CancelListener listener) {
            mCancelListener = listener;
            if ((mIsCancelled) && (mCancelListener != null))
                mCancelListener.onCancel();
        }

        public boolean setMode(int mode) {
            ResourceCounter rc = modeToCounter(mMode);
            if (rc != null)
                releaseResource(rc);
            mMode = MODE_NONE;

            rc = modeToCounter(mode);
            if (rc != null) {
                if (!acquireResource(rc)) {
                    return false;
                }
                mMode = mode;
            }

            return true;
        }

        private ResourceCounter modeToCounter(int mode) {
            if (mode == MODE_CPU)
                return ThreadPool.this.mCpuCounter;
            if (mode == MODE_NETWORK) {
                return ThreadPool.this.mNetworkCounter;
            }
            return null;
        }

        private boolean acquireResource(ResourceCounter counter) {
            while (mWaitOnResource != null) {
                synchronized (this) {
                    if (mIsCancelled) {
                        mWaitOnResource = null;
                        return false;
                    }
                    mWaitOnResource = counter;
                }

                synchronized (counter) {
                    if (counter.value > 0)
                        counter.value -= 1;
                    else {
                        try {
                            counter.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Logger.e(TAG, "InterruptedException error", e);
                        }
                    }
                }
            }
            synchronized (this) {
                mWaitOnResource = null;
            }

            return true;
        }

        private void releaseResource(ResourceCounter counter) {
            synchronized (counter) {
                counter.value += 1;
                counter.notifyAll();
            }
        }
    }
}