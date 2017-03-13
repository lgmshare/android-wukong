package com.lgmshare.base.model;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/9 11:05
 * @email: lgmshare@gmail.com
 */
public class DeviceInfo {

    private float screenWidth;
    private float screenHeight;
    private int versionCode;
    private String versionName;
    private String versionSdk;
    private String phoneName;
    private String udid;

    public DeviceInfo(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }
}
