package com.lgmshare.component.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

/**
 * 运行时上下文环境工具类
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/7/7 10:02
 */
public class ContextUtils {

    private static final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    /**
     * 获取App安装包信息
     *
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ContextUtils", "PackageInfo is Null");
            e.printStackTrace(System.err);
        }
        if (info == null) {
            info = new PackageInfo();
        }
        return info;
    }

    /**
     * 获取应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String packageName = context.getPackageName();
        String versionName = null;
        try {
            PackageInfo pkg = context.getPackageManager().getPackageInfo(packageName, 0);
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
    public static int getVersionCode(Context context) {
        String packageName = context.getPackageName();
        int versionCode = 0;
        try {
            PackageInfo pkg = context.getPackageManager().getPackageInfo(packageName, 0);
            versionCode = pkg.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 获取App唯一标识(设备ID，获取不到，则随机生成一个标识)
     *
     * @return DeviceId
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceID(Context context) {
        String uniqueID = null;
        try {
            //需要读取设备状态权限
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            uniqueID = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取不到，则随机生成一个标识
        if (TextUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
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
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    /**
     * 获取应用MD5签名
     *
     * @param context
     * @return
     */
    public static String getSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            Signature signature = signatures[0];
            return hexdigest(signature.toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * HEX编码
     *
     * @param signature
     * @return
     */
    public static String hexdigest(byte[] signature) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(signature);
            byte[] msgArray = messageDigest.digest();
            char[] md5Array = new char[32];
            int i = 0;
            int j = 0;
            while (true) {
                if (i >= 16)
                    return new String(md5Array);
                int k = msgArray[i];
                int m = j + 1;
                md5Array[j] = hexDigits[(0xF & k >>> 4)];
                j = m + 1;
                md5Array[m] = hexDigits[(k & 0xF)];
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param context
     * @param className 判断的服务名字
     * @return true 在运行 false 未运行
     */
    private boolean isServiceRunning(final Context context, String className) {
        boolean result = false;
        if (context == null || TextUtils.isEmpty(className)) {
            return result;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningServiceInfo> processInfos = am.getRunningServices(50);
            if (processInfos != null && !processInfos.isEmpty()) {
                for (ActivityManager.RunningServiceInfo processInfo : processInfos) {
                    if (processInfo.service.equals(className)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 判断一个程序是否显示在前端,根据测试此方法执行效率在11毫秒,无需担心此方法的执行效率
     * <p/>
     * 可以根据importance的不同来判断前台或后台
     * RunningAppProcessInfo 里面的常量IMOPORTANCE就是所说的前台后台，其实IMOPORTANCE是表示这个app进程的重要性，
     * 因为系统回收时候，会根据IMOPORTANCE来回收进程的。具体可以去看文档。。
     * public static final int IMPORTANCE_FOREGROUND = 100//在屏幕最前端、可获取到焦点 可理解为Activity生命周期的OnResume();
     * public static final int IMPORTANCE_VISIBLE = 200//在屏幕前端、获取不到焦点可理解为Activity生命周期的OnStart();
     * public static final int IMPORTANCE_SERVICE = 300//在服务中
     * public static final int IMPORTANCE_BACKGROUND = 400//后台
     * public static final int IMPORTANCE_EMPTY = 500//空进程
     * <p/>
     *
     * @param context 上下文环境
     * @return true--->在前端,false--->不在前端
     */
    public static boolean isApplicationShowing(final Context context) {
        boolean result = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            if (processInfos != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                    if (processInfo.processName.equals(context.getPackageName())) {
                        int status = processInfo.importance;
                        if (status == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
                                || status == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            Log.d("ContextUtils", "app is showing");
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 判断一个程序是否在运行
     *
     * @param context context 上下文环境
     * @return true--->在运行,false--->未运行
     */
    public static boolean isApplicationRunning(final Context context, String packageName) {
        boolean result = false;
        if (context == null || TextUtils.isEmpty(packageName)) {
            return result;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            if (processInfos != null && !processInfos.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                    if (processInfo.processName.equals(packageName)) {
                        int status = processInfo.importance;
                        Log.d("ContextUtils", "app packageName:" + packageName + " processInfo status:" + status);
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 用来判断应用是否已安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isApplicationAvailable(final Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 获取mainfest文件中的标签值
     *
     * @param context
     * @param name
     * @return
     */
    public static String getMainfestMetaDataValue(Context context, String name) {
        ApplicationInfo appInfo = null;
        String value = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            value = "";
        }
        return value;
    }

}
