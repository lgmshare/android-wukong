package com.lgmshare.component.app;

import android.text.TextUtils;

/**
 * @author: lim.
 * @email: lgmshare@gmail.com
 * @Version V1.0
 * @Description
 * @datetime : 2015/5/11 11:32
 */
public final class FrameConfiguration {

    /*调试模式*/
    private final boolean enableDebugModel;
    /*是否保存调试日志*/
    private final boolean enableDebugLogSave;
    /*是否打开严格模式*/
    private final boolean enableStrictMode;
    /*日志Tag*/
    private final String debugLogTag;
    /*日志保存路径*/
    private final String debugLogFilePath;
    /*应用crash文件保存路径*/
    private final String crashFilePath;
    /*应用下载文件保存路径*/
    private final String downloadFilePath;

    private FrameConfiguration(Builder builder) {
        enableDebugModel = builder.enableDebugModel;
        enableDebugLogSave = builder.enableDebugLogSave;
        enableStrictMode = builder.enableStrictMode;
        debugLogTag = builder.debugLogTag;
        debugLogFilePath = builder.debugLogFilePath;
        downloadFilePath = builder.downloadFilePath;
        crashFilePath = builder.crashFilePath;
    }

    public boolean isEnableDebugModel() {
        return enableDebugModel;
    }

    public boolean isEnableDebugLogSave() {
        return enableDebugLogSave;
    }

    public boolean isEnableStrictMode() {
        return enableStrictMode;
    }

    public String getDebugLogTag() {
        return debugLogTag;
    }

    public String getDebugLogFilePath() {
        return debugLogFilePath;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public String getCrashFilePath() {
        return crashFilePath;
    }

    public static class Builder {

        private boolean enableDebugModel;
        private boolean enableDebugLogSave;
        private boolean enableStrictMode;

        private String debugLogTag;
        private String debugLogFilePath;
        private String downloadFilePath;
        private String crashFilePath;

        public Builder() {
            this.enableDebugModel = false;
            this.enableDebugLogSave = false;
            this.enableStrictMode = false;

            this.debugLogTag = null;
            this.debugLogFilePath = null;
            this.downloadFilePath = null;
            this.crashFilePath = null;
        }

        public Builder enableDebugModel(boolean enableDebugModel) {
            this.enableDebugModel = enableDebugModel;
            return this;
        }

        public Builder enableDebugLogSave(boolean enableSaveLog) {
            this.enableDebugLogSave = enableSaveLog;
            return this;
        }

        public Builder enableStrictMode(boolean enableStrictMode) {
            this.enableStrictMode = enableStrictMode;
            return this;
        }

        public Builder debugLogTag(String debugLogTag) {
            this.debugLogTag = debugLogTag;
            return this;
        }

        public Builder debugLogFilePath(String appLogFilePath) {
            this.debugLogFilePath = appLogFilePath;
            return this;
        }

        public Builder downloadFilePath(String appDownloadFilePath) {
            this.downloadFilePath = appDownloadFilePath;
            return this;
        }

        public Builder crashFilePath(String appCrashFilePath) {
            this.crashFilePath = appCrashFilePath;
            return this;
        }

        public FrameConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new FrameConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if (TextUtils.isEmpty(debugLogTag)) {
                debugLogTag = FrameConfig.TAG;
            }
        }
    }
}
