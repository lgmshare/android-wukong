package com.lgmshare.base;

import android.os.Environment;

import com.lgmshare.base.helper.DataCleanHelper;
import com.lgmshare.base.manager.AccountManager;
import com.lgmshare.component.FrameApplication;
import com.lgmshare.component.FrameConfiguration;
import com.lgmshare.component.cache.CacheManager;
import com.lgmshare.component.image.ImageLoader;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.network.download.DownloadManager;
import com.lgmshare.component.network.download.DownloadManagerImpl;
import com.lgmshare.component.update.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/24.
 */
public class AppApplication extends FrameApplication {

    private static AppApplication mInstance;

    private AccountManager mAccountManager;

    public static AppApplication getInstance() {
        return mInstance;
    }
    private static final String APATCH_PATH = "/out.apatch";
    /**
     * patch manager
     */
    private PatchManager mPatchManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //账户管理器
        mAccountManager = AccountManager.init(this);

        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.0");
        Logger.d(TAG, "inited.");

        // load patch
        mPatchManager.loadPatch();
        Logger.d(TAG, "apatch loaded.");

        // add patch at runtime
        try {
            // .apatch file path
            String patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + APATCH_PATH;
            mPatchManager.addPatch(patchFileString);
            Logger.d(TAG, "apatch:" + patchFileString + " added.");
        } catch (IOException e) {
            Logger.e(TAG, "", e);
        }

        //提醒定时service
        //startService(new Intent(this, AlarmService.class));
    }

    @Override
    public FrameConfiguration onBuildFrameConfiguration() {
        FrameConfiguration config = new FrameConfiguration.Builder(this)
                .enableDebugModel(AppConfig.DEBUG_MODE)
                .enableStrictMode(false)
                .enableSaveLog(AppConfig.DEBUG_LOGS)
                .dbVersion(AppConfig.DB_VERSION)
                .dbAccount("lgmshare")
                .dbPassword("123456")
                .debugLogTag(AppConfig.APP_LOG_TAG)
                .debugLogFilePath(AppConfig.FILE_LOG_PATH)
                .crashFilePath(AppConfig.FILE_CRASH_PATH)
                .imageCachePath(AppConfig.FILE_IMAGE_PATH)
                .downloadFilePath(AppConfig.FILE_DOENLOAD_PATH)
                .build();
        return config;
    }

    public AccountManager getAccountManager() {
        return mAccountManager;
    }

    /**
     * 清楚应用缓存
     */
    public void cleanCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();

        CacheManager cacheManager = new CacheManager(this, TAG);
        cacheManager.clearCache();

        DownloadManager downloadManager = new DownloadManagerImpl(this);
        downloadManager.removeAll(true);

        AppContext.getRequestQueue().getCache().clear();

        DataCleanHelper.cleanApplicationData(this);
    }

    /**
     * 应用退出
     */
    public void exit() {
        AppContext.getActivityStacker().finishAllActivity();
        System.exit(0);
        Runtime.getRuntime().exit(0);
    }

}
