package com.lgmshare.component.logger;

import com.lgmshare.component.utils.FileSizeFormatUtils;

/**
 * Logger is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 */
public final class Logger {

    private static final String DEFAULT_TAG = "PRETTYLOGGER";

    private static Printer printer = new LoggerPrinter();

    private Logger() {
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static LoggerConfiguration init() {
        return init(DEFAULT_TAG);
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger as TAG
     */
    public static LoggerConfiguration init(String tag) {
        printer = new LoggerPrinter();
        return printer.init(tag);
    }

    public static void clear() {
        printer.clear();
        printer = null;
    }

    public static Printer t(String tag) {
        return printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
    }

    public static Printer t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void d(String message) {
        printer.d(message);
    }

    public static void e(String message) {
        printer.e(message);
    }

    public static void e(Throwable throwable, String message) {
        printer.e(message, throwable);
    }

    public static void i(String message) {
        printer.i(message);
    }

    public static void v(String message) {
        printer.v(message);
    }

    public static void w(String message) {
        printer.w(message);
    }

    public static void wtf(String message) {
        printer.wtf(message);
    }

    public static void d(String tag, String message) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.d(message);
    }

    public static void e(String tag, String message) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.e(message);
    }

    public static void e(String tag, String message, Throwable throwable) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.e(message, throwable);
    }

    public static void i(String tag, String message) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.i(message);
    }

    public static void v(String tag, String message) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.v(message);
    }

    public static void w(String tag, String message) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.w(message);
    }

    public static void wtf(String tag, String message) {
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.wtf(message);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        printer.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }

    /**
     * 打印当前的内存占用状况，以便改进内存消耗
     */
    public static void debugMemory(String tag) {
        StringBuilder sb = new StringBuilder();
        sb.append("   maxMemory:" + FileSizeFormatUtils.getAutoSize(Runtime.getRuntime().maxMemory()));//应用程序最大可用内存
        sb.append(" totalMemory:" + FileSizeFormatUtils.getAutoSize(Runtime.getRuntime().totalMemory()));//应用程序已获得内存
        sb.append("  freeMemory:" + FileSizeFormatUtils.getAutoSize(Runtime.getRuntime().freeMemory()));//应用程序已获得内存中未使用内存
        printer.t(tag, printer.getLoggerConfiguration().getMethodCount());
        printer.d(sb.toString());
    }
}
