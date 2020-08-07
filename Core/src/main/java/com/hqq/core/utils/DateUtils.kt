package com.hqq.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   DateUtils
 * @Date : 2018/7/5 0005  上午 11:03
 * @Email :  qiqiang213@gmail.com
 * @Descrive : https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/lib/src/main/java/com/blankj/utilcode/util/TimeUtils.java
 * 字母	日期或时间元素	表示	示例
 * G	Era 标志符	Text	AD
 * y	年	Year	1996; 96
 * M	年中的月份	Month	July; Jul; 07
 * w	年中的周数	Number	27
 * W	月份中的周数	Number	2
 * D	年中的天数	Number	189
 * d	月份中的天数	Number	10
 * F	月份中的星期	Number	2
 * E	星期中的天数	Text	Tuesday; Tue
 * a	Am/pm 标记	Text	PM
 * H	一天中的小时数（0-23）	Number	0
 * k	一天中的小时数（1-24）	Number	24
 * K	am/pm 中的小时数（0-11）	Number	0
 * h	am/pm 中的小时数（1-12）	Number	12
 * m	小时中的分钟数	Number	30
 * s	分钟中的秒数	Number	55
 * S	毫秒数	Number	978
 * z	时区	General time zone	Pacific Standard Time; PST; GMT-08:00
 * Z	时区	RFC 822 time zone	-0800
 */
object DateUtils {
    private val SDF_THREAD_LOCAL = ThreadLocal<SimpleDateFormat>()
    private val defaultFormat: SimpleDateFormat
        private get() {
            var simpleDateFormat = SDF_THREAD_LOCAL.get()
            if (simpleDateFormat == null) {
                simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                SDF_THREAD_LOCAL.set(simpleDateFormat)
            }
            return simpleDateFormat
        }

    /**
     * 获取当前东八区的时间
     *
     * @return
     */
    val nowDate: String
        get() {
            val dff = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            dff.timeZone = TimeZone.getTimeZone("GMT+08")
            return dff.format(Date())
        }

    /**
     * 月日时分秒，0-9前补0
     */
    fun fillZero(number: Int): String {
        return if (number < 10) "0$number" else "" + number
    }

