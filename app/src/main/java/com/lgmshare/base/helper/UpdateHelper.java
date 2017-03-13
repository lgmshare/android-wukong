package com.lgmshare.base.helper;

import android.content.Context;

import com.lgmshare.base.AppContext;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/11/19 10:57
 * @email: lgmshare@gmail.com
 */
public class UpdateHelper {

    private static final int NOTICE_DIALOG = 1;
    private static final int NOTICE_NOTIFICATION = 2;

    private static UpdateHelper mInstance;

    private Context mContext;

    private UpdateHelper(Context context) {
        mContext = context;
    }

    public static UpdateHelper getInstance() {
        if (mInstance == null) {
            mInstance = new UpdateHelper(AppContext.getContext());
        }
        return mInstance;
    }

    public void checkUpdate() {
        String updateMessage = "";
        String apkUrl = "";
        int apkCode = 1;
        int mTypeOfNotice = 1;
        int versionCode = AppContext.getVersionCode();
        if (apkCode > versionCode) {
            if (mTypeOfNotice == NOTICE_NOTIFICATION) {
                showNotification(updateMessage, apkUrl);
            } else if (mTypeOfNotice == NOTICE_DIALOG) {
                showDialog(updateMessage, apkUrl);
            }
        } else {
            //已经是最新版本
        }
    }

    private void showNotification(String message, String url) {

    }

    private void showDialog(String message, String url) {

    }

}
