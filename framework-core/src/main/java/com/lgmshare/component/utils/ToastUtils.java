package com.lgmshare.component.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast封装工具类
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2014-6-3 10:24
 * @email: lgmshare@gmail.com
 */
public class ToastUtils {

    /**
     * Show Toast in long time by system
     *
     * @param context
     * @param strMsg
     */
    public static void show(final Context context, final String strMsg) {
        if (context == null || TextUtils.isEmpty(strMsg)) {
            return;
        }
        Toast.makeText(context, strMsg, Toast.LENGTH_LONG).show();
    }

    /**
     * Show Toast in long time by system
     *
     * @param context
     * @param resId
     */
    public static void show(final Context context, final int resId) {
        if (context == null || resId == 0) {
            return;
        }
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * Show Toast in long time by system
     *
     * @param context
     * @param strMsg
     */
    public static void showShort(final Context context, final String strMsg) {
        if (context == null || TextUtils.isEmpty(strMsg)) {
            return;
        }
        Toast.makeText(context, strMsg, Toast.LENGTH_LONG).show();
    }

    /**
     * Show Toast in long time by system
     *
     * @param context
     * @param resId
     */
    public static void showShort(final Context context, final int resId) {
        if (context == null || resId == 0) {
            return;
        }
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
