package com.hqq.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Author : huangqiqiang
 * @Package : com.shangwenwan.sww.utils
 * @FileName :   MathUtils
 * @Date : 2019/4/1 0001  下午 2:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class MathUtils {

    /**
     * double  四舍五入 向上取整
     *
     * @param doubles
     * @return
     */
    public static String double2int(String doubles) {
        return new BigDecimal(doubles).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 同上
     *
     * @param doubles
     * @return
     */
    public static String double2int(double doubles) {
        return new BigDecimal(doubles).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 格式化 科学计数法
     *
     * @param doubles
     * @return
     */
    public static String formatDouble(double doubles) {
        return getDoubleString(Double.valueOf(new BigDecimal(doubles).toString()));
    }

    /**
     * 如果是小数，保留两位，非小数，保留整数
     * 默认是不开启四舍五入
     *
     * @param number
     * @return
     */

    public static String getDoubleString(double number) {
        return getDoubleString(number, RoundingMode.FLOOR);
    }

    /**
     * 同上
     *
     * @param number
     * @param roundingMode
     * @return
     */
    public static String getDoubleString(double number, RoundingMode roundingMode) {
        String numberStr;
        if (((long) number * 1000) == (long) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((long) number);
        } else {
            DecimalFormat df = new DecimalFormat("######0.00");
            df.setRoundingMode(roundingMode);
            numberStr = df.format(number);
        }
        return numberStr;
    }


    public static void main(String[] args) {
//        System.out.println(getDoubleString(9999970.00));
//        System.out.println(getDoubleString(0.001));
//        System.out.println(getDoubleString(0.01));
//        System.out.println(getDoubleString(12.00));
        System.out.println(Double.parseDouble("100495") / 1000.00);
        System.out.println(MathUtils.getDoubleString(Double.parseDouble("100495") / 1000.00));

    }
}
