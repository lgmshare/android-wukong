package com.lgmshare.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lgmshare.base.recevier.AlarmReceiver;
import com.lgmshare.component.logger.Logger;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/15
 */
public class AlarmService extends Service {

    public static final String TAG = "AlarmService";

    private static final int UPDATE_INTERVAL_TIME = 30;

    private boolean mThreadRunning = true;
    private int mUpdateTime;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.v(TAG, "onCreate");
        startUpdateTask();
    }

    /**
     * START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。
     * 随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。
     * 如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
     * START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。
     * START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
     * START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.v(TAG, "onStartCommand (flags:" + flags + "  startId:" + startId + ")");
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.v(TAG, "onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.v(TAG, "onDestroy");
        stopUpdateTask();
        super.onDestroy();
    }

    private void startUpdateTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mThreadRunning) {
                    if (mUpdateTime >= UPDATE_INTERVAL_TIME) {
                        mUpdateTime = 0;
                        Logger.v(TAG, "ThreadRunning Send");
                        sendBroadcast(new Intent(AlarmService.this, AlarmReceiver.class));
                    } else {
                        mUpdateTime++;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, TAG).start();
    }

    private void stopUpdateTask() {
        mThreadRunning = false;
        mUpdateTime = 0;
    }
}
