package com.lgmshare.base;


/**
 * @author: lim
 * @description: 应用基本配置参数
 * @email: lgmshare@gmail.com
 * @datetime 2014-6-3  上午11:12:20
 */
public class AppConfig {

    /* 运行环境0:DEV  1:TEST 2:REL */
    public static final int RUNTIME_MODE = 0;
    /* 运行环境是否可选择 */
    public static boolean DEBUG_RUNTIME = false;
    /* 应用调试模式 */
    public static boolean DEBUG_MODE = true;
    /* 保存错误日志 */
    public static boolean DEBUG_LOGS = true;
    /* 发送carsh报告 */
    public static boolean SEND_CRASH = false;
    /* 应用统计模式 */
    public static boolean DEBUG_ANALYTICS = true;
    /* Http连接url */
    public static String HTTP_BASE_URL = "http://115.29.77.223:8668/shidai/app/api/";
    ////////////////////////////////分割线////////////////////////////////////
    /* 应用名称*/
    public static final String APP_NAME = "base";
    /* tag*/
    public static final String APP_LOG_TAG = "base-log";
    /* 应用BUILD版本号*/
    public static final String APP_VERSION_BUILD = "1.0";
    /* 应用数据库版本号*/
    public static final int DB_VERSION = 1;
    public static final String HTTP_BASE_URL_DEV = "http://115.29.77.223:8668/shidai/app/api/";
    public static final String HTTP_BASE_URL_TEST = "http://115.29.77.223:8668/shidai/app/api/";
    public static final String HTTP_BASE_URL_REL = "http://115.29.77.223:8668/shidai/app/api/";
    /* 每页加载数据大小 */
    public static final int PAGE_SIZE = 10;
    /* splash加载延迟等待时间 */
    public static final int SPLASH_TIME_DELAY = 1 * 1000;
    /* 客户端的DES 加密的key */
    public static final String MD5_KEY = "0123456789";
    /* SD卡文件存储根目录 */
    public static final String FILE_DIR = "/wukong/";
    /* 升级包保存路径 */
    public static final String FILE_DOENLOAD_PATH = FILE_DIR + "/download/";
    /* 日志保存路径*/
    public static final String FILE_LOG_PATH = FILE_DIR + "/log/";
    public static final String FILE_CACHE_PATH = FILE_DIR + "/cache/";
    public static final String FILE_IMAGE_PATH = FILE_DIR + "/image/";
    public static final String FILE_CRASH_PATH = FILE_DIR + "/crash/";
}
