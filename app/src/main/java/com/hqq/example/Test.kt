package com.hqq.example

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example
 * @FileName :   Test
 * @Date  : 2020/8/7 0007  下午 3:00
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class Test {

    class A {
        var aa = 0
    }

    class B {
        var a = A()

        var aa
            get() = a.aa
            set(value) {
                a.aa = value
            }


    }


    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {

            var b = B();


            println(b.aa)
            println(b.a.aa)
            b.a.aa = 3
            println(b.aa)
            println(b.a.aa)
            b.a.aa = 4
            println(b.aa)
            println(b.a.aa)
            b.aa = 6
            println(b.aa)
            println(b.a.aa)


        }
    }

}