package com.lgmshare.component.logger;

/**
 * 日志模块配置类
 */
public final class LoggerConfiguration {

    private boolean showThreadInfo = true;//是否显示所在线程信息

    private int level = 2;//日志打印层级
    private int methodCount = 2;//显示方法数
    private int methodOffset = 0;//偏移

    private boolean isFormat = true;//是否格式化
    private boolean isPrint = true;//是否打印
    private boolean isWriteLogFile = true;//是否保存日志信息

    private String logFilePath;//日志文件保存路径
    private String logFileName;//日志文件名

    public LoggerConfiguration hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    public LoggerConfiguration setLevel(int level) {
        this.level = level;
        return this;
    }

    public LoggerConfiguration setMethodCount(int methodCount) {
        if (methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public LoggerConfiguration setMethodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public LoggerConfiguration setFormat(boolean format) {
        this.isFormat = format;
        return this;
    }

    public LoggerConfiguration setPrint(boolean print) {
        this.isPrint = print;
        return this;
    }

    public LoggerConfiguration setWriteLogFile(boolean write) {
        this.isWriteLogFile = write;
        return this;
    }

    public LoggerConfiguration setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
        return this;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public int getLevel() {
        return level;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public boolean isFormat() {
        return isFormat;
    }

    public boolean isPrint() {
        return isPrint;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public boolean isWriteLogFile() {
        return isWriteLogFile;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public String getLogFileName() {
        return logFileName;
    }
}
