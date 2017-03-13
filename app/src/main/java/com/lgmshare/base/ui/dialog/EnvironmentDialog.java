package com.lgmshare.base.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.lgmshare.base.AppConfig;
import com.lgmshare.base.AppContext;
import com.lgmshare.base.AppController;
import com.lgmshare.base.R;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/25
 */
public class EnvironmentDialog extends DialogFragment {

    public static final String TAG = "environmentDialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] runtimeMode = AppContext.getContext().getResources().getStringArray(R.array.runtime_mode);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher)
                .setTitle("运行环境")
                .setSingleChoiceItems(runtimeMode, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        /* User2 clicked on a radio button do some stuff */
                    }
                })
                .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        /* User2 clicked Yes so do some stuff */
                        dismiss();
                        setRuntimeMode(whichButton);
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return true;
                    }
                })
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * ------------------------------------------------------
     * <p>运行环境</p>
     * ------------------------------------------------------
     */
    private void setRuntimeMode(int mode) {
        switch (mode) {
            case 1:
                //开发
                AppConfig.DEBUG_MODE = true;
                AppConfig.DEBUG_RUNTIME = true;
                AppConfig.DEBUG_ANALYTICS = true;
                AppConfig.HTTP_BASE_URL = AppConfig.HTTP_BASE_URL_DEV;
                break;
            case 2:
                //测试
                AppConfig.DEBUG_MODE = true;
                AppConfig.DEBUG_RUNTIME = true;
                AppConfig.DEBUG_ANALYTICS = true;
                AppConfig.HTTP_BASE_URL = AppConfig.HTTP_BASE_URL_TEST;
                break;
            case 3:
                //正式
                AppConfig.DEBUG_MODE = false;
                AppConfig.DEBUG_RUNTIME = false;
                AppConfig.DEBUG_ANALYTICS = true;
                AppConfig.HTTP_BASE_URL = AppConfig.HTTP_BASE_URL_REL;
                break;
            case 4:
                break;
            default:
                break;
        }
        AppController.startMainActivity(getActivity());
    }
}
