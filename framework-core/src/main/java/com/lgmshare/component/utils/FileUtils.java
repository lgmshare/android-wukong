package com.lgmshare.component.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 文件操作工具
 *
 * @author: lim
 * @description: 文件操作工具
 * @email: lgmshare@gmail.com
 * @datetime 2014年6月3日  下午4:03:42
 */
public class FileUtils {

    /**
     * 是否挂载了SD卡
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
    }

    /**
     * 检查文件是否存在
     *
     * @param uri 文件路径(filePath + fileName)
     * @return
     */
    public static boolean isExist(String uri) {
        return isExist(uri, false);
    }

    /**
     * 检查文件是否存在
     *
     * @param uri      文件路径  (根目录filePath + fileName)
     * @param isCreate 是否创建
     * @return
     */
    public static boolean isExist(String uri, boolean isCreate) {
        File file = new File(uri);
        boolean ret = file.exists();
        if (!ret && isCreate) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                ret = false;
            }
        }
        return ret;
    }

    /**
     * 创建文件
     *
     * @param uri 文件路径(根目录filePath + fileName)
     */
    public static File createFile(String uri) {
        File file = new File(uri);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 创建文件（兼容部分手机在SD卡上创建不成功）
     * 创建失败，会在cache目录创建文件
     *
     * @param context
     * @param fileName 文件名
     * @param fileDir  文件目录（appName/image/）
     * @return
     */
    public static File createFile(Context context, String fileName, String fileDir) {
        String filePath = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + fileDir;
        File file = null;
        try {
            //创建目录
            File fileDirTemp = new File(filePath);
            fileDirTemp.mkdirs();
            file = new File(fileDirTemp, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            try {
                //TODO 创建失败，转到cache目录
                String cachePath = context.getCacheDir().getAbsoluteFile() + File.separator + "temp";
                File fileDirTemp = new File(cachePath);
                fileDirTemp.mkdirs();
                file = new File(fileDirTemp, fileDir);
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 在SD卡上创建文件夹
     *
     * @param fileDir
     * @return
     */
    public static File createSDFile(String fileDir) {
        String path = getSDAbsolutePath(fileDir);
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    /**
     * 在SD卡上创建目录和文件
     *
     * @param fileDir
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createSDFile(String fileDir, String fileName) {
        String path = getSDAbsolutePath(fileDir);
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void delete(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void delete(File file) {
        delete(file, false);
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file
     * @param ignoreDir 是否删除当前文件夹
     */
    public static void delete(File file, boolean ignoreDir) {
        if ((file == null) || (!file.exists())) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] fileList = file.listFiles();
        if (fileList == null) {
            return;
        }

        for (File f : fileList) {
            delete(f, ignoreDir);
        }

        if (!ignoreDir) file.delete();
    }

    /**
     * 读数据
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] read(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                is.close();
            }
        }
        return null;
    }

    /**
     * 写入数据
     *
     * @param fileName
     * @param data
     * @throws IOException
     */
    public static void write(String fileName, byte[] data) throws IOException {
        FileOutputStream os = null;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            os = new FileOutputStream(fileName);
            os.write(data);
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                os.close();
            }
        }
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param fileDir
     * @param fileName
     * @param input
     * @return
     */
    public static File write2SDFromInput(String fileDir, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            createSDFile(fileDir);
            file = createSDFile(fileDir + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将一个字符串写入到SD卡中
     *
     * @param fileDir
     * @param fileName
     * @param input
     * @return
     */
    public static File write2SDFromInput(String fileDir, String fileName, String input) {
        File file = null;
        OutputStream output = null;
        try {
            createSDFile(fileDir);
            file = createSDFile(fileDir + fileName);
            output = new FileOutputStream(file);
            output.write(input.getBytes());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getSystemAbsolutePath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    public static String getSystemAbsolutePath(Context context, String filePath) {
        return getSystemAbsolutePath(context) + "/" + filePath;
    }

    public static String getSDAbsolutePath() {
        // 得到当前外部存储设备的目录( /SDCARD )
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getSDAbsolutePath(String filePath) {
        return getSDAbsolutePath() + "/" + filePath;
    }

    /**
     * 根据系统环境，获取一个有效文件路径
     *
     * @param context
     * @param fileDir
     * @return
     */
    public static String getFilePath(Context context, String fileDir) {
        if (FileUtils.isSDCardMounted()) {
            return getSDAbsolutePath(fileDir);
        } else {
            return getSystemAbsolutePath(context, fileDir);
        }
    }

    /**
     * 根据系统环境，获取一个有效文件路径
     *
     * @param context
     * @param fileDir
     * @param fileName
     * @return
     */
    public static String getFilePath(Context context, String fileDir, String fileName) {
        if (FileUtils.isSDCardMounted()) {
            return getSDAbsolutePath(fileDir) + "/" + fileName;
        } else {
            return getSystemAbsolutePath(context, fileDir) + "/" + fileName;
        }
    }

    /**
     * 根据系统环境，获取一个有效缓存 路径
     *
     * @param context
     * @param fileDir
     * @param fileName
     * @return
     */
    public static String getCachePath(Context context, String fileDir, String fileName) {
        if (FileUtils.isSDCardMounted()) {
            return context.getExternalCacheDir().getPath() + "/" + fileDir + "/" + fileName;
        } else {
            return context.getCacheDir().getPath() + "/" + fileDir + "/" + fileName;
        }
    }

    /**
     * 从raw读取文本内容
     *
     * @param context
     * @param id
     * @return
     */
    public static String getFileFromRaw(Context context, int id) {
        StringBuffer result = new StringBuffer();
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().openRawResource(id), "UTF-8");
            bufReader = new BufferedReader(inputReader);
            String lineTxt = null;
            while ((lineTxt = bufReader.readLine()) != null)
                result.append(lineTxt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
                if (bufReader != null) {
                    bufReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString().trim();
    }

    /**
     * 从assets读取文本内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getFileFromAssets(Context context, String fileName) {
        StringBuffer result = new StringBuffer();
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName), "UTF-8");
            bufReader = new BufferedReader(inputReader);
            String lineTxt = null;
            while ((lineTxt = bufReader.readLine()) != null) {
                result.append(lineTxt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
                if (bufReader != null) {
                    bufReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString().trim();
    }

    public static String getUriFromLocalFile(File file) {
        return Uri.decode(Uri.fromFile(file).toString());
    }

    public static String getUriFromLocalFile(String filePath) {
        return Uri.decode(Uri.fromFile(new File(filePath)).toString());
    }
}
