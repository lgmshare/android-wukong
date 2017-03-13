package com.lgmshare.component.utils;

import android.util.Log;

import com.lgmshare.component.annotation.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * 字符类处理工具类
 *
 * @author: lim
 * @description: 字符类处理工具类
 * @email: lgmshare@gmail.com
 * @datetime 2014-6-3  上午09:41:55
 */
public class StringUtils {

    private static final String TAG = "StringUtils";

    /**
     * HEX编码 将形如0x12 0x2A 0x01 转换为122A01
     *
     * @param data 待转换数据
     * @return 转换后数据
     */
    public static String hexEncode(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String tmp = Integer.toHexString(data[i] & 0xff);
            if (tmp.length() < 2) {
                buffer.append('0');
            }
            buffer.append(tmp);
        }
        String retStr = buffer.toString().toUpperCase(Locale.getDefault());
        return retStr;
    }

    /**
     * HEX解码 将形如122A01 转换为0x12 0x2A 0x01
     *
     * @param data 待转换数据
     * @return 转换后数据
     */
    public static byte[] hexDecode(String data) {
        if (data == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < data.length(); i += 2) {
            String onebyte = data.substring(i, i + 2);
            int b = Integer.parseInt(onebyte, 16) & 0xff;
            out.write(b);
        }
        return out.toByteArray();
    }

    /**
     * 判断字符串是否为有效字符串（不为空字符串）
     *
     * @param data 待判断字符串
     * @return true:有效字符串 false:无效字符串
     */
    public static boolean isValid(String data) {
        return !(null == data || "".equals(data) || "null".equals(data));
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转整数
     *
     * @param obj
     * @return
     */
    public static int parseInt(String obj) {
        if (isEmpty(obj)) {
            return 0;
        }
        try {
            return Integer.parseInt(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double parseDouble(String obj) {
        if (isEmpty(obj)) {
            return 0;
        }
        try {
            return Double.valueOf(obj.trim());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转长整型
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long parseLong(String obj) {
        if (isEmpty(obj)) {
            return 0;
        }
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param obj
     * @return 转换异常返回 false
     */
    public static boolean parseBoolean(String obj) {
        if (isEmpty(obj)) {
            return false;
        }
        try {
            return Boolean.parseBoolean(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * byte[]转字符串
     *
     * @param data
     * @return
     */
    public static String bytesToString(byte[] data) {
        String result = "";
        if (data == null) {
            return result;
        }
        return new String(data);
    }

    /**
     * byte[]转字符串
     *
     * @param data
     * @param encode 编码方式
     * @return
     */
    public static String bytesToString(byte[] data, String encode) {
        String result = "";
        if (data == null) {
            return result;
        }
        try {
            result = new String(data, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "不支持的编码方式");
        }
        return result;
    }

    /**
     * 字符串转byte[] （默认UTF-8编码）
     *
     * @param data
     * @return
     */
    public static byte[] stringToBytes(@NotNull String data) {
        byte[] result = null;
        if (data == null) {
            return result;
        }
        return data.getBytes();
    }

    /**
     * 字符串转byte[]
     *
     * @param data
     * @param encode 编码方式
     * @return
     */
    public static byte[] stringToBytes(@NotNull String data, @NotNull String encode) {
        byte[] result = null;
        if (data == null) {
            return result;
        }
        try {
            result = data.getBytes(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "不支持的编码方式");
        }
        return result;
    }

    /**
     * 是否是只包含‘a-zA-Z’的字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyChar(String txt) {
        return txt.matches("[a-zA-Z]+");
    }

    /**
     * 是否是只包含中文的字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyChineseChar(String txt) {
        return txt.matches("[一-龥]+");
    }

    /**
     * 是否是只包含‘A-Z’的大写字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyLargeChar(String txt) {
        return txt.matches("[A-Z]+");
    }

    /**
     * 是否是只包含‘a-z’的小写字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyLowerChar(String txt) {
        return txt.matches("[a-z]+");
    }

    /**
     * 是否是只包含‘0-9’的数字字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyNumber(String txt) {
        return txt.matches("[0-9]+");
    }

    /**
     * 是否是只包含数字和下划线的字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyNumberCharAndUnderline(String txt) {
        return txt.matches("\\w+");
    }

    /**
     * 是否是只包含数字和‘a-zA-Z’的字符窜
     *
     * @param txt
     * @return
     */
    public static boolean checkOnlyNumerAndChar(String txt) {
        return txt.matches("[A-Za-z0-9]+");
    }
}
