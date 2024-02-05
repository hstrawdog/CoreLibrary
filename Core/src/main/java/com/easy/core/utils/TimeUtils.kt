package com.easy.core.utils

import com.easy.core.utils.DataUtils.isNullString
import com.easy.core.utils.DataUtils.stringToInt
import com.easy.core.utils.log.LogUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author tamsiree
 * @date 2016/1/24
 * 时间相关工具类
 */
object TimeUtils {
    /**
     *
     * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
     * 格式的意义如下： 日期和时间模式
     * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * HH:mm    15:44
     * h:mm a    3:44 下午
     * HH:mm z    15:44 CST
     * HH:mm Z    15:44 +0800
     * HH:mm zzzz    15:44 中国标准时间
     * HH:mm:ss    15:44:40
     * yyyy-MM-dd    2016-08-12
     * yyyy-MM-dd HH:mm    2016-08-12 15:44
     * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     * K:mm a    3:44 下午
     * EEE, MMM d, ''yy    星期五, 八月 12, '16
     * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     * yyMMddHHmmssZ    160812154440+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     *  https://github.com/Blankj/AndroidUtilCode
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
    </pre> *
     */

    enum class TimeUnit {
        MSEC, SEC, MIN, HOUR, DAY
    }
    /******************** 时间相关常量  */
    /**
     * 秒与毫秒的倍数
     */
    const val SEC = 1000

    /**
     * 分与毫秒的倍数
     */
    const val MIN = 60000

    /**
     * 时与毫秒的倍数
     */
    const val HOUR = 3600000

    /**
     * 天与毫秒的倍数
     */
    const val DAY = 86400000

    /**
     * 毫秒与毫秒的倍数
     */
    const val MSEC = 1

    /**
     * 时间戳格式转换
     */
    val dayNames = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

    //Date格式 常用
    const val DATE_FORMAT_DETACH = "yyyy-MM-dd HH:mm:ss"

    //Date格式 带毫秒
    const val DATE_FORMAT_DETACH_SSS = "yyyy-MM-dd HH:mm:ss SSS"

    //时间格式 分钟：秒钟 一般用于视频时间显示
    const val DATE_FORMAT_MM_SS = "mm:ss"

    val DEFAULT_SDF = SimpleDateFormat(DATE_FORMAT_DETACH, Locale.getDefault())

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    val curTimeMills: Long
        get() = System.currentTimeMillis()

    /**
     * 获取当前时间
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @return 时间字符串
     */
    @JvmStatic
    val curTimeString: String
        get() = date2String(Date())

    /**
     * 获取当前时间
     *
     * Date类型
     *
     * @return Date类型时间
     */
    val curTimeDate: Date
        get() = Date()
    private val SDF_THREAD_LOCAL = ThreadLocal<SimpleDateFormat>()

