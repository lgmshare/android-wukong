package com.lgmshare.component.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.lgmshare.component.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/4
 */
final class FrameCrashHandler implements UncaughtExceptionHandler {

    private final String TAG = "CrashHandler";
    // 程序的Context对象
    private Context mContext;
    // 实例
    private static FrameCrashHandler instance = null;
    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // crash保存路径
    private String mFileDir;

    private FrameCrashHandler(Context context) {
        mContext = context.getApplicationContext();
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        mFileDir = FrameContext.getCrashFilePath();
    }

    /**
     * 初始化
     */
    public static void init(Context context) {
        instance = new FrameCrashHandler(context);
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(instance);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();

        //////////////////save log to file/////////////
        if (TextUtils.isEmpty(mFileDir)) {
            return false;
        }
        String message = crashCollectLogInfo(mContext, ex);
        String filePath = crashSaveLogInfo(mContext, mFileDir, message);
        Logger.e(TAG, "crash file :" + filePath);
        return true;
    }

    /**
     * 收集设备、异常信息
     *
     * @param ctx
     */
    private String crashCollectLogInfo(Context ctx, Throwable ex) {
        StringBuffer sb = new StringBuffer(200);
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                sb.append("versionName=" + pi.versionName + "\n");
                sb.append("versionCode=" + pi.versionCode + "\n");
            }

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append(field.getName() + "=" + field.get(null).toString() + "\n");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "an error occured when collect package info", e);
        } catch (IllegalAccessException e) {
            Logger.e(TAG, "an error occured when collect crash info", e);
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String exception = stringWriter.toString();
        sb.append(exception);

        return sb.toString();
    }

    /**
     * 保存异常日志
     *
     * @param context
     * @param fileDir
     * @param log
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String crashSaveLogInfo(Context context, String fileDir, String log) {
        try {
            String logFileDir = getFileDir(context, fileDir);
            String logFilePath = logFileDir + "/" + getFileName();

            //没有挂载SD卡，无法写文件
            if (TextUtils.isEmpty(logFileDir)) {
                Logger.e(TAG, "No SDCard, Can't Write File of error log");
                return null;
            }

            //创建文件夹
            File pathFile = new File(logFileDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }

            //创建文件
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(logFilePath);
            fos.write(log.getBytes());
            fos.close();
            return logFilePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件路径
     *
     * @param context
     * @param fileDir
     * @return
     */
    private String getFileDir(Context context, String fileDir) {
        String logSaveDir = null;
        //判断SDcard存在并且可以进行读写
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logSaveDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileDir;
        } else {
            logSaveDir = context.getFilesDir().getAbsolutePath() + "/" + fileDir;
        }
        return logSaveDir;
    }

    /**
     * 获取文件名
     *
     * @return
     */
    private String getFileName() {
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        String fileName = "crash-" + datetime + ".log";
        return fileName;
    }
}
