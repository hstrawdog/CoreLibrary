package com.easy.core.utils.log

import android.content.Context
import android.util.Log
import com.easy.core.CoreConfig
import com.easy.core.utils.TimeUtils
import com.easy.core.utils.file.FileUtils
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils.log
 * @FileName :   LogUtils
 * @Date : 2019/3/13 0013
 * @Email :  qiqiang213@gmail.com
 * @Describe :    logcat 打印的长度  4*1024  这边用的 4*1000
 */
object LogUtils {
    /**
     * Android Logcat 限制大约 4076 bytes，不是字符数。
     */
    private const val LOG_MAX_LENGTH = 4000

    /**
     * Log 输出标签
     */
    var TAG = "com.easy.core"

    @JvmStatic
    private val LOG_FORMAT = SimpleDateFormat("yyyy年MM月dd日_HH点mm分ss秒") // 日志的输出格式

    @JvmStatic
    private val FILE_SUFFIX = SimpleDateFormat("HH点mm分ss秒") // 日志文件格式

    @JvmStatic
    private val FILE_DIR = SimpleDateFormat("yyyy年MM月dd日") // 日志文件格式

    @JvmStatic
    private var LOG_FILE_PATH // 日志文件保存路径
            :String? = null

    @JvmStatic
    private var LOG_FILE_NAME // 日志文件保存名称
            :String? = null

    private const val LOG_SAVE_DAYS = 7 // sd卡中日志文件的最多保存天数

    @JvmStatic
    fun init(context:Context) { // 在Application中初始化
        LOG_FILE_PATH = FileUtils.rootPath!!.path + File.separator + context.packageName + File.separator + "Log"
        LOG_FILE_NAME = "TLog_"
    }

    /**
     *  需要将其他的包含if (CoreConfig.get().isDebug)方法 都改造成如此的
     * @param call Function0<Any>
     */
    fun v(block:() -> Any?) {
        if (!CoreConfig.get().isDebug) return
        val msg = block() ?: "标签 $TAG 的打印内容为空！"
        doLog("v", TAG, msg)
    }

    /**
     * DEBUG 类型日志 - lambda 版本
     */
    @JvmStatic
    fun d(block:() -> Any?) {
        logInternal("d", TAG, block)
    }

    @JvmStatic
    fun d(tag:String, block:() -> Any?) {
        logInternal("d", tag, block)
    }

    /**
     * INFO 类型日志 - lambda 版本
     */
    @JvmStatic
    fun i(block:() -> Any?) {
        logInternal("i", TAG, block)
    }

    @JvmStatic
    fun i(tag:String = TAG, block:() -> Any?) {
        logInternal("i", tag, block)
    }

    /**
     * WARN 类型日志 - lambda 版本
     */
    @JvmStatic
    fun w(block:() -> Any?) {
        logInternal("w", TAG, block)
    }

    /**
     * ERROR 类型日志 - lambda 版本
     */
    @JvmStatic
    fun e(block:() -> Any?) {
        logInternal("e", TAG, block)
    }

    @JvmStatic
    fun e(tag:String, block:() -> Any?) {
        logInternal("e", tag, block)
    }

    /**
     * 标记一个 d - lambda 版本
     */
    fun dMark(tag:String = "Mark", block:() -> Any?) {
        logInternal("d", tag, block)
    }


    fun dInfo(block:() -> Any?, lever:String = "w") {
        var tag = "Info"
        if (CoreConfig.get().isDebug) {
            logInternal(lever, tag) {
                "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────"
            }
            block.invoke()?.let {
                logInternal(lever, tag) {
                    it
                }
            } ?: {
                logInternal(lever, tag) {
                    "标签 : 内容为空！"
                }
            }
            logInternal(lever, tag) {
                "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
            }
            val stackTrace = Throwable().stackTrace
            if (stackTrace.size > 1) {
                for (index in 1 until Math.min(10, stackTrace.size)) {
                    val targetElement = stackTrace[index]
                    val head = "${Thread.currentThread().name}  |      ${targetElement.getClassName()}.${targetElement.getMethodName()}(${
                        getFileName(targetElement)
                    }:${targetElement.getLineNumber()})            "


                    logInternal(lever, tag) {
                        "|      $head     "
                    }
                }
            }

