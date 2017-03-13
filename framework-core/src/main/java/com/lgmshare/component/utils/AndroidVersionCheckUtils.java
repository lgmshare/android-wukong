package com.lgmshare.component.utils;

import android.os.Build;

/**
 * 用于多版本兼容检测
 *
 * @version V1.0
 * @author: lim
 * @description AndroidVersionCheckUtils 用于多版本兼容检测
 * @datetime 2013-1-10 上午 9:53
 */
public class AndroidVersionCheckUtils {

    public static int version() {
        return Build.VERSION.SDK_INT;
    }

    public static class VERSION_CODES {
        public static final int CUR_DEVELOPMENT = 10000;
        public static final int BASE = 1;
        public static final int BASE_1_1 = 2;
        public static final int CUPCAKE = 3;
        public static final int DONUT = 4;
        public static final int ECLAIR = 5;
        public static final int ECLAIR_0_1 = 6;
        public static final int ECLAIR_MR1 = 7;
        public static final int FROYO = 8;
        public static final int GINGERBREAD = 9;
        public static final int GINGERBREAD_MR1 = 10;
        public static final int HONEYCOMB = 11;
        public static final int HONEYCOMB_MR1 = 12;
        public static final int HONEYCOMB_MR2 = 13;
        public static final int ICE_CREAM_SANDWICH = 14;
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        public static final int JELLY_BEAN = 16;
        public static final int JELLY_BEAN_MR1 = 17;
        public static final int JELLY_BEAN_MR2 = 18;
        public static final int KITKAT = 19;
        public static final int KITKAT_WATCH = 20;
        public static final int LOLLIPOP = 21;
        public static final int LOLLIPOP_MR1 = 22;
        public static final int M = 23;
    }

    /**
     * 当前Android系统版本是否在（ Donut） Android 1.6或以上
     *
     * @return
     */
    public static boolean hasDonut() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT;
    }

    /**
     * 当前Android系统版本是否在（ Eclair） Android 2.0或 以上
     *
     * @return
     */
    public static boolean hasEclair() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
    }

    /**
     * 当前Android系统版本是否在（ Froyo） Android 2.2或 Android 2.2以上
     *
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 当前Android系统版本是否在（ Gingerbread） Android 2.3x或 Android 2.3x 以上
     *
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 当前Android系统版本是否在（ Honeycomb） Android3.1或 Android3.1以上
     *
     * @return
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 当前Android系统版本是否在（ HoneycombMR1） Android3.1.1或 Android3.1.1以上
     *
     * @return
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 当前Android系统版本是否在（ IceCreamSandwich） Android4.0或 Android4.0以上
     *
     * @return
     */
    public static boolean hasIcecreamsandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 当前Android系统版本是否在（ JELLY_BEAN） Android4.1或 Android4.1以上
     *
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 当前Android系统版本是否在（ JELLY_BEAN） Android4.2或 Android4.2以上
     *
     * @return
     */
    public static boolean hasJellyBeanMr1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 当前Android系统版本是否在（ JELLY_BEAN） Android4.3或 Android4.3以上
     *
     * @return
     */
    public static boolean hasJellyBeanMr2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }


    /**
     * 当前Android系统版本是否在（ KITKAT） Android4.4或 Android4.4以上
     *
     * @return
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 当前Android系统版本是否在（ LOLLIPOP） Android5.0或 Android5.0以上
     *
     * @return
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 当前Android系统版本是否在（ M） Android6.0或 Android6.0以上
     *
     * @return
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
