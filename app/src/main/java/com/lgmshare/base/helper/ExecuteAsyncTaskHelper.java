package com.lgmshare.base.helper;

import android.app.Dialog;
import android.content.Context;

import com.lgmshare.base.helper.concurrency.Command;
import com.lgmshare.base.helper.concurrency.CommandCallback;
import com.lgmshare.base.ui.dialog.LoadingDialog;

/**
 * 异步线程执行帮助类
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/7 12:29
 * @email: lgmshare@gmail.com
 */
public class ExecuteAsyncTaskHelper {

    public static <T> void execute(Context context, Command<T> cmd) {
        execute(context, null, cmd);
    }

    public static <T> void execute(Context context, int message, Command<T> cmd) {
        execute(context, context.getString(message), cmd, null);
    }

    public static <T> void execute(Context context, CharSequence message, Command<T> cmd) {
        execute(context, message, cmd, null);
    }

    public static <T> void execute(Context context, int message, Command<T> cmd, final CommandCallback<T> callback) {
        execute(context, context.getString(message), cmd, callback);
    }

    public static <T> void execute(Context context, CharSequence message, Command<T> cmd, final CommandCallback<T> callback) {
        final Dialog progressDialog = LoadingDialog.create(context, message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setDimAmount(0f);
        progressDialog.show();
        cmd.start(new CommandCallback<T>() {
            @Override
            public void onResult(T result) {
                progressDialog.dismiss();
                if (callback != null) {
                    callback.onResult(result);
                }
            }

            @Override
            public void onError(Exception e) {
                progressDialog.dismiss();
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }
}
