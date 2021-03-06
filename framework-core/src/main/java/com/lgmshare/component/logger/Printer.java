package com.lgmshare.component.logger;

public interface Printer {

    LoggerConfiguration init(String tag);

    LoggerConfiguration getLoggerConfiguration();

    Printer t(String tag, int methodCount);

    void d(String message, Object... args);

    void e(String message, Object... args);

    void e(String message, Throwable throwable, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void v(String message, Object... args);

    void wtf(String message, Object... args);

    void json(String json);

    void xml(String xml);

    void clear();
}
