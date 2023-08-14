import com.hqq.core.utils.TimeUtils
import java.text.SimpleDateFormat
import java.util.Date



fun main() {
    println(TimeUtils.differentDays(SimpleDateFormat("yyyy-MM-dd").parse("2022-7-18"), Date()))

//    GlobalScope.launch { // 在后台启动一个新协程，并继续执行之后的代码
//        delay(1000L) // 非阻塞式地延迟一秒
//        println("World!") // 延迟结束后打印
//    }
//    println("Hello,") //主线程继续执行，不受协程 delay 所影响
//    Thread.sleep(2000L) // 主线程阻塞式睡眠2秒，以此来保证JVM存活

//    runBlocking {
//        println("1")
//        var fun1 = async {
//            println("fun1-1")
//            delay(1000)
//            fun1(1)
//            println("fun1-2")
//        }
//        println("2")
//
//        var fun2 = async {
//            println("fun2-1")
//            delay(500)
//            fun1(2)
//            println("fun2-2")
//        }
//        println("3")
//
//
//        println("    ${fun1.await()}          ${fun2.await()}")
//        println("4")
//
//    }


}


fun fun1(num: Int): String {

    return "fun$num"
}