            logInternal(lever, tag) {
                "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────"
            }
        }
    }


    @Deprecated("请使用 v { } 形式，支持延迟执行")
    @JvmStatic
    fun v(any:Any?) {
        v { any }
    }

    /**
     * DEBUG 类型日志
     *œ
     * @param object
     */
    @JvmStatic
    fun d(any:Any?) {
        d(TAG, any)
    }

    /**
     * DEBUG 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    @JvmStatic
    fun d(tag:String, any:Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                d("标签" + tag + "的打印内容为空！")
            } else {
                doLog("d", tag, any)
            }
        }
    }

    @JvmStatic
    fun i(any:Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("i", TAG, any)
            }
        }
    }

    @JvmStatic
    fun i(tag:String = TAG, any:Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("i", TAG, any)
            }
        }
    }

    @JvmStatic
    fun w(any:Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                doLog("w", TAG, "标签" + TAG + "的打印内容为空！")
            } else {
                doLog("w", TAG, any)
            }
        }
    }

    /**
     * ERROR 类型日志
     *
     * @param object
     */
    @JvmStatic
    fun e(any:Any?) {
        e(TAG, any)
    }

    /**
     * E 类型错误日志
     */
    @JvmStatic
    fun e(exception:Exception?) {
        if (CoreConfig.get().isDebug) {
            Log.e(TAG, TAG, exception)
        }
    }

    /**
     * E 类型日志
     */
    @JvmStatic
    fun e(tag:String, any:Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                e("标签 : $tag 的打印内容为空！")
            } else {
                doLog("e", tag, any)
            }

        }
    }

    /**
     *   标记一个 d
     * @param tag String
     * @param any Any?
     */
    fun dMark(tag:String = "Mark", any:Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                dInfo(tag)
            } else {
                d(tag, any)
            }
        }
    }

    /**
     *   打一个日志标记
     * @param any Any?
     */
    @JvmStatic
    fun e4Mark(any:Any?) {
        if (CoreConfig.get().isDebug) {
            e("$TAG", any)
        }
    }

    /**
     * E 类型日志
     */
    @JvmStatic
    fun dInfo(any:Any?) {
        var tag = "Info"
        if (CoreConfig.get().isDebug) {
            doLog("w", tag, "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
            if (any == null) {
                e(tag, "标签 : 内容为空！")
            } else {
                doLog("w", tag, any)
            }
            doLog("w", tag, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄")
            val stackTrace = Throwable().stackTrace
            if (stackTrace.size > 1) {
                for (index in 1 until Math.min(10, stackTrace.size)) {
                    val targetElement = stackTrace[index]
                    val head = "${Thread.currentThread().name}  |      ${targetElement.getClassName()}.${targetElement.getMethodName()}(${
                        getFileName(targetElement)
                    }:${targetElement.getLineNumber()})            "
                    doLog("w", tag, "|      $head     ")
                }
            }
            doLog("w", tag, "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
        }
    }

    /**
     * 以 类名 为 tag
     * @param any Any?
     */
    fun de(any:Any?) {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size > 1) {
            val targetElement = stackTrace[0]
            e(targetElement.fileName, any)
        } else {
            e(any)
        }
    }

    /**
     * 核心统一日志方法，供 lambda 版本调用
     */
    private inline fun logInternal(level:String, tag:String = TAG, block:() -> Any?) {
        if (!CoreConfig.get().isDebug) return
        val msg = block() ?: "标签 $tag 的打印内容为空！"
        doLog(level, tag, msg)
    }

    /**
     *  判断日志是否超过长度
     */
    @JvmStatic
    private fun doLog(level:String, tag:String, any:Any) {
        val log = any.toString().trim()
        if (log.toByteArray(Charsets.UTF_8).size > LOG_MAX_LENGTH) {
            doLog4Length(level, tag, log)
        } else {
            printLog(level, tag, log)
        }
    }

    /**
     *  处理超过限制长度的日志
     */
    @JvmStatic
    private fun doLog4Length(level:String, tag:String, log:String) {
        val bytes = log.toByteArray(Charsets.UTF_8)
        val maxBytes = LOG_MAX_LENGTH // Android Logcat 限制，取安全值
        var start = 0
        while (start < bytes.size) {
            var end = (start + maxBytes).coerceAtMost(bytes.size)

            // 确保不截断 UTF-8 多字节字符
            while (end > start && (bytes[end - 1].toInt() and 0xC0) == 0x80) {
                end--
            }

            val chunk = bytes.copyOfRange(start, end).toString(Charsets.UTF_8)
            printLog(level, tag, chunk)

            start = end
        }
    }

    /**
     *  打印日志
     */
    @JvmStatic
    private fun printLog(level:String, tag:String, msg:String) {
        when (level) {
            "e" -> {
                Log.e(tag, msg)
            }

            "d" -> {
                Log.d(tag, msg)
            }

            "w" -> {
                Log.w(tag, msg)
            }

            "v" -> {
                Log.v(tag, msg)
            }

            "i" -> {
                Log.i(tag, msg)

            }

        }

    }

    /**
     *  获取类文件名
     * @param targetElement StackTraceElement
     * @return String
     */
    @JvmStatic
    private fun getFileName(targetElement:StackTraceElement):String {
        val fileName = targetElement.fileName
        if (fileName != null) return fileName
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        var className = targetElement.className
        val classNameInfo = className.split("\\.".toRegex()).toTypedArray()
        if (classNameInfo.size > 0) {
            className = classNameInfo[classNameInfo.size - 1]
        }
        val index = className.indexOf('$')
        if (index != -1) {
            className = className.substring(0, index)
        }
        return "$className.java"
    }

    //region  将日子 写入文件中
    @Synchronized
    private fun log2File(mylogtype:String, tag:String, text:String):File {
        val nowtime = Date()
        val date = FILE_SUFFIX.format(nowtime)
        val dateLogContent = """
            Date:${LOG_FORMAT.format(nowtime)}
            LogType:$mylogtype
            Tag:$tag
            Content:
            $text
            """.trimIndent() // 日志输出格式
        val path = LOG_FILE_PATH + File.separator + FILE_DIR.format(nowtime)
        val destDir = File(path)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }
        val file = File(path, "${LOG_FILE_NAME}$date.txt")
        try {
            if (!file.exists()) {
                file.createNewFile()
            }
            val filerWriter = FileWriter(file, true)
            val bufWriter = BufferedWriter(filerWriter)
            bufWriter.write(dateLogContent)
            bufWriter.newLine()
            bufWriter.close()
            filerWriter.close()
        } catch (e:IOException) {
            e.printStackTrace()
        }
        return file
    }

    /**
     * 删除指定的日志文件
     */
    @JvmStatic
    fun delAllLogFile() { // 删除日志文件
//        String needDelFiel = FILE_SUFFIX.format(getDateBefore());
        LOG_FILE_PATH?.let { FileUtils.deleteDir(it) }
    }

    /**
     * 得到LOG_SAVE_DAYS天前的日期
     *
     * @return
     */
    @JvmStatic
    private val dateBefore:Date
        private get() {
            val nowtime = Date()
            val now = Calendar.getInstance()
            now.time = nowtime
            now[Calendar.DATE] = now[Calendar.DATE] - LOG_SAVE_DAYS
            return now.time
        }

    @JvmStatic
    fun saveLogFile(message:String) {
        val fileDir = File(FileUtils.rootPath.toString() + File.separator + CoreConfig.applicationContext.packageName)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val file = File(fileDir, TimeUtils.formatDate("yyyyMMdd") + ".txt")
        try {
            if (file.exists()) {
                val ps = PrintStream(FileOutputStream(file, true))
                ps.append("""
    ${TimeUtils.formatDate("\n\n\nyyyy-MM-dd HH:mm:ss")}
    $message
    """.trimIndent()) // 往文件里写入字符串
            } else {
                val ps = PrintStream(FileOutputStream(file))
                file.createNewFile()
                ps.println("""
    ${TimeUtils.formatDate("yyyy-MM-dd HH:mm:ss")}
    $message
    """.trimIndent()) // 往文件里写入字符串
            }
        } catch (e:IOException) {
            e.printStackTrace()
        }
    }
    //endregion

}
