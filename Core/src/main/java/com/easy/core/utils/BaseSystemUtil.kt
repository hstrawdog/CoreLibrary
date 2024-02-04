package com.rongji.core.utils

import android.os.Build

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.utils
 * @Date : 10:20
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object  BaseSystemUtil {

    //是否是荣耀设备
    fun isHonorDevice() = Build.MANUFACTURER.equals("HONOR", ignoreCase = true)

    //是否是小米设备
    fun isXiaomiDevice() = Build.MANUFACTURER.equals("Xiaomi", ignoreCase = true)

    //是否是oppo设备
    //realme 是oppo的海外品牌后面脱离了；一加是oppo的独立运营品牌。因此判断
    //它们是需要单独判断
    fun isOppoDevice() = Build.MANUFACTURER.equals("OPPO", ignoreCase = true)

    //是否是一加手机
    fun isOnePlusDevice() = Build.MANUFACTURER.equals("OnePlus", ignoreCase = true)

    //是否是realme手机
    fun isRealmeDevice() = Build.MANUFACTURER.equals("realme", ignoreCase = true)

    //是否是vivo设备
    fun isVivoDevice() = Build.MANUFACTURER.equals("vivo", ignoreCase = true)

    //是否是华为设备
    fun isHuaweiDevice() = Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true)

    /**
     * 华为系列
     * @return Boolean
     */
     fun isHuaWeiSeriesDevice() = (isHuaweiDevice() || isHonorDevice())


}