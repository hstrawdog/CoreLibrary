package com.hqq.core.utils

import java.io.*
import java.util.*

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * @FileName: com.hqq.core.utils.CreateDefColorFile.java
 * @emain: 593979591@qq.com
 * @date: 2019-04-24 20:32
 *
 */
object CreateDefColorFile {
    /**
     * 生成文件路径
     */
    private const val path = "./Core/src/main/res/values/def_text_style.xml"

    /**
     * 文件路径+名称
     */
    private var filenameTemp: String? = null

    @JvmStatic
    fun main(args: Array<String>) {
        val list: MutableList<String> = ArrayList()
        list.add("333")
        list.add("666")
        list.add("999")
        list.add("main")
        list.add("red")
        list.add("white")
        val stringBuilder = StringBuilder()
        stringBuilder.append("""<?xml version="1.0" encoding="utf-8"?>
<resources>

""")
        stringBuilder.append("""    <style name="def_text">
        <item name="android:includeFontPadding">false</item>
    </style>

""")
        run {
            var i = 16
            while (i < 72) {
                stringBuilder.append("    <style name=\"def_text.$i\">\n")
                stringBuilder.append("        <item name=\"android:textSize\">@dimen/x$i</item>\n")
                stringBuilder.append("    </style>\n\n")
                i = i + 2
            }
        }
        var i = 16
        while (i < 47) {
            for (color in list) {
                stringBuilder.append("    <style name=\"def_text.$i.$color\">\n")
                stringBuilder.append("        <item name =\"android:textColor\">@color/color_$color</item>\n")
                stringBuilder.append("    </style>\n\n")
            }
            i = i + 2
        }
        stringBuilder.append("\n</resources>")
        createFile(stringBuilder.toString())
    }

    /**
     * 创建文件
     *
     * @param filecontent 文件内容
     * @return 是否创建成功，成功则返回true
     */
    fun createFile(filecontent: String): Boolean {
        var bool = false
        filenameTemp = path //文件路径+名称+文件类型
        val file = File(filenameTemp)
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile()
                bool = true
                println("success create file,the file is $filenameTemp")
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent)
            } else {
                val fileWriter = FileWriter(file)
                fileWriter.write("")
                fileWriter.flush()
                fileWriter.close()
                //创建文件成功后，写入内容到文件里
                writeFileContent(filenameTemp, filecontent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bool
    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun writeFileContent(filepath: String?, newstr: String): Boolean {
        var bool = false
        val filein = """
            $newstr

            """.trimIndent() //新写入的行，换行
        var temp: String? = ""
        var fis: FileInputStream? = null
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        var fos: FileOutputStream? = null
        var pw: PrintWriter? = null
        try {
            val file = File(filepath) //文件路径(包括文件名称)
            //将文件读入输入流
            fis = FileInputStream(file)
            isr = InputStreamReader(fis)
            br = BufferedReader(isr)
            var buffer = StringBuffer()

            //文件原有内容
            var i = 0
            while (br.readLine().also { temp = it } != null) {
                buffer.append(temp)
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"))
                i++
            }
            buffer.append(filein)
            fos = FileOutputStream(file)
            pw = PrintWriter(fos)
            pw.write(buffer.toString().toCharArray())
            pw.flush()
            bool = true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //不要忘记关闭
            pw?.close()
            fos?.close()
            br?.close()
            isr?.close()
            fis?.close()
        }
        return bool
    }
}