package com.hqq.core.widget

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.widget
 * @Date  : 下午 5:33
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class A {


    companion object {
        /** 我是main入口函数 **/
        @JvmName("main1")
        @JvmStatic
        fun main(args: Array<String>) {
            println("" + "11".trim {
                it <= ' '
            })

            println("" + "1 1".trim {
                it <= ' '
            })

            println("" + "1 1 ".trim {
                it <= ' '
            })

        }
    }


    fun main(args: Array<String>) {



    }

}