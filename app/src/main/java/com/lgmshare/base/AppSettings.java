package com.lgmshare.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.lgmshare.component.utils.PreferenceUtil;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/10 11:39
 * @email: lgmshare@gmail.com
 */
public class AppSettings {

    private static AppSettings mInstance = null;

    private final Context mContext;
    private SharedPreferences mSharedPreferences;

    private AppSettings(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceUtil.getInstance().getSharedPreferences();
    }

    public static AppSettings getInstance() {
        if (mInstance == null) {
            mInstance = new AppSettings(AppContext.getContext());
        }
        return mInstance;
    }

    /**
     * 应用是否已初始化
     *
     * @return 已初始化:true, 未初始化：false
     */
    public boolean isInitialized() {
        return mSharedPreferences.getBoolean(AppConstants.PREFERENCE_KEY_INITIALIZED, false);
    }

    /**
     * 是否是新安装应用（获取后自动更新为false）
     *
     * @return
     */
    public boolean isNewVersion() {
        int currVersionCode = AppContext.getVersionCode();
        int lastVersionCode = mSharedPreferences.getInt(AppConstants.PREFERENCE_KEY_APP_VERSION_CODE, 0);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(AppConstants.PREFERENCE_KEY_APP_VERSION_CODE, currVersionCode);
        editor.commit();
        return lastVersionCode != currVersionCode;
    }

    /**
     * 应用初始化状态
     *
     * @param init
     */
    public void initialized(boolean init) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(AppConstants.PREFERENCE_KEY_INITIALIZED, init);
        editor.commit();
    }
}
