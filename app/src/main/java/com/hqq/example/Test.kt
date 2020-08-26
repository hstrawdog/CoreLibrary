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

    interface AS {

    }

    class A1 : AS {


    }

    class A2(a: AS) : AS by a {


    }


    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {


        }
    }

}