import com.hqq.core.utils.log.LogUtils
import kotlinx.coroutines.*

/**
 * @Author : huangqiqiang
 * @Package :
 * @Date  : 00:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun main() {
//    GlobalScope.launch { // 在后台启动一个新协程，并继续执行之后的代码
//        delay(1000L) // 非阻塞式地延迟一秒
//        println("World!") // 延迟结束后打印
//    }
//    println("Hello,") //主线程继续执行，不受协程 delay 所影响
//    Thread.sleep(2000L) // 主线程阻塞式睡眠2秒，以此来保证JVM存活


    runBlocking {


        println("---------   a1 ")
        var a1 = async {
            println("---------   a2 ")
            delay(500)
            println("---------   a3 ")
        }
        println("---------   a4 ")
        println("---------   b1 ")
        var a2 = async {
            println("---------   b2 ")
            delay(500)
            println("---------   b3 ")

        }
        println("---------   b4 ")
        println("---------   c1 ")
        var a3 = async {
            println("---------   c2 ")
            delay(500)
            println("---------   c3 ")
        }
        println("---------   c4 ")

        a1.await()
        a2.await()
        a3.await()
        println("---------   d1 ")

    }

}




