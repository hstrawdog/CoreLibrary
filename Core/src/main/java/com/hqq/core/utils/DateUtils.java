package com.hqq.core.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   DateUtils
 * @Date : 2018/7/5 0005  上午 11:03
 * @Descrive : https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/TimeUtils.java
 * @Email :
 */
public class DateUtils {
    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    private static SimpleDateFormat getDefaultFormat() {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * 月日时分秒，0-9前补0
     */
    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * 根据年份及月份计算每月的天数
     */
    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }


    /**
     * 将时分秒转为秒数
     *
     * @param time 秒
     * @return string
     */
    public static long formatTurnSecond(String time) {
        String s = time;
        int index1 = s.indexOf(":");
        int index2 = s.indexOf(":", index1 + 1);
        int hh = Integer.parseInt(s.substring(0, index1));
        int mi = Integer.parseInt(s.substring(index1 + 1, index2));
        int ss = Integer.parseInt(s.substring(index2 + 1));

        return hh * 60 * 60 + mi * 60 + ss;
    }


    /**
     * @param time yyyy-MM-dd HH-mm-ss
     * @return
     */
    public static String getDayInData(String time) {

        if (RegexUtils.checkNotNull(time)) {
            String[] str = time.split(" ");
            if (str.length > 1) {
                return str[1];
            }
            return "";
        }
        return "";
    }


    /**
     * @param time 00:00:00
     * @return 000000
     */
    public static int format2Second(String time) {
        if (RegexUtils.checkNull(time)) {
            return 0;
        }

        String[] str = time.split(":");
        int hh = 0;
        int mi = 0;
        int ss = 0;

        switch (str.length) {
            case 0:
                break;
            case 1:
                hh = Integer.parseInt(str[0]);
                break;
            case 2:
                hh = Integer.parseInt(str[0]);
                mi = Integer.parseInt(str[1]);
                break;
            default:
                hh = Integer.parseInt(str[0]);
                mi = Integer.parseInt(str[1]);
                ss = Integer.parseInt(str[2]);
                break;
        }
        return hh * 60 * 60 + mi * 60 + ss;
    }

    /**
     * @param time 00:00:00:00
     * @return 000000
     */
    public static long format2Millisecond(String time) {
        if (RegexUtils.checkNull(time)) {
            return 0;
        }

        String[] str = time.split(":");
        int hh = 0;
        int mi = 0;
        int ss = 0;
        int mm = 0;

        switch (str.length) {
            case 0:
                break;
            case 1:
                hh = Integer.parseInt(str[0]);
                break;
            case 2:
                hh = Integer.parseInt(str[0]);
                mi = Integer.parseInt(str[1]);
                break;
            case 3:

                hh = Integer.parseInt(str[0]);
                mi = Integer.parseInt(str[1]);
                ss = Integer.parseInt(str[2]);
                break;
            default:
                hh = Integer.parseInt(str[0]);
                mi = Integer.parseInt(str[1]);
                ss = Integer.parseInt(str[2]);
                mm = Integer.parseInt(str[3]);
                break;
        }
        return (hh * 60 * 60 + mi * 60 + ss) * 1000 + mm;

    }


    /**
     * 将秒数转为时分秒
     *
     * @param second 秒
     * @return string 00:00:00
     */
    public static String change(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return (h > 9 ? "" + h : "0" + h) + ":" +
                (d > 9 ? "" + d : "0" + d) + ":" +
                (s > 9 ? "" + s : "0" + s);
    }

    /**
     * @param leftsecond 秒
     * @return string
     */
    public static String formatTime(int leftsecond) {
        int day = (int) Math.floor(leftsecond / (60 * 60 * 24));
        int hour = (int) Math.floor((leftsecond - day * 24 * 60 * 60) / 3600);
        int minute = (int) Math.floor((leftsecond - day * 24 * 60 * 60 - hour * 3600) / 60);
        return "剩" + day + "天" + hour + "时" + minute + "分";
    }

