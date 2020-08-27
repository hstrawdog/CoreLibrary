package com.hqq.example

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

    }


}