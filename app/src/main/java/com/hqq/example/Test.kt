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
//        var list: List<String> = mutableListOf<String>()
//        var a = "1a"
//        var a1 = "1a"
//        println("1a".equals(a).toString())
//        println(("1a" == a).toString())
//        println(("1a" === a).toString())
//        println("-------------")
//        println(a1.equals(a).toString())
//        println((a1 == a).toString())
//        println((a1 === a).toString())
//        println("111111111111111111111111")
//
//        var str: String? = "5"
//        str?.run {
//
//            "1"
//            println(this + "aaa")
//        }


//        var a13 = floatArrayOf(
//                53988f,//0
//                15752.6f,//1
//                955f,//2
//                1429.5f,//3
//                655f,//4
//                2128f,//5
//                888f,//6
//                14807.6f,//7
//                4269f,//8
//                882f,//9
//                4950f,//10
//                1500f,//11
//                1200f,//12
//                450f,//13
//                150f,//14
//                342f,//15
//                507f,//16
//                1290f,//17
//                7566.06f,//18
//                11738.21f,//19
//                7036f,//20
//                19481.52f,//21
//                19354.37f,//22
//                19179.64f,//23
//                18598.78f,//24
//                17967f,//25
//                4090f,//26
//                14378.9f,//27
//                14600.55f,//28
//        )

//        var a13 = floatArrayOf(
//                53988f,
//                15752.6f,
//                1429.5f,
//                2128f,
//                14807.6f,
//                4269f,
//                342f,
//                1290f,
//                7566.06f,
//                11738.21f,
//                19481.52f,
//                19354.37f,
//                19179.64f,
//                18598.78f,
//                17967f,
//                4090f,
//                14378.9f,
//                14600.55f,
//                2212f,
//                2268f,
//                1148f,
//                3780f,
//                )
//        var a13 = floatArrayOf(
//                53988f,
//                15752.6f,
//                1429.5f,
//                655f,
//                14807.6f,
//                882f,
//                4950f,
//                1200f,
//                342f,
//                7566.06f,
//                11738.21f,
//                19481.52f,
//                19354.37f,
//                19179.64f,
//                18598.78f,
//                17967f,
//                4090f,
//                14378.9f,
//                14600.55f,
//                2212f,
//                2268f,
//                1148f,
//                3780f,
//
//
//                )

        var a13 = floatArrayOf(
                53988.00f,
                15752.60f,
                1429.50f,
                2128.00f,
                14807.60f,
                4269.00f,
                342.00f,
                1290.00f,
                7566.06f,
                11738.21f,
                19481.52f,
                19354.37f,
                19179.64f,
                18598.78f,
                17967.00f,
                4090.00f,
                14378.90f,
                14600.55f,
        )

        var sum = 67559.8f

        println("   开始总 ${a13.size}")


        for ((p, fl) in a13.withIndex()) {
            println("    执行第$p 个       $fl       ")
            temp(a13, fl, 0, "  $p   $fl ", sum)

        }
    }




    private fun temp(a13: FloatArray, f1: Float, p: Int, str: String, sum: Float) {


        if (p < a13.size) {
            for (position in p..a13.size - 1) {
                var f2 = a13[position]
                var te = f1 + f2
                if (te == sum) {
                    println("$str                  累加$f1   $position    $f2        总 ${te} ")
                }

                var newStr = str + "   $position  $f2  "

                temp(a13, te, position + 1, newStr, sum)
            }

        }


    }


}