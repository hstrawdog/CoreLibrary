import com.easy.example.utils.ColorUtil
import org.junit.Test

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example
 * @FileName :   Test
 * @Date : 2020/8/27 0027  上午 9:31
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class Test {

    @Test
    fun mains() {

        var a13 = floatArrayOf(15787.31f,
            2270.7f,
            4393.35f,
            2278f,
            1157f,
            1632f,
            4605.4f,
            2642.16f,
            7283.82f,
            9491.72f,
            19715.75f,
            15146.16f,
            10746.2f,
            2466.62f,
            32935f,
            107529f)

        var sum = 57714.6f

        println("   开始总 ${a13.size}")


        for ((p, fl) in a13.withIndex()) {
            println("    执行第$p 个       $fl       ")
            temp(a13, fl, 0, "  $p   $fl ", sum)

        }


        println(" ${com.easy.example.utils.ColorUtil.getRanDomColor()} ")
        println(" ${com.easy.example.utils.ColorUtil.getRanDomColor()} ")
        println(" ${com.easy.example.utils.ColorUtil.getRanDomColor()} ")
        println(" ${com.easy.example.utils.ColorUtil.getRanDomColor()} ")

    }


    private fun temp(a13: FloatArray, f1: Float, p: Int, str: String, sum: Float) {
        if (p < a13.size) {
            for (position in p until a13.size) {
                var f2 = a13[position]
                var te = f1 + f2
                if (te == sum) {
                    println("$str                  累加$f1   $position    $f2        总 ${te} ")
                }
                var newStr = "$str   $position  $f2  "
                temp(a13, te, position + 1, newStr, sum)
            }

        }


    }


}