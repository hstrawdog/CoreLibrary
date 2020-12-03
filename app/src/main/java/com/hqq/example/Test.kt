package com.hqq.example

import kotlinx.coroutines.*
import java.lang.Thread.sleep

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example
 * @FileName :   Test
 * @Date : 2020/8/27 0027  上午 9:31
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object Test {


    internal interface I {
        val a: A
    }

    open class A {
        final var a = 10
    }

    open class A1 : A() {

    }


    class B : I {
        override val a: A1 = A1()

    }


    @JvmStatic
    fun main(args: Array<String>) {
        var list: List<String> = mutableListOf<String>()
        var a = "1a"
        var a1 = "1a"
        println("1a".equals(a).toString())
        println(("1a" == a).toString())
        println(("1a" === a).toString())
        println("-------------")
        println(a1.equals(a).toString())
        println((a1 == a).toString())
        println((a1 === a).toString())
        println("111111111111111111111111")

        var str: String? = "5"
        str?.run {

            "1"
            println(this + "aaa")
        }


    }


}