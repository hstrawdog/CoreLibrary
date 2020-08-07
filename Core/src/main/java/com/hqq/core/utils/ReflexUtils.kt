package com.hqq.core.utils

import java.lang.reflect.ParameterizedType

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   ReflexUtils
 * @Date : 2020/7/24 0024  下午 3:32
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
internal object ReflexUtils {
    fun getT4Class(o: Any): Class<*>? {
        val type = o.javaClass.genericSuperclass
        return if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else null
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("args = " + getT4Class(A<String>())!!.name)
        println("args = " + getT4Class(A<Int>())!!.name)
    }

    internal class A<T>
}