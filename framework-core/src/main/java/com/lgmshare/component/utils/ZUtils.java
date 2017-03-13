package com.lgmshare.component.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各种杂七杂八的工具方法
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/4 10:12
 */
public class ZUtils {

    private static final String TAG = "Utils";

    /**
     * 安装apk文件
     *
     * @param apkPath
     * @param context
     */
    public static void installApk(Context context, String apkPath) {
        File file = new File(apkPath);
        if (!file.exists()) {
            Toast.makeText(context, "未找不到安装文件", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(file);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载apk文件
     *
     * @param packageName
     * @param context
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("package:" + packageName);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 验证手机号码格式
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobile)) {
            return false;
        } else {
            return mobile.matches(telRegex);
        }
    }

    /**
     * 验证Email地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 将传入的金额格式化为元,保留两位小数
     *
     * @param amount
     * @return 例如：传入5 返回5.00
     */
    public static String formatMoney(double amount) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("##,###0.00");
        return df.format(amount);
    }

    /**
     * 将传入的金额（单位分）格式化为元，保留两位小数 格式如：5.00
     *
     * @param amount
     * @return
     */
    public static String formatMoney(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "0.00";
        }
        String result;
        try {
            double num = Double.parseDouble(amount);
            DecimalFormat formater = new DecimalFormat("###,##0.00");
            result = formater.format(num / 100.0d);
        } catch (NumberFormatException e) {
            result = amount;
        }
        return result;
    }

    /**
     * 将传入的金额（单位元）格式化为元，保留两位小数 格式如：5.00
     *
     * @param amount
     * @return
     */
    public static String formatMoneyYuan(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "0.00";
        }
        String result;
        try {
            double num = Double.parseDouble(amount);
            DecimalFormat formater = new DecimalFormat("###,##0.00");
            result = formater.format(num);
        } catch (NumberFormatException e) {
            result = amount;
        }
        return result;
    }

    /**
     * 手机号码中间4位加星号
     *
     * @param mobile 手机号码
     * @return 136****8888
     */
    public static String formatMobileStar(String mobile) {
        if (TextUtils.isEmpty(mobile))
            return "";
        if (mobile.length() < 11)
            return mobile;
        return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
    }

    /**
     * 手机号码分组格式化
     *
     * @param mobile 手机号码
     * @return 136 8888 8888
     */
    public static String formatMobileGroup(String mobile) {
        if (mobile.length() != 11) {
            return mobile;
        }
        //匹配电话号码格式的正则表达式...
        Pattern regex = Pattern.compile("^\\(?([0-9]{3})\\)?[-. ]?([0-9]{4})[-.?]?([0-9]{4}){1}");
        //需要匹配的目标串...
        String formattedNumber = "";
        Matcher regexMatcher = regex.matcher(mobile);
        if (regexMatcher.find()) {
            //使用捕获分组的信息来进行格式化...
            formattedNumber = regexMatcher.replaceAll("($1) $2-$3");
        }
        return formattedNumber;
    }

    /**
     * 调用系统拨号-跳到拨号盘-拨打电话
     *
     * @param context
     * @param phone
     */
    public static void callSystemActionDial(Context context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    /**
     * 调用系统拨号-直接拨号
     * 需要增加CALL_PHONE权限
     *
     * @param context
     * @param phone
     */
    public static void callSystemActionCall(Context context, String phone) {
        if (PermissionCheckUtils.checkPermission(context, Manifest.permission.CALL_PHONE)) {
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            context.startActivity(intent);
        } else {
            //没有ACTION_CALL权限,跳到拨号盘
            callSystemActionDial(context, phone);
        }
    }

    /**
     * 调用系统发送短信(发送到指定号码)
     *
     * @param context
     * @param phone   手机号码
     */
    public static void callSystemSmsPhone(Context context, String phone) {
        callSystemSmsAction(context, phone, null);
    }

    /**
     * 调用系统发送短信（发送指定信息）
     *
     * @param context
     * @param content 短信内容
     */
    public static void callSystemSmsMsg(Context context, String content) {
        callSystemSmsAction(context, null, content);
    }

    /**
     * 调用系统发送短信
     *
     * @param context
     * @param phone   手机号码
     * @param content 短信内容
     */
    public static void callSystemSmsAction(Context context, String phone, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phone) ? "" : phone));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 发送邮件
     *
     * @param activity
     * @param andress
     * @param subject
     * @param content
     */
    public static void sendMail(Activity activity, String[] andress, String subject, String content) {
        Intent i = new Intent(Intent.ACTION_SEND);
        //i.setType("text/plain"); //模拟器
        i.setType("message/rfc822"); //真机
        i.putExtra(Intent.EXTRA_EMAIL, andress);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, content);
        activity.startActivity(Intent.createChooser(i, "Sending mail..."));
    }

    /**
     * 打开一个应用
     *
     * @param context
     * @param packageName
     */
    public static void openApplication(final Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 半角转全角
     *
     * @param input
     * @return
     */
    public static String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 全角转换为半角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

}
