package com.easy.core.utils.log

import android.content.Context
import android.util.Log
import com.easy.core.BuildConfig
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

    //规定每段显示的长度
    private const val LOG_MAX_LENGTH = 2000

    /**
     * Log 输出标签
     */
    var TAG = BuildConfig.LIBRARY_PACKAGE_NAME

    @JvmStatic
    fun v(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                v("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("v", TAG, any)
            }
        }
    }

    /**
     * DEBUG 类型日志
     *œ
     * @param object
     */
    @JvmStatic
    fun d(any: Any?) {
        d(TAG, any)
    }

    /**
     * DEBUG 类型日志
     *
     * @param tag    日志标识
     * @param object
     */
    @JvmStatic
    fun d(tag: String, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                d("标签" + tag + "的打印内容为空！")
            } else {
                doLog("d", tag, any)
            }
        }
    }

    /**
     * E 类型日志
     */
    @JvmStatic
    fun dInfo(any: Any?) {
        var tag = "Info"
        if (CoreConfig.get().isDebug) {
            doLog(tag, "w", "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
            if (any == null) {
                e("标签 : 内容为空！")
            } else {
                doLog("w", tag, any)
            }
            doLog(tag, "w", "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄")
            val stackTrace = Throwable().stackTrace
            if (stackTrace.size > 1) {
                for (index in 1 until Math.min(10, stackTrace.size)) {
                    val targetElement = stackTrace[index]
                    val head =
                        "${Thread.currentThread().name}  |      ${targetElement.getClassName()}.${targetElement.getMethodName()}(${
                            getFileName(targetElement)
                        }:${targetElement.getLineNumber()})            "
                    doLog("w", tag, "|      $head     ")
                }
            }
            doLog(tag, "w", "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────")
        }
    }

    @JvmStatic
    fun e4Debug(any: Any?) {
        if (CoreConfig.get().isDebug) {
            e("$TAG", any)
        }
    }

    @JvmStatic
    fun i(any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("i", TAG, any)
            }
        }
    }

    @JvmStatic
    fun i(tag: String = TAG, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                i("标签" + TAG + "的打印内容为空！")
            } else {
                doLog("i", TAG, any)
            }
        }
    }

    @JvmStatic
    fun w(any: Any?) {
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
    fun e(any: Any?) {
        e(TAG, any)
    }

    /**
     * E 类型错误日志
     */
    @JvmStatic
    fun e(exception: Exception?) {
        if (CoreConfig.get().isDebug) {
            Log.e(TAG, TAG, exception)
        }
    }

    /**
     * E 类型日志
     */
    @JvmStatic
    fun e(tag: String, any: Any?) {
        if (CoreConfig.get().isDebug) {
            if (any == null) {
                e("标签 : $tag 的打印内容为空！")
            } else {
                doLog("e", tag, any)
            }

        }
    }

    /**
     * 以 类名 为 tag
     * @param any Any?
     */
    fun de(any: Any?) {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size > 1) {
            val targetElement = stackTrace[0]
            e(targetElement.fileName, any)
        } else {
            e(any)
        }
    }

    /**
     *  判断日志是否超过长度
     */
    @JvmStatic
    private fun doLog(level: String, tag: String, any: Any) {
        val log = any.toString()
            .trim()
        if (log.length > LOG_MAX_LENGTH) {
            doLog4Length(level, tag, log)
        } else {
            printLog(level, tag, log)
        }
    }

    /**
     *  处理超过限制长度的日志
     */
    @JvmStatic
    private fun doLog4Length(level: String, str: String, log: String) {
        val strLength: Int = log.length
        var start = 0
        var end: Int = LOG_MAX_LENGTH
        for (i in 0..99) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                printLog(level, str + i, log.substring(start, end))
                start = end
                end += LOG_MAX_LENGTH
            } else {
                printLog(level, str + i, log.substring(start, strLength))
                break
            }
        }
    }

    /**
     *  打印日志
     */
    @JvmStatic
    private fun printLog(level: String, tag: String, msg: String) {
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
    private fun getFileName(targetElement: StackTraceElement): String {
        val fileName = targetElement.fileName
        if (fileName != null) return fileName
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        var className = targetElement.className
        val classNameInfo = className.split("\\.".toRegex())
            .toTypedArray()
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
    @JvmStatic
    private val LOG_FORMAT = SimpleDateFormat("yyyy年MM月dd日_HH点mm分ss秒") // 日志的输出格式

    @JvmStatic
    private val FILE_SUFFIX = SimpleDateFormat("HH点mm分ss秒") // 日志文件格式

    @JvmStatic
    private val FILE_DIR = SimpleDateFormat("yyyy年MM月dd日") // 日志文件格式

    @JvmStatic
    private var LOG_FILE_PATH // 日志文件保存路径
            : String? = null

    @JvmStatic
    private var LOG_FILE_NAME // 日志文件保存名称
            : String? = null

    private const val LOG_SAVE_DAYS = 7 // sd卡中日志文件的最多保存天数

    @JvmStatic
    fun init(context: Context) { // 在Application中初始化
        LOG_FILE_PATH = FileUtils.rootPath!!.path + File.separator + context.packageName + File.separator + "Log"
        LOG_FILE_NAME = "TLog_"
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     */
    @Synchronized
    private fun log2File(mylogtype: String, tag: String, text: String): File {
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
        } catch (e: IOException) {
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
    private val dateBefore: Date
        private get() {
            val nowtime = Date()
            val now = Calendar.getInstance()
            now.time = nowtime
            now[Calendar.DATE] = now[Calendar.DATE] - LOG_SAVE_DAYS
            return now.time
        }

    @JvmStatic
    fun saveLogFile(message: String) {
        val fileDir = File(FileUtils.rootPath.toString() + File.separator + CoreConfig.applicationContext.packageName)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val file = File(fileDir, TimeUtils.getCurrentDateTime("yyyyMMdd") + ".txt")
        try {
            if (file.exists()) {
                val ps = PrintStream(FileOutputStream(file, true))
                ps.append("""
    ${TimeUtils.getCurrentDateTime("\n\n\nyyyy-MM-dd HH:mm:ss")}
    $message
    """.trimIndent()) // 往文件里写入字符串
            } else {
                val ps = PrintStream(FileOutputStream(file))
                file.createNewFile()
                ps.println("""
    ${TimeUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")}
    $message
    """.trimIndent()) // 往文件里写入字符串
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    //endregion

}