    val defaultFormat: SimpleDateFormat
        get() {
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

    val nowDate4yyyyMMdd: String
        get() {
            val dff = SimpleDateFormat("yyyy-MM-dd")
            dff.timeZone = TimeZone.getTimeZone("GMT+08")
            return dff.format(Date())
        }

    /**
     * 将时间戳转为时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    @JvmStatic
    fun milliseconds2String(milliseconds: Long, format: SimpleDateFormat = DEFAULT_SDF): String {
        return format.format(Date(milliseconds))
    }

    /**
     * 将时间字符串转为时间戳
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    @JvmStatic
    fun string2Milliseconds(time: String?, format: SimpleDateFormat = DEFAULT_SDF): Long {
        try {
            return format.parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 将时间字符串转为Date类型
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return Date类型
     */
    @JvmStatic
    fun string2Date(time: String?, format: SimpleDateFormat = DEFAULT_SDF): Date {
        return Date(string2Milliseconds(time, format))
    }

    /**
     * 将Date类型转为时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    @JvmStatic
    fun date2String(time: Date?, format: SimpleDateFormat = DEFAULT_SDF): String {
        return format.format(time)
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    @JvmStatic
    fun date2Milliseconds(time: Date): Long {
        return time.time
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    @JvmStatic
    fun milliseconds2Date(milliseconds: Long): Date {
        return Date(milliseconds)
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    @JvmStatic
    fun milliseconds2Unit(milliseconds: Long, unit: TimeUnit?): Long {
        when (unit) {
            TimeUnit.MSEC -> return milliseconds / MSEC
            TimeUnit.SEC -> return milliseconds / SEC
            TimeUnit.MIN -> return milliseconds / MIN
            TimeUnit.HOUR -> return milliseconds / HOUR
            TimeUnit.DAY -> return milliseconds / DAY
            else -> {
            }
        }
        return -1
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2格式都为yyyy-MM-dd HH:mm:ss
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    @JvmStatic
    fun getIntervalTime(time0: String?, time1: String?, unit: TimeUnit?): Long {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF)
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2格式都为format
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @param format 时间格式
     * @return unit时间戳
     */
    @JvmStatic
    fun getIntervalTime(time0: String?, time1: String?, unit: TimeUnit?, format: SimpleDateFormat): Long {
        return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format) - string2Milliseconds(time1, format), unit))
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2都为Date类型
     *
     * @param time1 Date类型时间1
     * @param time2 Date类型时间2
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    @JvmStatic
    fun getIntervalTime(time1: Date, time2: Date, unit: TimeUnit?): Long {
        return Math.abs(milliseconds2Unit(date2Milliseconds(time2) - date2Milliseconds(time1), unit))
    }

    /**
     * 获取当前时间
     *
     * 格式为用户自定义
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    @JvmStatic
    fun getCurTimeString(format: SimpleDateFormat): String {
        return date2String(Date(), format)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @param unit
     *  * [TimeUnit.MSEC]:毫秒
     *  * [TimeUnit.SEC]:秒
     *  * [TimeUnit.MIN]:分
     *  * [TimeUnit.HOUR]:小时
     *  * [TimeUnit.DAY]:天
     *
     * @return unit时间戳
     */
    @JvmStatic
    fun getIntervalByNow(time: String?, unit: TimeUnit?): Long {
        return getIntervalByNow(time, unit, DEFAULT_SDF)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * time格式为format
     *
     * @param time   时间字符串
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @param format 时间格式
     * @return unit时间戳
     */
    @JvmStatic
    fun getIntervalByNow(time: String?, unit: TimeUnit?, format: SimpleDateFormat): Long {
        return getIntervalTime(curTimeString, time, unit, format)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * time为Date类型
     *
     * @param time Date类型时间
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    @JvmStatic
    fun getIntervalByNow(time: Date, unit: TimeUnit?): Long {
        return getIntervalTime(curTimeDate, time, unit)
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return `true`: 闰年<br></br>
     * `false`: 平年
     */
    @JvmStatic
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 将date转换成format格式的日期
     *
     * @param format 格式
     * @param date   日期
     * @return
     */
    @JvmStatic
    fun simpleDateFormat(format: String?, date: Date?): String {
        var format = format
        if (isNullString(format)) {
            format = DATE_FORMAT_DETACH_SSS
        }
        return SimpleDateFormat(format).format(date)
    }
    //--------------------------------------------字符串转换成时间戳-----------------------------------
    /**
     * 将指定格式的日期转换成时间戳
     *
     * @param mDate
     * @return
     */
    @JvmStatic
    fun Date2Timestamp(mDate: Date?): String {
        return mDate!!.time.toString()
            .substring(0, 10)
    }

    /**
     * 将日期字符串 按照 指定的格式 转换成 DATE
     * 转换失败时 return null;
     *
     * @param format
     * @param datess
     * @return
     */
    @JvmStatic
    fun string2Date(format: String?, datess: String?): Date? {
        val sdr = SimpleDateFormat(format)
        var date: Date? = null
        try {
            date = sdr.parse(datess)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * 将 yyyy年MM月dd日 转换成 时间戳
     *
     * @param format
     * @param datess
     * @return
     */
    @JvmStatic
    fun string2Timestamp(format: String?, datess: String?): String {
        val date = string2Date(format, datess)
        return Date2Timestamp(date)
    }
    //===========================================字符串转换成时间戳====================================
    /**
     * 获取当前日期时间 / 得到今天的日期
     * str yyyy-MM-dd HH:mm:ss 之类的
     *
     * @return
     */
    @JvmStatic
    fun getCurrentDateTime(format: String?): String {
        return simpleDateFormat(format, Date())
    }

    /**
     * 时间戳  转换成 指定格式的日期
     * 如果format为空，则默认格式为
     *
     * @param times  时间戳
     * @param format 日期格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    @JvmStatic
    fun getDate(times: String?, format: String?): String {
        return simpleDateFormat(format, Date(stringToInt(times!!) * 1000L))
    }

    /**
     * 得到昨天的日期
     *
     * @param format 日期格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    @JvmStatic
    fun getYesterdayDate(format: String?): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        return simpleDateFormat(format, calendar.time)
    }

    /**
     * 视频时间 转换成 "mm:ss"
     *
     * @param milliseconds
     * @return
     */
    @JvmStatic
    fun formatTime(milliseconds: Long): String {
        val format = DATE_FORMAT_MM_SS
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("GMT+0")
        return sdf.format(milliseconds)
    }

    /**
     * "mm:ss" 转换成 视频时间
     *
     * @param time
     * @return
     */
    @JvmStatic
    fun formatSeconds(time: String?): Long {
        val format = DATE_FORMAT_MM_SS
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("GMT+0")
        val date: Date
        var times: Long = 0
        try {
            date = sdf.parse(time)
            val l = date.time
            times = l
            LogUtils.d("时间戳", times.toString() + "")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return times
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    @JvmStatic
    fun getDaysByYearMonth(year: Int, month: Int): Int {
        val a = Calendar.getInstance()
        a[Calendar.YEAR] = year
        a[Calendar.MONTH] = month - 1
        a[Calendar.DATE] = 1
        a.roll(Calendar.DATE, -1)
        return a[Calendar.DATE]
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常<br></br>
     */
    @JvmStatic
    fun stringForWeek(strDate: String?): Int {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        c.time = format.parse(strDate)
        return if (c[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY) {
            7
        } else {
            c[Calendar.DAY_OF_WEEK] - 1
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常<br></br>
     */
    @JvmStatic
    fun stringForWeek(strDate: String?, simpleDateFormat: SimpleDateFormat): Int {
        val c = Calendar.getInstance()
        c.time = simpleDateFormat.parse(strDate)
        return if (c[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY) {
            7
        } else {
            c[Calendar.DAY_OF_WEEK] - 1
        }
    }

    /**
     * 根据年份及月份计算每月的天数
     */
    @JvmStatic
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
    @JvmStatic
    fun formatTurnSecond(time: String): Long {
        val index1 = time.indexOf(":")
        val index2 = time.indexOf(":", index1 + 1)
        val hh = time.substring(0, index1)
            .toInt()
            .toLong()
        val mi = time.substring(index1 + 1, index2)
            .toInt()
            .toLong()
        val ss = time.substring(index2 + 1)
            .toInt()
            .toLong()
        return hh * 60 * 60 + mi * 60 + ss
    }

    /**
     * @param time yyyy-MM-dd HH-mm-ss
     * @return
     */
    @JvmStatic
    fun getDayInData(time: String): String {
        if (DataUtils.checkUnNull(time)) {
            val str = time.split(" ".toRegex())
                .toTypedArray()
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
    @JvmStatic
    fun format2Second(time: String): Long {
        if (DataUtils.checkIsNull(time)) {
            return 0
        }
        val str = time.split(":".toRegex())
            .toTypedArray()
        var hh: Long = 0
        var mi: Long = 0
        var ss: Long = 0
        when (str.size) {
            0 -> {
            }

            1 -> hh = str[0].toInt()
                .toLong()

            2 -> {
                hh = str[0].toInt()
                    .toLong()
                mi = str[1].toInt()
                    .toLong()
            }

            else -> {
                hh = str[0].toInt()
                    .toLong()
                mi = str[1].toInt()
                    .toLong()
                ss = str[2].toInt()
                    .toLong()
            }
        }
        return hh * 60 * 60 + mi * 60 + ss
    }

    /**
     * @param time 00:00:00:00
     * @return 000000
     */
    @JvmStatic
    fun format2Millisecond(time: String): Long {
        if (DataUtils.checkIsNull(time)) {
            return 0
        }
        val str = time.split(":".toRegex())
            .toTypedArray()
        var hh: Long = 0
        var mi: Long = 0
        var ss: Long = 0
        var mm: Long = 0
        when (str.size) {
            0 -> {
            }

            1 -> hh = str[0].toInt()
                .toLong()

            2 -> {
                hh = str[0].toInt()
                    .toLong()
                mi = str[1].toInt()
                    .toLong()
            }

            3 -> {
                hh = str[0].toInt()
                    .toLong()
                mi = str[1].toInt()
                    .toLong()
                ss = str[2].toInt()
                    .toLong()
            }

            else -> {
                hh = str[0].toInt()
                    .toLong()
                mi = str[1].toInt()
                    .toLong()
                ss = str[2].toInt()
                    .toLong()
                mm = str[3].toInt()
                    .toLong()
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
    @JvmStatic
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
        return (if (h > 9) "" + h else "0$h") + ":" + (if (d > 9) "" + d else "0$d") + ":" + if (s > 9) "" + s else "0$s"
    }

    /**
     * @param leftsecond 秒
     * @return string
     */
    @JvmStatic
    fun formatTime(leftsecond: Int): String {
        val day: Long = Math.floor(leftsecond / (60 * 60 * 24).toDouble())
            .toLong()
        val hour: Long = Math.floor((leftsecond - day * 24 * 60 * 60) / 3600.toDouble())
            .toLong()
        val minute: Long = Math.floor((leftsecond - day * 24 * 60 * 60 - hour * 3600) / 60.toDouble())
            .toLong()
        return "剩" + day + "天" + hour + "时" + minute + "分"
    }

    /**
     * @param second 秒
     * @return string
     */
    @JvmStatic
    fun formatTime2Second(second: Int, type: Int = 1): String {
        val day: Long = Math.floor(second / (60 * 60 * 24).toDouble())
            .toLong()
        val hour: Long = Math.floor((second - day * 24 * 60 * 60) / 3600.toDouble())
            .toLong()
        val minute: Long = Math.floor((second - day * 24 * 60 * 60 - hour * 3600) / 60.toDouble())
            .toLong()
        val second: Long = Math.floor(second - day * 24 * 60 * 60 - hour * 3600 - (minute * 60).toDouble())
            .toLong()
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
    @JvmStatic
    fun string2Millisecond(date: String?): Long {
        if (DataUtils.checkIsNull(date)) {
            return 0L
        }
        try {
            return defaultFormat.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0L
    }

    @JvmStatic
    fun dateConvert(timeSamp: Long, flag: Int): String {
        var time = timeSamp
        time *= 1000
        val result: String
        val todayCalendar = Calendar.getInstance()
        val otherCalendar = Calendar.getInstance()
        otherCalendar.timeInMillis = time

        val timeFormat = "M月d日"
        val yearTimeFormat = "yyyy年M月d日"

        val yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
        if (yearTemp) {
            val todayMonth = todayCalendar.get(Calendar.MONTH)
            val otherMonth = otherCalendar.get(Calendar.MONTH)
            if (todayMonth == otherMonth) {//表示是同一个月
                when (todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE)) {
                    0 -> result = format2HourAndMin(time)
                    1 -> if (flag == 1) {
                        result = "昨天 "
                    } else {
                        result = "昨天 " + format2HourAndMin(time)
                    }

                    2, 3, 4, 5, 6 -> {
                        val dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH)
                        val todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH)
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            val dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK)
                            if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1]
                            } else {
                                result = formatData2All(time, timeFormat)
                            }
                        } else {
                            result = formatData2All(time, timeFormat)
                        }
                    }

                    else -> result = formatData2All(time, timeFormat)
                }
            } else {
                result = formatData2All(time, timeFormat)
            }
        } else {
            result = formatData2All(time, yearTimeFormat)
        }
        return result
    }

    /**
     * 当天的显示时间格式
     */
    @JvmStatic
    fun format2HourAndMin(time: Long): String {
        val format = SimpleDateFormat("HH:mm")
        return format.format(Date(time))
    }

    /**
     * 格式化成指定格式
     */
    @JvmStatic
    fun formatData2All(time: Long, timeFormat: String): String {
        val format = SimpleDateFormat(timeFormat)
        return format.format(Date(time))
    }

    /**
     *  格式化时间
     * @param data String
     * @return String
     * 1分钟前
     * 1小时前
     * 1天前
     * 01-01
     */
    @JvmStatic
    fun formatData(data: String): String {
        val dateOld = defaultFormat.parse(data)
        val dateNow = Date()
        val result = dateNow.time - dateOld.time
        val minute = result / 1000 / 60
        val hour = minute / 60
        val day = hour / 24
        if (minute < 60) {
            return minute.toString() + "分钟前"
        } else if (hour < 24) {
            return hour.toString() + "小时前"
        } else if (day < 7) {
            return day.toString() + "天前"
        } else {
            return SimpleDateFormat("MM-dd", Locale.getDefault()).format(dateOld)
        }
    }

    /**
     * date2比date1多的天数
     * @param date1 小的日期
     * @param date2 大的日期
     * @return 当天 返回 0
     */
    fun differentDays(date1: Date, date2: Date): Int {
        val cal1: Calendar = Calendar.getInstance()
        cal1.time = date1
        val cal2: Calendar = Calendar.getInstance()
        cal2.time = date2

        val day1: Int = cal1.get(Calendar.DAY_OF_YEAR)
        val day2: Int = cal2.get(Calendar.DAY_OF_YEAR)

        val year1: Int = cal1.get(Calendar.YEAR)
        val year2: Int = cal2.get(Calendar.YEAR)
        return if (year1 != year2) {
            // 不同年
            var timeDistance = 0
            for (i in year1 until year2) {
                timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    366
                } else {
                    //不是闰年
                    365
                }
            }
            timeDistance + (day2 - day1)
        } else {
            //同一年
            println("判断day2 - day1 : " + (day2 - day1))
            day2 - day1
        }
    }

    /**
     * 获取当天的YYYY-MM-dd 00:00:00 时间点的毫秒级时间戳
     *
     * @return
     */
    @JvmStatic
    fun getCurrentTime4Millis1s(): Long {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0 //控制时
        cal[Calendar.MINUTE] = 0 //控制分
        cal[Calendar.SECOND] = 0 //控制秒
        return cal.timeInMillis
    }

    /**
     * 获取今天开始时间
     */
    @JvmStatic
    fun getCurrentBeginDate3(): Date {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0 //控制时
        cal[Calendar.MINUTE] = 0 //控制分
        cal[Calendar.SECOND] = 0 //控制秒
        return cal.time;
    }

    val nowDate4yyyy: String
        get() {
            val dff = SimpleDateFormat("yyyy")
            dff.timeZone = TimeZone.getTimeZone("GMT+08")
            return dff.format(Date())
        }
    val nowDate4Moon: String
        get() {
            val dff = SimpleDateFormat("MM")
            dff.timeZone = TimeZone.getTimeZone("GMT+08")
            return dff.format(Date())
        }


    @JvmStatic
    fun main(args: Array<String>) {
        println(formatData2All(1616743088821, "yyyy-MM-dd HH:mm:ss"))
    }

}