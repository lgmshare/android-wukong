package com.lgmshare.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lgmshare.base.helper.PathConfigruationHelper;
import com.lgmshare.base.ui.activity.AboutActivity;
import com.lgmshare.base.ui.activity.GuideActivity;
import com.lgmshare.base.ui.activity.MainActivity;
import com.lgmshare.base.ui.activity.SettingActivity;
import com.lgmshare.base.ui.activity.common.WebViewActivity;
import com.lgmshare.base.ui.activity.image.ImageSelectActivity;
import com.lgmshare.base.ui.activity.user.UserLoginActivity;
import com.lgmshare.base.ui.activity.user.UserRegisterActivity;

/**
 * 应用跳转控制
 * <p/>
 * <p/>
 * Created by ex3ndr on 07.10.14.
 */
public class AppController {

    public static void startGuideActivity(Activity activity) {
        Intent intent = new Intent(activity, GuideActivity.class);
        activity.startActivity(intent);
    }

    public static void startUserLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, UserLoginActivity.class);
        activity.startActivity(intent);
    }

    public static void startUserRegisterActivity(Activity activity) {
        Intent intent = new Intent(activity, UserRegisterActivity.class);
        activity.startActivity(intent);
    }

    public static void startMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startGuideAndMainActivity(Activity activity) {
        Intent intent1 = new Intent(activity, MainActivity.class);
        Intent intent2 = new Intent(activity, GuideActivity.class);
        Intent[] intents = new Intent[]{intent1, intent2};
        activity.startActivities(intents);
    }

    public static void startImageSelectActivity(Activity activity, boolean crop, int requestCode) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstants.EXTRA_IMAGE_CROP, crop);
        bundle.putString(AppConstants.EXTRA_IMAGE_PATH, PathConfigruationHelper.getFilePathToCache(activity, "temp", "png"));
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startWebViewActivity(Activity activity, int pageTitle, String pageUrl) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(AppConstants.EXTRA_WEB_TITLE, pageTitle);
        intent.putExtra(AppConstants.EXTRA_WEB_URL, pageUrl);
        activity.startActivity(intent);
    }

    public static void startSettingActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    public static void startAboutActivity(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * 返回到上一个页面
     *
     * @param activity
     */
    public static void back(Activity activity) {
        activity.finish();
    }

    /**
     * 返回到上一个页面并返回值
     *
     * @param activity
     * @param retCode
     * @param retData
     */
    public static void backWithResult(Activity activity, int retCode, Bundle retData) {
        Intent intent = new Intent();
        if (null != retData) {
            intent.putExtras(retData);
        }
        activity.setResult(retCode, intent);
        activity.finish();
    }

    /**
     * 返回应用主页
     */
    public static void backHomePage() {
        AppContext.getActivityStacker().finishTopActivity(MainActivity.class);
    }
}
