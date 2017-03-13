package com.lgmshare.base.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 *
 *
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/8
 */
public class PermissionsUtil {

    /*public static boolean checkPermission(Context context, String permission, int REQUEST_CODE_ASK_CALL_PHONE){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }else{
                //上面已经写好的拨号方法
                callDirectly(mobile);
            }
        }
        return context.checkPermission(permission, 0, 0);
    }*/
}
