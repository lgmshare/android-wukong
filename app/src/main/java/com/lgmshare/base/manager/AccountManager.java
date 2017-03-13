package com.lgmshare.base.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.lgmshare.base.AppConfig;
import com.lgmshare.base.model.User;
import com.lgmshare.base.util.JsonParseUtil;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.utils.PreferenceUtil;
import com.lgmshare.component.utils.SecurityUtil;

/**
 * 账户管理类
 * Created by Administrator.
 * Datetime: 2015/6/26.
 * Email: lgmshare@mgail.com
 */
public class AccountManager {

    private static final String KEY_USER = "user";
    private static final String KEY_USER_USERNAME = "user_username";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_SAVE_PASSWORD = "save_password";

    /* 账户信息改变广播action */
    public static final String ACITON_ACCOUNT_INFO_CHANGE_RECEVIER = "com.lgmshare.base.account.change";
    //单例
    private static AccountManager mInstance;
    private Context mContext;
    private User mUser;
    private boolean mSavePassword;//是否记住密码

    private boolean mLogged;

    /**
     * 初始化
     */
    public static AccountManager init(Context context) {
        if (mInstance == null) {
            mInstance = new AccountManager(context);
        }
        return mInstance;
    }

    private AccountManager(Context context) {
        this.mContext = context;
        this.mUser = getLocalUser();
        this.mSavePassword = PreferenceUtil.getInstance().getBoolean(KEY_SAVE_PASSWORD);
    }

    /**
     * 添加user
     *
     * @param user 用户对象
     */
    public void setUser(User user) {
        setUser(user, true);
    }

    /**
     * 添加user
     *
     * @param user   用户对象
     * @param isSave 是否缓存到本地
     */
    public void setUser(User user, boolean isSave) {
        if (isSave) {
            saveLocalUser(user);
        }
        if (isSavePassword()) {
            saveLastLoginPassword(user.getToken());
        }
        mUser = user;
        mLogged = true;
        saveLastLoginUsername(user.getUsername());
        sendChangeBroadcastReceiver();
    }

    public User getUser() {
        if (mUser == null) {
            mUser = getLocalUser();
        }
        return mUser;
    }

    /**
     * 获取本地user数据
     *
     * @return
     */
    private User getLocalUser() {
        return JsonParseUtil.parseObject(PreferenceUtil.getInstance().getString(KEY_USER), User.class);
    }

    /**
     * 保存user到本地
     *
     * @param user
     */
    private void saveLocalUser(User user) {
        PreferenceUtil.getInstance().putString(KEY_USER, JsonParseUtil.toJSONString(user));
    }

    /**
     * 获取上一次登录账户名
     *
     * @return
     */
    public String getLastLoginUsername() {
        return PreferenceUtil.getInstance().getString(KEY_USER_USERNAME);
    }

    /**
     * 保存登录账户名
     *
     * @param username
     */
    public void saveLastLoginUsername(String username) {
        PreferenceUtil.getInstance().putString(KEY_USER_USERNAME, username);
    }

    /**
     * 获取账户密码
     *
     * @return
     */
    public String getLastLoginPassword() {
        String passwrod = null;
        String data = PreferenceUtil.getInstance().getString(KEY_USER_PASSWORD);
        if (!TextUtils.isEmpty(data)) {
            passwrod = SecurityUtil.decryptTeaToString(AppConfig.MD5_KEY.getBytes(), data.getBytes());
        }
        return passwrod;
    }

    /**
     * 保存账户密码
     *
     * @param password
     */
    public void saveLastLoginPassword(String password) {
        String data = SecurityUtil.encryptTea(AppConfig.MD5_KEY.getBytes(), password).toString();
        PreferenceUtil.getInstance().putString(KEY_USER_PASSWORD, data);
    }


    /**
     * 是否记住密码
     *
     * @param savePwd
     */
    public void savePassword(boolean savePwd) {
        mSavePassword = savePwd;
        PreferenceUtil.getInstance().putBoolean(KEY_SAVE_PASSWORD, savePwd);
    }

    /**
     * 用户登出
     */
    public void logout() {
        mLogged = false;
        mUser = null;
    }

    public boolean isSavePassword() {
        return mSavePassword;
    }

    public boolean isLogin() {
        return mLogged && mUser != null;
    }

    /**
     * 添加广播
     */
    public void registerAccountInfoChangeReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter(ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
        mContext.registerReceiver(receiver, intentFilter);
    }

    /**
     * 注销广播
     */
    public void unregisterAccountInfoChangeReceiver(BroadcastReceiver receiver) {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    /**
     * 发送广播
     */
    private void sendChangeBroadcastReceiver() {
        Logger.d("AccountManager", ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
        Intent intent = new Intent();
        intent.setAction(ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
        mContext.sendBroadcast(intent);
    }
}
