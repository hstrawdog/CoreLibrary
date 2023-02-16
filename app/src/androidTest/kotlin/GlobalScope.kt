import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package :
 * @Date  : 00:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */


/**
 * date2比date1多的天数
 * @param date1
 * @param date2
 * @return
 */
private fun differentDays(date1:Date, date2:Date):Int {
    val cal1:Calendar = Calendar.getInstance()
    cal1.setTime(date1)
    val cal2:Calendar = Calendar.getInstance()
    cal2.setTime(date2)
    val day1:Int = cal1.get(Calendar.DAY_OF_YEAR)
    val day2:Int = cal2.get(Calendar.DAY_OF_YEAR)
    val year1:Int = cal1.get(Calendar.YEAR)
    val year2:Int = cal2.get(Calendar.YEAR)
    return if (year1 != year2) {
        // 不同年
        var timeDistance = 0
        for (i in year1 until year2) {
            timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                //闰年
                366
            } else {
                //不是闰年
                365
            }
        }
        timeDistance + (day2 - day1)
    } else {
        //同一年
        println("判断day2 - day1 : " + (day2 - day1))
        day2 - day1
    }
}


fun main() {

    println(differentDays(SimpleDateFormat("yyyy-MM-dd").parse("2023-2-11"), Date()))

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


fun fun1(num:Int):String {

    return "fun$num"
}
