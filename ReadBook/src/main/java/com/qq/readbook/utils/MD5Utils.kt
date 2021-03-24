package com.qq.readbook.utils

import com.hqq.core.utils.log.LogUtils.e4Debug
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @version V1.0 <描述当前版本功能>
 * 在此写用途
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.utils.Md5Utils.java
 * @emain: 593979591@qq.com
 * @date: 2020-12-14 22:32
</描述当前版本功能> */
internal object MD5Utils {
    /**
     * md5
     *
     * @param sourceStr
     * @return
     */
    fun getStringMD5(sourceStr: String): String {
        var s: String = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            //这两行代码的作用是：
            // 将bytes数组转换为BigInterger类型。1，表示 +，即正数。
            val bigInt = BigInteger(1, md.digest(sourceStr.toByteArray()))
            // 通过format方法，获取32位的十六进制的字符串。032,代表高位补0 32位，X代表十六进制的整形数据。
            //为什么是32位？因为MD5算法返回的时一个128bit的整数，我们习惯于用16进制来表示，那就是32位。
            s = String.format("%032x", bigInt)
            e4Debug("加密 $sourceStr")
            e4Debug("结果 $s")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return s
    }
}