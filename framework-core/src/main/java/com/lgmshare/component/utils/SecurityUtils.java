package com.lgmshare.component.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author: lim
 * @description: 安全加密工具
 * @Email limshare@gmail.com
 * @datetime 2014-11-17 下午5:02:58
 */
public class SecurityUtils {

    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final long POLY64REV = -7661587058870466123L;
    private static final long INITIALCRC = -1L;
    private static long[] sCrcTable = new long[256];

    static {
        for (int i = 0; i < 256; i++) {
            long part = i;
            for (int j = 0; j < 8; j++) {
                long x = ((int) part & 0x1) != 0 ? -7661587058870466123L : 0L;
                part = part >> 1 ^ x;
            }
            sCrcTable[i] = part;
        }
    }

    /**
     * 计算信息摘要
     *
     * @param source
     * @param algorithm 算法
     * @return
     */
    public static String encrypt(String source, String algorithm) {
        if (source == null) {
            return null;
        }
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(source.getBytes());
            result = bytes2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算信息摘要
     *
     * @param file      文件
     * @param algorithm 算法
     * @return
     */
    public static String encrypt(File file, String algorithm) {
        if ((file == null) || (!file.exists()) || (!file.isFile())) {
            return null;
        }
        String result = null;
        try {
            result = encryptOrThrow(file, algorithm);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算信息摘要
     *
     * @param file      文件
     * @param algorithm 算法
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String encryptOrThrow(File file, String algorithm) throws IOException, NoSuchAlgorithmException {
        if (file == null) {
            return null;
        }
        String result = null;
        FileInputStream fis = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            fis = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            result = bytes2Hex(digest.digest());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 计算MD5信息摘要
     *
     * @param source
     * @return
     */
    public static String encryptMD5(String source) {
        return encrypt(source, "MD5");
    }

    /**
     * 文件计算MD5信息摘要
     *
     * @param file
     * @return
     */
    public static String encryptMD5(File file) {
        return encrypt(file, "MD5");
    }

    /**
     * HEX编码
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        if ((bytes == null) || (bytes.length == 0)) {
            return null;
        }

        char[] buf = new char[2 * bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf[(2 * i + 1)] = digits[(b & 0xF)];
            b = (byte) (b >>> 4);
            buf[(2 * i)] = digits[(b & 0xF)];
        }
        return new String(buf);
    }

    /**
     * crc64算法
     *
     * @param in
     * @return
     */
    public static long crc64Long(String in) {
        if ((in == null) || (in.length() == 0)) {
            return 0L;
        }
        return crc64Long(getBytes(in));
    }

    /**
     * crc64算法
     *
     * @param buffer
     * @return
     */
    public static long crc64Long(byte[] buffer) {
        long crc = -1L;
        int k = 0;
        for (int n = buffer.length; k < n; k++) {
            crc = sCrcTable[(((int) crc ^ buffer[k]) & 0xFF)] ^ crc >> 8;
        }
        return crc;
    }

    public static byte[] getBytes(String in) {
        byte[] result = new byte[in.length() * 2];
        int output = 0;
        char[] charArray = in.toCharArray();
        for (char ch : charArray) {
            result[(output++)] = ((byte) (ch & 0xFF));
            result[(output++)] = ((byte) (ch >> '\b'));
        }
        return result;
    }
}