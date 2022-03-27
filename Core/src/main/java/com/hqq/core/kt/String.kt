package com.hqq.core.kt

import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.kt
 * @Date  : 11:21
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
/**
 *  非空判断
 * @receiver String
 * @return Boolean
 */
fun String.isNotNull(): Boolean {
    return !isNullOrEmpty()
}

/**
 *  获取文件名称
 *   未实现 文件路径判断
 * @receiver String
 */
fun String.getFileName(): String? {
    if (isNotNull()) {
        val file = File(this)
        return file.name
    }

    return  null
}



