package com.lgmshare.base.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lgmshare.component.utils.ToastUtil;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/10 11:18
 * @email: lgmshare@gmail.com
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            mContext = context;
            checkForNewNotifications();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForNewNotifications() {
        ToastUtil.show(mContext, "AlarmReceiver");
    }
}