    /**
     * 根据年份及月份计算每月的天数
     */
    fun calculateDaysInMonth(year: Int, month: Int): Int {
        // 添加大小月月份并将其转换为list,方便之后的判断
        val bigMonths = arrayOf("1", "3", "5", "7", "8", "10", "12")
        val littleMonths = arrayOf("4", "6", "9", "11")
        val bigList = Arrays.asList(*bigMonths)
        val littleList = Arrays.asList(*littleMonths)
        // 判断大小月及是否闰年,用来确定"日"的数据
        return if (bigList.contains(month.toString())) {
            31
        } else if (littleList.contains(month.toString())) {
            30
        } else {
            if (year <= 0) {
                return 29
            }
            // 是否闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                29
            } else {
                28
            }
        }
    }

    /**
     * 将时分秒转为秒数
     *
     * @param time 秒
     * @return string
     */
    fun formatTurnSecond(time: String): Long {
        val index1 = time.indexOf(":")
        val index2 = time.indexOf(":", index1 + 1)
        val hh = time.substring(0, index1).toInt().toLong()
        val mi = time.substring(index1 + 1, index2).toInt().toLong()
        val ss = time.substring(index2 + 1).toInt().toLong()
        return hh * 60 * 60 + mi * 60 + ss
    }

    /**
     * @param time yyyy-MM-dd HH-mm-ss
     * @return
     */
    fun getDayInData(time: String): String {
        if (RegexUtils.unNull(time)) {
            val str = time.split(" ".toRegex()).toTypedArray()
            return if (str.size > 1) {
                str[1]
            } else ""
        }
        return ""
    }

    /**
     * @param time 00:00:00
     * @return 000000
     */
    fun format2Second(time: String): Long {
        if (RegexUtils.isNull(time)) {
            return 0
        }
        val str = time.split(":".toRegex()).toTypedArray()
        var hh: Long = 0
        var mi: Long = 0
        var ss: Long = 0
        when (str.size) {
            0 -> {
            }
            1 -> hh = str[0].toInt().toLong()
            2 -> {
                hh = str[0].toInt().toLong()
                mi = str[1].toInt().toLong()
            }
            else -> {
                hh = str[0].toInt().toLong()
                mi = str[1].toInt().toLong()
                ss = str[2].toInt().toLong()
            }
        }
        return hh * 60 * 60 + mi * 60 + ss
    }

    /**
     * @param time 00:00:00:00
     * @return 000000
     */
    fun format2Millisecond(time: String): Long {
        if (RegexUtils.isNull(time)) {
            return 0
        }
        val str = time.split(":".toRegex()).toTypedArray()
        var hh: Long = 0
        var mi: Long = 0
        var ss: Long = 0
        var mm: Long = 0
        when (str.size) {
            0 -> {
            }
            1 -> hh = str[0].toInt().toLong()
            2 -> {
                hh = str[0].toInt().toLong()
                mi = str[1].toInt().toLong()
            }
            3 -> {
                hh = str[0].toInt().toLong()
                mi = str[1].toInt().toLong()
                ss = str[2].toInt().toLong()
            }
            else -> {
                hh = str[0].toInt().toLong()
                mi = str[1].toInt().toLong()
                ss = str[2].toInt().toLong()
                mm = str[3].toInt().toLong()
            }
        }
        return (hh * 60 * 60 + mi * 60 + ss) * 1000 + mm
    }

    /**
     * 将秒数转为时分秒
     *
     * @param second 秒
     * @return string 00:00:00
     */
    fun change(second: Int): String {
        var h = 0
        var d = 0
        var s = 0
        val temp = second % 3600
        if (second > 3600) {
            h = second / 3600
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60
                    if (temp % 60 != 0) {
                        s = temp % 60
                    }
                } else {
                    s = temp
                }
            }
        } else {
            d = second / 60
            if (second % 60 != 0) {
                s = second % 60
            }
        }
        return (if (h > 9) "" + h else "0$h") + ":" +
                (if (d > 9) "" + d else "0$d") + ":" +
                if (s > 9) "" + s else "0$s"
    }

    /**
     * @param leftsecond 秒
     * @return string
     */
    fun formatTime(leftsecond: Int): String {
        val day: Long = Math.floor(leftsecond / (60 * 60 * 24).toDouble()) as Int.toLong()
        val hour: Long = Math.floor((leftsecond - day * 24 * 60 * 60) / 3600.toDouble()) as Int.toLong()
        val minute: Long = Math.floor((leftsecond - day * 24 * 60 * 60 - hour * 3600) / 60.toDouble()) as Int.toLong()
        return "剩" + day + "天" + hour + "时" + minute + "分"
    }

    /**
     * @param leftsecond 秒
     * @return string
     */
    @JvmOverloads
    fun formatTime2Second(leftsecond: Int, type: Int = 1): String {
        val day: Long = Math.floor(leftsecond / (60 * 60 * 24).toDouble()) as Int.toLong()
        val hour: Long = Math.floor((leftsecond - day * 24 * 60 * 60) / 3600.toDouble()) as Int.toLong()
        val minute: Long = Math.floor((leftsecond - day * 24 * 60 * 60 - hour * 3600) / 60.toDouble()) as Int.toLong()
        val second: Long = Math.floor(leftsecond - day * 24 * 60 * 60 - hour * 3600 - (minute * 60).toDouble()) as Int.toLong()
        val stringBuilder = StringBuilder("剩")
        if (type == 1) {
            if (day > 0) {
                stringBuilder.append(day.toString() + "天")
            }
            if (hour > 0) {
                stringBuilder.append(hour.toString() + "时")
            }
        } else {
            stringBuilder.append(day.toString() + "天")
            stringBuilder.append(hour.toString() + "时")
        }
        return stringBuilder.toString() + minute + "分" + second + "秒"
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    fun string2Millisecond(date: String?): Long {
        if (RegexUtils.isNull(date)) {
            return 0L
        }
        try {
            return defaultFormat.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0L
    }

    fun millis2String(millis: Long, simpleDateFormat: SimpleDateFormat): String {
        return simpleDateFormat.format(Date(millis))
    }

    fun millis2String(millis: Long): String {
        return defaultFormat.format(Date(millis))
    }

    fun formatStringTime(time: String?, format: String?): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        try {
            return simpleDateFormat.format(defaultFormat.parse(time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("args = [$nowDate]")
    }
}