package com.hqq.core.utils

import com.hqq.core.utils.log.LogUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @Author : huangqiqiang
 * @Package : com...utils
 * @FileName :   MathUtils
 * @Date : 2019/4/1 0001  下午 2:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object MathUtils {
    /**
     * double  四舍五入 向上取整
     *
     * @param object
     * @return
     */
    fun double2int(`object`: Any): String {
        if (`object` is Double) {
            return BigDecimal(`object`).setScale(0, BigDecimal.ROUND_HALF_UP).toString()
        } else if (`object` is String) {
            return BigDecimal(`object`).setScale(0, BigDecimal.ROUND_HALF_UP).toString()
        }
        return `object`.toString()
    }

    /**
     * 同上
     *
     * @param number
     * @param roundingMode
     * @return
     */
    fun formatDecimals(number: Double, roundingMode: RoundingMode?): String {
        val numberStr: String
        if (number.toLong() * 1000 == (number * 1000) as Long) {
            //如果是一个整数
            numberStr = number as Long.toString()
        } else {
            val df = DecimalFormat("######0.00")
            df.roundingMode = roundingMode
            numberStr = df.format(number)
        }
        return numberStr
    }

    /**
     * 目前只支持
     * 格式化小数点
     * 0.00   0
     * 0.1    0.10
     * 0.110  0.11
     *
     * @param o
     * @return
     */
    fun formatDecimals(o: Any): String {
        if (o is Double) {
            return formatDecimals(o, RoundingMode.FLOOR)
        } else if (o is String) {
            try {
                return formatDecimals(java.lang.Double.valueOf(o.toString()), RoundingMode.FLOOR)
            } catch (e: Exception) {
                LogUtils.e(" formatDecimals $e")
            }
        }
        return o.toString()
    }

    /**
     * 如果是小数，保留两位，非小数，保留整数
     * 默认是不支持四舍五入
     *
     * @param number
     * @return
     */
    fun getDoubleString(number: Double): String {
        return getDoubleString(number, RoundingMode.FLOOR)
    }

    /**
     * 同上
     *
     * @param number
     * @return
     */
    fun getDoubleString(number: String): String {
        return getDoubleString(number.toDouble())
    }

    /**
     * 同上
     *
     * @param number
     * @param roundingMode
     * @return
     */
    fun getDoubleString(number: Double, roundingMode: RoundingMode?): String {
        var numberStr = number.toString() + ""
        try {
            if (number.toLong() * 1000 == (number * 1000) as Long) {
                //如果是一个整数
                numberStr = number as Long.toString()
            } else {
                val df = DecimalFormat("######0.00")
                df.roundingMode = roundingMode
                numberStr = df.format(number)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return numberStr
    }

    @JvmStatic
    fun main(args: Array<String>) {
//        System.out.println(Double.parseDouble("100495") / 1000.00);
//        System.out.println(MathUtils.getDoubleString(Double.parseDouble("100495") / 1000.00));

//        System.out.println("args = [" + formatDecimals(0.0) + "]");
//        System.out.println("args = [" + formatDecimals(1.0) + "]");
//        System.out.println("args = [" + formatDecimals(1.1) + "]");
//        System.out.println("args = [" + formatDecimals("0.001") + "]");
        println("args = [" + formatDecimals("0.110") + "]")
    }
}