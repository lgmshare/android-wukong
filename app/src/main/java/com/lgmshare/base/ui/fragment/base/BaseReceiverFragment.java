package com.lgmshare.base.ui.fragment.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.app.fragment.FragmentState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/24.
 */
public abstract class BaseReceiverFragment extends BaseFragment {

    // Receiver Filters
    private List<String> mBroadcastFilters = new ArrayList<String>();
    private Set<String> mBroadcastAction = new HashSet<>();
    private boolean mBroadcastUpdate = false;

    protected BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logger.d(TAG, "onReceive:" + action);
            //界面未在前台，暂不发送通知
            if (getFragmentState() == FragmentState.RESUME) {
                broadcastReceiver(action);
            } else {
                mBroadcastAction.add(action);
                mBroadcastUpdate = true;
            }
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
        //界面回到前台，发送通知
        sendNotify();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }

    private synchronized void sendNotify() {
        if (getActivity().isFinishing()) {
            return;
        }
        if (mBroadcastUpdate) {
            mBroadcastUpdate = false;
            Iterator ir = mBroadcastAction.iterator();
            while (ir.hasNext()) {
                broadcastReceiver((String) ir.next());
                ir.remove();
            }
        }
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
     * add receiver filter
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
