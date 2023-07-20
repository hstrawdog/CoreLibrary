package com.hqq.core.utils.log

import android.content.Context
import android.util.Log
import com.hqq.core.utils.DataTool.isNullString
import com.hqq.core.utils.RxTool
import com.hqq.core.utils.TimeTool
import com.hqq.core.utils.file.FileTool
import com.hqq.core.utils.file.FileTool.rootPath
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author tamsiree
 * @date 2016/1/24
 */
object TLog {
    private val LOG_FORMAT = SimpleDateFormat("yyyy年MM月dd日_HH点mm分ss秒") // 日志的输出格式
    private val FILE_SUFFIX = SimpleDateFormat("HH点mm分ss秒") // 日志文件格式
    private val FILE_DIR = SimpleDateFormat("yyyy年MM月dd日") // 日志文件格式

    // 日志文件总开关
    private var LOG_SWITCH = true

    // 日志写入文件开关
    private var LOG_TO_FILE = true

    //崩溃日志写入文件开关
    private var LOG_CRASH_FILE = true

    private const val LOG_TAG = "TLog" // 默认的tag
    private const val LOG_TYPE = 'v' // 输入日志类型，v代表输出所有信息,w则只输出警告...
    private const val LOG_SAVE_DAYS = 7 // sd卡中日志文件的最多保存天数
    private var LOG_FILE_PATH // 日志文件保存路径
            : String? = null
    private var LOG_FILE_NAME // 日志文件保存名称
            : String? = null

    @JvmStatic
    fun init(context: Context) { // 在Application中初始化
        LOG_FILE_PATH = rootPath!!.path + File.separator + context.packageName + File.separator + "Log"
        LOG_FILE_NAME = "TLog_"
    }

    fun switchLog(switch: Boolean) {
        LOG_SWITCH = switch
    }

    fun switch2File(switch: Boolean) {
        LOG_TO_FILE = switch
    }

    fun switchCrashFile(switch: Boolean) {
        LOG_CRASH_FILE = switch
    }


    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     */
    private fun log(tag: String, msg: String, tr: Throwable?, level: Char): File {
        var logFile = File("")
        if (LOG_SWITCH) {
            if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg, tr)
            } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.w(tag, msg, tr)
            } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.d(tag, msg, tr)
            } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.i(tag, msg, tr)
            } else {
                Log.v(tag, msg, tr)
            }
            if (LOG_TO_FILE || (LOG_CRASH_FILE && ('e' == level || 'e' == LOG_TYPE))) {
                var content = ""
                if (!isNullString(msg)) {
                    content += msg
                }
                if (tr != null) {
                    content += "\n"
                    content += Log.getStackTraceString(tr)
                }
                logFile = log2File(level.toString(), tag, content)
            }
        }
        return logFile
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
        val file = File(path, "$LOG_FILE_NAME$date.txt")
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
        FileTool.deleteDir(LOG_FILE_PATH)
    }

    /**
     * 得到LOG_SAVE_DAYS天前的日期
     *
     * @return
     */
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
        val fileDir = File(rootPath.toString() + File.separator + RxTool.getContext().packageName)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val file = File(fileDir, TimeTool.getCurrentDateTime("yyyyMMdd") + ".txt")
        try {
            if (file.exists()) {
                val ps = PrintStream(FileOutputStream(file, true))
                ps.append("""
    ${TimeTool.getCurrentDateTime("\n\n\nyyyy-MM-dd HH:mm:ss")}
    $message
    """.trimIndent()) // 往文件里写入字符串
            } else {
                val ps = PrintStream(FileOutputStream(file))
                file.createNewFile()
                ps.println("""
    ${TimeTool.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")}
    $message
    """.trimIndent()) // 往文件里写入字符串
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}