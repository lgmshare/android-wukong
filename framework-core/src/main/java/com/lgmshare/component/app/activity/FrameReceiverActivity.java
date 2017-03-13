package com.lgmshare.component.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * activity 广播基类
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/15 17:28
 */
public abstract class FrameReceiverActivity extends FrameActivity {

    // Receiver Filters
    private List<String> mBroadcastFilters = new ArrayList<String>();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            broadcastReceiver(intent.getAction());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastFilter(mBroadcastFilters);
    }

    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    /**
     * 注册广播
     */
    private void register() {
        IntentFilter filter = new IntentFilter();
        int length = mBroadcastFilters.size();
        for (int i = 0; i < length; i++) {
            filter.addAction(mBroadcastFilters.get(i));
        }
        this.registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * 注销广播
     */
    private void unregister() {
        this.unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * addItem receiver filter
     *
     * @param actions
     */
    protected abstract void broadcastFilter(List<String> actions);

    /**
     * notify update message
     *
     * @param action
     */
    protected abstract void broadcastReceiver(String action);
}
