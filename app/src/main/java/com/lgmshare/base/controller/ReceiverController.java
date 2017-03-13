package com.lgmshare.base.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author: lim
 * @description: 广播发送控制器
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/4
 */
public class ReceiverController {

    public static void sendReceiver(Context context, String action) {
        Intent intent = new Intent(action);
        context.sendBroadcast(intent);
    }

    public static void sendReceiver(Context context, String action, Bundle bd) {
        Intent intent = new Intent(action);
        intent.putExtras(bd);
        context.sendBroadcast(intent);
    }

    public static void timeUpdate(){

    }

}
