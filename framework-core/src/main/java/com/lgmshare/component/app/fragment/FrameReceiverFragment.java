package com.lgmshare.component.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment 广播基类
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/15 17:28
 */
public abstract class FrameReceiverFragment extends FrameFragment {

    // Receiver Filters
    private List<String> mBroadcastFilters = new ArrayList<String>();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            broadcastReceiver(intent.getAction());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastFilter(mBroadcastFilters);
    }

    @Override
    public void onStart() {
        super.onStart();
        register();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
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
        getActivity().registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * 注销广播
     */
    private void unregister() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
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
