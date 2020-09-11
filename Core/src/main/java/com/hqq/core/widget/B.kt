package com.hqq.core.widget

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.widget
 * @Date : 下午 5:38
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object B {
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
        println("" + " 1 2 ".trim {
            it <= ' '
        })

        println("" + "21 2 1".trim {
            it <= '1'
        })
        println("" + " 3 1 2 3".trim {
            it <= '3'
        })


        println(" 1 1 1 1".trim())
    }

}