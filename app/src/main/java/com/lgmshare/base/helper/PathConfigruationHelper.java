package com.lgmshare.base.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.lgmshare.base.AppConfig;
import com.lgmshare.component.utils.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/15 11:42
 */
public class PathConfigruationHelper {

    public enum Module {
        privilege, update, temp, media
    }

    /*通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    如果使用上面的方法，当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。
    而且上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项*/

    /**
     * SDCard/Android/data/你的应用的包名/files/ 目录(长时间保存的数据)
     *
     * @param prefix  文件名前缀
     * @param postfix 文件名后缀
     * @return prefix + "_" + DateUtil.getRandom(4) + "." + postfix
     * @see .util.RandomUtil
     */
    public static String getFilePathOfExternal(Context context, String prefix, String postfix) {
        File externalFile = context.getExternalFilesDir(null);
        if (externalFile == null) {
            return null;
        }
        String externalPath = externalFile.getAbsolutePath();

        File dest = new File(externalPath + "/tmp/");
        dest.mkdirs();

        File outputFile = new File(dest, prefix + "_" + DateUtil.getRandom(4) + "." + postfix);
        return outputFile.getAbsolutePath();
    }

    /**
     * SDCard/Android/data/你的应用的包名/cache/ 目录(短时间时间保存的数据)
     *
     * @param prefix  文件名前缀
     * @param postfix 文件名后缀
     * @return prefix + "_" + DateUtil.getRandom(4) + "." + postfix
     * @see .util.RandomUtil
     */
    public static String getFilePathToCache(Context context, String prefix, String postfix) {
        String floderPath = null;
        if (isSDCardMounted() || !isExternalStorageRemovable()) {
            if (getExternalCacheDir(context) == null) {
                floderPath = context.getCacheDir().getPath();
            } else {
                floderPath = getExternalCacheDir(context).getPath();//应用缓存目录
            }
        } else {
            floderPath = context.getCacheDir().getPath();
        }

        File file = new File(floderPath, prefix + "_" + DateUtil.getRandom(4) + "." + postfix);
        if (file.exists()) {
            file.delete();
        }
        return file.getAbsolutePath();
    }

    /**
     * 内部存储路径
     *
     * @param prefix  文件名前缀
     * @param postfix 文件名后缀
     * @return prefix + "_" + DateUtil.getRandom(4) + "." + postfix
     * @see .util.RandomUtil
     */
    public static String getFilePathToInternal(Context context, String prefix, String postfix) {
        String externalPath;
        File externalFile = context.getFilesDir();
        if (externalFile == null) {
            externalPath = "data/data/".concat(context.getPackageName()).concat("/files");
        } else {
            externalPath = externalFile.getAbsolutePath();
        }

        File dest = new File(externalPath + "/tmp/");
        dest.mkdirs();
        if (!dest.exists()) return null;

        File outputFile = new File(dest, prefix + "_" + DateUtil.getRandom(4) + "." + postfix);
        return outputFile.getAbsolutePath();
    }

    public static String getFilePathToSD(Context context, Module module, String fileName) {
        if (isSDCardMounted()) {
            return getSDCardStorePath(module, fileName);
        } else {
            return getSystemStorePath(context, module, fileName);
        }
    }

    public static boolean isSDCardMounted() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        } else {
            return true;
        }
    }

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static File getFile(Context context, Module module, String fileName) {
        String filePath = "";
        File file = null;
        if (isSDCardMounted()) {
            filePath = getSDCardStorePath(module, fileName);
        } else {
            filePath = getSystemStorePath(context, module, fileName);
        }
        file = new File(filePath);
        return file;
    }

    private static String getSystemStorePath(Context context, Module module, String fileName) {
        String dirPath = context.getFilesDir().getAbsolutePath() + "/" + AppConfig.FILE_DIR + "/" + getFloderName(module) + "/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath + fileName;
    }

    private static String getSDCardStorePath(Module module, String fileName) {
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/" + AppConfig.FILE_DIR + "/" + getFloderName(module) + "/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath + fileName;
    }

    private static String getFloderName(Module module) {
        String floderName = module.toString();
        return floderName;
    }

    public static int copy(String fromFile, String toFile) {
        // 要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        // 如同判断SD卡是否存在或者文件是否存在
        // 如果不存在则 return出去
        if (!root.exists()) {
            return -1;
        }
        // 如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();
        // 目标目录
        File targetDir = new File(toFile);
        // 创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        // 遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            // 如果当前项为子目录 进行递归
            if (currentFiles[i].isDirectory()) {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");
            } else {// 如果当前项为文件则进行文件拷贝
                copySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 0;
    }

    // 文件拷贝
    // 要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int copySdcardFile(String fromFile, String toFile) {
        try {
            InputStream in = new FileInputStream(fromFile);
            // 输出的文件流
            OutputStream os = new FileOutputStream(toFile);
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 开始读取
            while ((len = in.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            in.close();
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }
}
