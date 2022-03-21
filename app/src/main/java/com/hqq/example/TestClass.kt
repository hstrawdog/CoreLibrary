package com.hqq.example

import com.hqq.core.kt.getFileName
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example
 * @Date : 10:08
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object TestClass {
    @JvmStatic
    fun main(args: Array<String>) {

        println("".getFileName())
        println("1".getFileName())
        println("1/1.png".getFileName())
        println("1/.png".getFileName())
    }
}