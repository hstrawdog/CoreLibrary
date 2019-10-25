package com.hqq.example.utils;

import com.hqq.example.utils.log.LogUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Author : huangqiqiang
 * @Package : com...utils
 * @FileName :   MathUtils
 * @Date : 2019/4/1 0001  下午 2:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class MathUtils {

    /**
     * double  四舍五入 向上取整
     *
     * @param object
     * @return
     */
    public static String double2int(Object object) {
        if (object instanceof Double) {
            return new BigDecimal((Double) object).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        } else if (object instanceof String) {
            return new BigDecimal((String) object).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        }
        return object.toString();

    }

    /**
     * 同上
     *
     * @param number
     * @param roundingMode
     * @return
     */
    public static String formatDecimals(double number, RoundingMode roundingMode) {
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
    public static String formatDecimals(Object o) {
        if (o instanceof Double) {
            return formatDecimals((Double) o, RoundingMode.FLOOR);
        } else if (o instanceof String) {
            try {
                return formatDecimals(Double.valueOf(o.toString()), RoundingMode.FLOOR);
            } catch (Exception e) {
                LogUtils.e(" formatDecimals " + e.toString());
            }
        }
        return o.toString();
    }


    public static void main(String[] args) {
//        System.out.println(Double.parseDouble("100495") / 1000.00);
//        System.out.println(MathUtils.getDoubleString(Double.parseDouble("100495") / 1000.00));

//        System.out.println("args = [" + formatDecimals(0.0) + "]");
//        System.out.println("args = [" + formatDecimals(1.0) + "]");
//        System.out.println("args = [" + formatDecimals(1.1) + "]");
//        System.out.println("args = [" + formatDecimals("0.001") + "]");
        System.out.println("args = [" + formatDecimals("0.110") + "]");


    }
}
