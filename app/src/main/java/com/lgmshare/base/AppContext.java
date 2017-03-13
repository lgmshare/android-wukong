package com.lgmshare.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.lgmshare.component.FrameApplication;
import com.lgmshare.component.FrameContext;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.utils.CommonUtil;
import com.lgmshare.component.utils.PreferenceUtil;
import com.lgmshare.component.utils.StringUtil;

import java.util.UUID;

/**
 * Created by Administrator on 2015/12/24.
 */
public class AppContext extends FrameContext {

    /**
     * 功能初始化
     *
     * @param application
     */
    public AppContext(FrameApplication application) {
        super(application);
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static String getVersionName() {
        String packageName = AppContext.getContext().getPackageName();
        String versionName = null;
        try {
            PackageInfo pkg = AppContext.getContext().getPackageManager().getPackageInfo(packageName, 0);
            versionName = pkg.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionName = "";
        }
        return versionName;
    }

    /**
     * 获取版本VersionCode
     *
     * @return
     */
    public static int getVersionCode() {
        String packageName = AppContext.getContext().getPackageName();
        int versionCode = 0;
        try {
            PackageInfo pkg = AppContext.getContext().getPackageManager().getPackageInfo(packageName, 0);
            versionCode = pkg.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取App安装包信息
     *
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            String packageName = AppContext.getContext().getPackageName();
            info = AppContext.getContext().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("AppContext", "PackageInfo is Null");
            e.printStackTrace(System.err);
        }
        if (info == null) {
            info = new PackageInfo();
        }
        return info;
    }

    /**
     * 获取App唯一标识(设备ID，获取不到，则随机生成一个标识)
     *
     * @return DeviceId
     */
    public static String getAppUniqueID() {
        String uniqueID = PreferenceUtil.getInstance().getString(AppConstants.PREFERENCE_KEY_APP_UNIQUEID);
        if (StringUtil.isEmpty(uniqueID)) {
            // 获取设备ID
            TelephonyManager tm = (TelephonyManager) AppContext.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            uniqueID = tm.getDeviceId();
            // 获取不到，则随机生成一个标识
            if (StringUtil.isEmpty(uniqueID)) {
                uniqueID = UUID.randomUUID().toString();
            }
            PreferenceUtil.getInstance().putString(AppConstants.PREFERENCE_KEY_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取设备名称
     *
     * @return Build.MANUFACTURER + Build.MODEL
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return CommonUtil.getFirstCapitalize(model);
        } else {
            return CommonUtil.getFirstCapitalize(manufacturer) + " " + model;
        }
    }
}
