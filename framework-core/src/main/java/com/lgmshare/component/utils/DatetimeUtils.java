package com.lgmshare.component.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

/**
 * 日期时间格式化工具
 *
 * @author: lim
 * @description: 日期时间格式化工具
 * @email: lgmshare@gmail.com
 * @datetime 2014-5-14 下午09:31:06
 */
public class DatetimeUtils {

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    /**
     * 得到现在时间
     *
     * @return 时间格式时间
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        return formatter.parse(dateString, pos);
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDateLong() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        return formatter.parse(dateString, pos);
    }

    /**
     * 获取时间 小时:分:秒 HH:mm:ss
     *
     * @return HH:mm:ss
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(HH_MM_SS, Locale.getDefault());
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 得到现在小时
     */
    public static String getCurrentHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH", Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getCurrentMunite() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("mm", Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间
     *
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDateLong() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat 格式字符串 如：yyyyMMddhhmmss
     * @return
     */
    public static String getStringDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat, Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获得当前时间戳
     *
     * @return long
     */
    public static long getTimestampLong() {
        long unixTimeGMT = 0;
        try {
            unixTimeGMT = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unixTimeGMT;
    }

    /**
     * 获得当前时间戳
     *
     * @return string
     */
    public static String getTimestamp() {
        String unixTimeGMT;
        try {
            long unixTime = System.currentTimeMillis();
            unixTimeGMT = unixTime + "";
        } catch (Exception e) {
            unixTimeGMT = "";
        }
        return unixTimeGMT;

    }

    /**
     * 将长时间格式字符串转换为时间
     *
     * @param strDate 时间字符串
     * @param format  时间字符串的格式 format
     * @return
     */
    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 将短时间格式字符串转换为时间 {@link #YYYY_MM_DD}
     *
     * @param strDate
     * @return
     */
    public static Date strToDateShort(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 时间字符串 yyyyMMddHHmmss
     * @return Date
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date   日期
     * @param format 日期格式
     * @return
     */
    public static String dateToStr(Date date, String format) {
        String sdate = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            sdate = df.format(date);
        }
        return sdate;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param date yyyyMMdd
     * @return yyyy-MM-dd
     */
    public static String dateToStrShort(Date date) {
        return dateToStr(date, YYYY_MM_DD);
    }

    /**
     * 将长时间格式时间转换为字符串  <br>date转字符串</br>
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateToStrLong(Date date) {
        return dateToStr(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:mm"的格式，返回字符型的分钟
     *
     * @param st1
     * @param st2
     * @return
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) {
            return "0";
        } else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sdate1, String sdate2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        long day = 0;
        try {
            Date date = myFormatter.parse(sdate1);
            Date mydate = myFormatter.parse(sdate2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中delay表示分钟.
     *
     * @param sdate yyyyMMddHHmmss
     * @param delay delay为前移或后延的分钟数
     * @return
     */
    public static String getNextMinute(String sdate, int delay) {
        SimpleDateFormat format = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.getDefault());
        String mydate1 = "";
        try {
            Date date1 = format.parse(sdate);
            long Time = (date1.getTime() / 1000) + delay * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     *
     * @param nowdate yyyy-MM-dd
     * @param delay
     * @return yyyy-MM-dd
     */
    public static String getNextDay(String nowdate, String format, int delay) {
        try {
            String sdate = "";
            Date date = strToDate(nowdate, format);
            long myTime = (date.getTime() / 1000) + delay * 24 * 60 * 60;
            date.setTime(myTime * 1000);
            sdate = dateToStr(date, format);
            return sdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     *
     * @param date  yyyy-MM-dd
     * @param delay
     * @return yyyy-MM-dd
     */
    public static Date getNextDay(Date date, int delay) {
        try {
            Date newDate = new Date(date.getTime());
            long myTime = (date.getTime() / 1000) + delay * 24 * 60 * 60;
            newDate.setTime(myTime * 1000);
            return newDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到一个时间延后或前移几月的时间,nowdate为时间,delay为前移或后延的月数
     *
     * @param num
     * @return
     */
    public static String getNextMouth(String sdate, int num) {
        Date date = strToDateShort(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        return new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault()).format(c.getTime());
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase(Locale.getDefault()) + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param sdate
     * @return
     */
    public static String getEndDateOfMonth(String sdate) {// yyyy-MM-dd
        String str = sdate.substring(0, 8);
        String month = sdate.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(sdate)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 得到本月的第一天
     *
     * @return
     */
    public static String getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateToStrShort(calendar.getTime());
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStrShort(calendar.getTime());
    }

    /**
     * 得到某月有多少天数
     *
     * @param isLeapyear 目标年份
     * @param month      目标月份
     * @return
     */
    public static int getDaysOfMonth(boolean isLeapyear, int month) {
        int daysOfMonth = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapyear) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }
        }
        return daysOfMonth;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getWeekSeq() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        return week;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getWeekSeq(String sdate) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(strToDateShort(sdate));
        String week = Integer.toString(calendar.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        return week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeekOfDate(String sdate, int num) {
        // 再转换为时间
        Date dd = strToDateShort(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num == 1) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num == 2) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num == 3) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num == 4) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num == 5) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num == 6) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num == 0) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        if (getWeekInt(sdate) == 1) {
            return getNextDay(new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault()).format(c.getTime()), YYYY_MM_DD, -7);
        } else {
            return new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault()).format(c.getTime());
        }
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return int 1=星期日 7=星期六，其他类推
     */
    public static int getWeekInt(String sdate) {
        // 再转换为时间
        Date date = DatetimeUtils.strToDateShort(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据一个日期，返回是星期几
     *
     * @param sdate
     * @return
     */
    public static String getWeekStr(String sdate) {
        String str = "";
        int week = DatetimeUtils.getWeekInt(sdate);
        if (1 == week) {
            str = "星期日";
        } else if (2 == week) {
            str = "星期一";
        } else if (3 == week) {
            str = "星期二";
        } else if (4 == week) {
            str = "星期三";
        } else if (5 == week) {
            str = "星期四";
        } else if (6 == week) {
            str = "星期五";
        } else if (7 == week) {
            str = "星期六";
        }
        return str;
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeekStrFormat(String sdate) {
        // 再转换为时间
        Date date = DatetimeUtils.strToDateShort(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(c.getTime());
    }

    /**
     * 获取某年中的某月的第一天是星期几
     *
     * @param year  目标年份
     * @param month 目标月份
     * @return
     */
    public static int getWeekdayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取指定日期所在周的开始及结束日期
     *
     * @param sdate  指定的日期  yyyy-MM-dd
     * @param format 指定的格式
     * @return String[0]:开始日期，String[1]:结束日期
     */
    public static String[] getWeekStartAndEndDate(String sdate, String format) {
        Date date = strToDateShort(sdate);
        String[] dates = new String[2];
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dates[0] = dateToStr(c.getTime(), format);
        c.add(Calendar.DATE, 6);
        dates[1] = dateToStr(c.getTime(), format);
        return dates;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDaysDiff(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        Date date = null;
        Date mydate = null;
        long day = 0;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 计算时间差
     *
     * @param begin
     * @param end
     * @return 返回格式,"hh小时mm分钟ss秒"
     */
    public static String getTimeDifference(Date begin, Date end) {
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        // long ss = between % 60;
        long mm = (between / 60) % 60;
        long hh = (between / 3600) % 24;
        long dd = between / (3600 * 24);

        StringBuffer time = new StringBuffer();
        if (dd != 0) {
            time.append(dd + "天");
        }
        if (hh != 0) {
            time.append(hh + "小时");
        }
        if (mm != 0) {
            time.append(mm + "分钟");
        }
        /*
         * if(ss!=0 || dd != 0 || hh != 0 || mm != 0){ time.append(ss+"秒"); }
		 */
        return time.toString();
    }

    /**
     * 计算时间差
     *
     * @param beginS
     * @param endS
     * @return 返回格式,"hh小时mm分钟ss秒"
     * @throws ParseException
     */
    public static String getTimeDifference(String beginS, String endS)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM, Locale.getDefault());
        Date begin = format.parse(beginS);
        Date end = format.parse(endS);
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        // long ss = between % 60;
        long mm = (between / 60) % 60;
        long hh = (between / 3600) % 24;
        long dd = between / (3600 * 24);

        StringBuffer time = new StringBuffer();
        if (dd != 0) {
            time.append(dd + "天");
        }
        if (hh != 0 || dd != 0) {
            time.append(hh + "小时");
        }
        if (mm != 0 || dd != 0 || hh != 0) {
            time.append(mm + "分钟");
        }
        /*
         * if(ss!=0 || dd != 0 || hh != 0 || mm != 0){ time.append(ss+"秒"); }
		 */
        return time.toString();
    }

    /**
     * 此函数返回该日历第一行星期日所在的日期
     * （形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间）
     *
     * @param sdate
     * @return
     */
    public static String getMonthFristDayForWeek(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";
        // 得到这个月的1号是星期几
        Date date = DatetimeUtils.strToDateShort(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        return DatetimeUtils.getNextDay(sdate, YYYY_MM_DD, (1 - u));
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数 ，可以自己定
     */
    public static String getDateNo(int k) {
        return getStringDate(YYYYMMDDHHMMSS) + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i 长度
     * @return
     */
    public static String getRandom(int i) {
        Random random = new Random();
        if (i == 0)
            return "";
        String str = "";
        for (int k = 0; k < i; k++) {
            //得到0-9中的一个数值
            str = str + random.nextInt(9);
        }
        return str;
    }

    /**
     * 有效日期验证
     *
     * @param sdate
     */
    public static boolean isRightDate(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
        if (sdate == null)
            return false;
        if (sdate.length() > 10) {
            sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
        } else {
            sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        }
        try {
            sdf.parse(sdate);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.US);
        boolean b = false;
        Date time = strToDateShort(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = sdf.format(today);
            String timeDate = sdf.format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDateShort(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else
            return (year % 4) == 0 && (year % 100) != 0;
    }

    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && Calendar.DECEMBER == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && Calendar.DECEMBER == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 时间字符串格式化
     *
     * @param sdate yyyyMMddHHmmss
     * @param type  格式类型(1:yyyy-MM-dd \ 2:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String formatSdata(String sdate, int type) {
        try {
            if (TextUtils.isEmpty(sdate)) {
                return "";
            }

            SimpleDateFormat strFormat = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.US);
            SimpleDateFormat dateFormat = null;
            //格式化类型
            if (type == 1) {
                dateFormat = new SimpleDateFormat(YYYY_MM_DD, Locale.US);
            } else {
                dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.US);
            }
            Date date = strFormat.parse(sdate);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间字符串格式化
     *
     * @param sdate yyyyMMddHHmmss
     * @param type  格式类型(1:yyyy年MM月 \ 2:yyyy年MM月dd日\ 3:yyyy年MM月dd日 HH时mm分ss秒)
     * @return
     */
    public static String formatSdataYM(String sdate, int type) {
        try {
            if (TextUtils.isEmpty(sdate)) {
                return "";
            }

            SimpleDateFormat strFormat = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.US);
            SimpleDateFormat dateFormat = null;
            //格式化类型
            if (type == 1) {
                dateFormat = new SimpleDateFormat("yyyy年MM月", Locale.US);
            } else if (type == 2) {
                dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.US);
            } else {
                dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.US);
            }
            Date date = strFormat.parse(sdate);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间字符串格式化
     *
     * @param sdate yyyy-MM-dd
     * @return yyyyMMdd
     */
    public static String sdataFormatShort(String sdate) {
        try {
            if (TextUtils.isEmpty(sdate)) {
                return "";
            }
            SimpleDateFormat strFormat = new SimpleDateFormat(YYYY_MM_DD, Locale.US);
            SimpleDateFormat dateFormat = null;
            //格式化类型
            dateFormat = new SimpleDateFormat(YYYYMMDD, Locale.US);
            Date date = strFormat.parse(sdate);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间字符串格式化
     *
     * @param sdate yyyy-MM-dd HH:mm:ss
     * @return yyyyMMddHHmmss
     */
    public static String sdataFormat(String sdate) {
        try {
            if (TextUtils.isEmpty(sdate)) {
                return "";
            }
            SimpleDateFormat strFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.US);
            SimpleDateFormat dateFormat = null;
            //格式化类型
            dateFormat = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.US);
            Date date = strFormat.parse(sdate);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间戳转日期格式
     *
     * @param timestamp
     * @param format
     * @return
     */
    public static String timestampToSdate(String timestamp, String format) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(timestampToDate(timestamp));
    }

    /**
     * 时间戳转日期格式
     *
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(String timestamp) {
        if (TextUtils.isEmpty(timestamp)) {
            return null;
        }
        Date date = null;
        if (timestamp.length() == 10) {
            date = new Date(Long.parseLong(timestamp) * 1000);
        } else if (timestamp.length() == 13) {
            date = new Date(Long.parseLong(timestamp));
        }
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    /**
     * 日期格式转时间戳
     *
     * @param strDate
     * @return
     */
    public static String sdateToTimestamp(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(strDate, pos);
        return String.valueOf(date.getTime());
    }

    /**
     * 时间字符串格式化
     *
     * @param sdate   时间字符串
     * @param format1 传入时间格式
     * @param format2 返回时间格式
     * @return
     */
    public static String format(String sdate, String format1, String format2) {
        try {
            if (TextUtils.isEmpty(sdate)) {
                return "";
            }

            SimpleDateFormat strFormat = new SimpleDateFormat(format1, Locale.US);
            SimpleDateFormat dateFormat = new SimpleDateFormat(format2, Locale.US);
            Date date = strFormat.parse(sdate);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 对时间戳格式进行格式化，保证时间戳长度为13位
     *
     * @param timestamp 时间戳
     * @return 返回为13位的时间戳
     */
    public static String formatTimestamp(String timestamp) {
        if (timestamp == null || "".equals(timestamp)) {
            return "";
        }
        String tempTimeStamp = timestamp + "00000000000000";
        StringBuffer stringBuffer = new StringBuffer(tempTimeStamp);
        return tempTimeStamp = stringBuffer.substring(0, 13);
    }

    /**
     * 以友好的方式显示时间，根据 timestamp 生成各类时间状态串
     *
     * @param timestamp 距1970 00:00:00 GMT的秒数
     * @param format    格式
     * @return 时间状态串(如：刚刚5分钟前)
     */
    public static String friendlyTimeState(String timestamp, String format) {
        if (timestamp == null || "".equals(timestamp)) {
            return "";
        }

        try {
            timestamp = formatTimestamp(timestamp);
            long _timestamp = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - _timestamp < 60 * 1000) {
                return "刚刚";
            } else if (System.currentTimeMillis() - _timestamp < 30 * 60 * 1000) {
                return ((System.currentTimeMillis() - _timestamp) / 1000 / 60) + "分钟前";
            } else {
                SimpleDateFormat sdf = null;
                Calendar now = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(_timestamp);
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                    sdf = new SimpleDateFormat("今天 HH:mm", Locale.US);
                    return sdf.format(c.getTime());
                }
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
                    sdf = new SimpleDateFormat("昨天 HH:mm", Locale.US);
                    return sdf.format(c.getTime());
                } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    if (format != null && !format.equalsIgnoreCase("")) {
                        sdf = new SimpleDateFormat(format, Locale.US);
                    } else {
                        sdf = new SimpleDateFormat("M月d日 HH:mm", Locale.US);
                    }
                    return sdf.format(c.getTime());
                } else {
                    if (format != null && !format.equalsIgnoreCase("")) {
                        sdf = new SimpleDateFormat(format, Locale.US);
                    } else {
                        sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm", Locale.US);
                    }
                    return sdf.format(c.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String friendlyTime(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.US);
        Date time = strToDateLong(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = sdf.format(cal.getTime());
        String paramDate = sdf.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = sdf.format(time);
        }
        return ftime;
    }

    /**
     * 以友好的方式显示日期
     *
     * @param sdate yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String friendlyDate(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.US);
        Date time = strToDateLong(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = sdf.format(cal.getTime());
        String paramDate = sdf.format(time);
        if (curDate.equals(paramDate)) {
            return "今天";
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days < -2) {
            ftime = sdf.format(time);
        } else if (days == -2) {
            ftime = "后天";
        } else if (days == -1) {
            ftime = "明天";
        } else if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = sdf.format(time);
        }
        return ftime;
    }

}