    /**
     * @param leftsecond 秒
     * @return string
     */
    public static String formatTime2Second(int leftsecond) {
        return formatTime2Second(leftsecond, 1);
    }

    public static String formatTime2Second(int leftsecond, int type) {
        int day = (int) Math.floor(leftsecond / (60 * 60 * 24));
        int hour = (int) Math.floor((leftsecond - day * 24 * 60 * 60) / 3600);
        int minute = (int) Math.floor((leftsecond - day * 24 * 60 * 60 - hour * 3600) / 60);
        int second = (int) Math.floor(leftsecond - day * 24 * 60 * 60 - hour * 3600 - minute * 60);

        StringBuilder stringBuilder = new StringBuilder("剩");

        if (type == 1) {
            if (day > 0) {
                stringBuilder.append(day + "天");
            }
            if (hour > 0) {
                stringBuilder.append(hour + "时");
            }
        } else {
            stringBuilder.append(day + "天");
            stringBuilder.append(hour + "时");
        }

        return stringBuilder.toString() + minute + "分" + second + "秒";
    }


    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Long string2Millisecond(String date) {
        if (RegexUtils.checkNull(date)) {
            return 0L;
        }
        try {
            return getDefaultFormat().parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String millis2String(final long millis, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date(millis));
    }

    public static String millis2String(final long millis) {
        return getDefaultFormat().format(new Date(millis));
    }


    public static String formatStringTime(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return simpleDateFormat.format(getDefaultFormat().parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) {
//        System.out.println(string2Millisecond("2019-01-07 17:36:22"));
//        System.out.println(
//                millis2String(string2Millisecond("2019-01-07 17:36:22") - 30 * 60 * 1000
//                        , new SimpleDateFormat("MM月dd日HH时mm分ss秒")));
//
//        for (int i = 0; i < 10; i++) {
//            if (i == 6) {
//                break;
//            }
//            System.out.println(i);
//        }
//        System.out.println(string2Millisecond(""));


        long n = 10000;

        long start1 = System.currentTimeMillis();
        String s1 = new String("hello");
        for (long i = 0; i < n; i++) {
            s1 += "拼接--";
        }
        long end1 = System.currentTimeMillis();
        long time1 = end1 - start1;
        System.out.println("用String+=拼接字符串的时间:" + time1);

        long start2 = System.currentTimeMillis();
        String s2 = new String("hello");
        for (long i = 0; i < n; i++) {
            s2 = s2 + "拼接--";
        }
        long end2 = System.currentTimeMillis();
        long time2 = end2 - start2;
        System.out.println("用String=String+拼接字符串的时间" + time2);

        long start3 = System.currentTimeMillis();
        String s3 = new String("hello");
        for (long i = 0; i < n; i++) {
            s3 = s3.concat("拼接--");
        }
        long end3 = System.currentTimeMillis();
        long time3 = end3 - start3;
        System.out.println("用String.concat拼接字符串的时间" + time3);

        long start4 = System.currentTimeMillis();
        StringBuffer s4 = new StringBuffer("hello");
        for (long i = 0; i < n; i++) {
            s4.append("拼接--");
        }
        long end4 = System.currentTimeMillis();
        long time4 = end4 - start4;
        System.out.println("用StringBuffer.append拼接字符串的时间" + time4);

        long start5 = System.currentTimeMillis();
        StringBuilder s5 = new StringBuilder("hello");
        for (long i = 0; i < n; i++) {
            s5.append("拼接--");
        }
        long end5 = System.currentTimeMillis();
        long time5 = end5 - start5;
        System.out.println("用StringBuilder.append拼接字符串的时间" + time5);

        long start6 = System.currentTimeMillis();
        String.format("%s1231231", 123123);
        long end6 = System.currentTimeMillis();
        long time6 = end6 - start6;
        System.out.println("用String.format拼接字符串的时间" + time6);

    }


}
