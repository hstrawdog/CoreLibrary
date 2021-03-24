package com.qq.readbook

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.hqq.core.utils.gson.GsonUtil
import com.qq.readbook.bean.book.BookSource
import com.qq.readbook.bean.RuleSearchBean
import com.qq.readbook.repository.read.JsoupUtils
import org.jsoup.Jsoup
import org.junit.Test
import org.seimicrawler.xpath.JXDocument
import java.io.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {


        val str: String? = null

        val a = "1aaab".trim() {
            it <= 'a'
        }

        print(a)
        print(str?.trim { it <= ' ' })
        print("".toString().trim { it <= ' ' })
        print(' '.toString().trim { it <= ' ' })


        var tt = "{\"tt\":[{\"class\":\"library\"},{\"class\":\"library\"}]}";
        var t1 = "{key:{\"class\":\"library\"},{\"class\":\"library\"}}";
        var list = GsonUtil.fromJson(tt, JsonObject::class.java)
        print("")

    }

    @Test
    fun testHtml() {
        var html =  readFileContent("/Users/huangqiqiang/Desktop/Android_studio/ReadBook/app/src/main/assets/testSearch.html")
        val doc = Jsoup.parse(html)
        var sourceList = readJson()
        val searchRuleBean = GsonUtil.fromJson<RuleSearchBean>(sourceList.ruleSearch, RuleSearchBean::class.java)
        println("---开始------")
        var list = JXDocument.create(doc).selN(searchRuleBean?.ruleSearchList)
        for (jxNode in list) {
            println("---开始单个书籍------")
            println(jxNode.toString())
            searchRuleBean?.apply {
                println("   ")
                println("   ")
                println(ruleBookName)
                println("ruleBookName:  " + JsoupUtils.getValue4key(jxNode, ruleBookName))
                println("   ")
                println(ruleAuthor)
                println("ruleAuthor:  " + JsoupUtils.getValue4key(jxNode, ruleAuthor))
                println("   ")

                println(ruleImg)
                println("ruleImg:  " + JsoupUtils.getValue4key(jxNode, ruleImg))
                println("   ")

                println(ruleDesc)
                println("ruleDesc:  " + JsoupUtils.getValue4key(jxNode, ruleDesc))
                println("   ")

                println(ruleChapterUrl)
                println("ruleChapterUrl:  " + JsoupUtils.getValue4key(jxNode, ruleChapterUrl))
                println("   ")

                println(ruleBookDetailUrl)
                println("ruleBookDetailUrl:  " + JsoupUtils.getValue4key(jxNode, ruleBookDetailUrl))
                println("   ")

                println(ruleNewestChapterTitle)
                println("ruleNewestChapterTitle:  " + JsoupUtils.getValue4key(jxNode, ruleNewestChapterTitle))
                println("   ")

                println(ruleType)
                println("ruleType:  " + JsoupUtils.getValue4key(jxNode, ruleType))
                println("   ")

                println(ruleWordCount)
                println("ruleWordCount:  " + JsoupUtils.getValue4key(jxNode, ruleWordCount))
                println("   ")

                println(ruleUpdateData)
                println("ruleUpdateData:  " + JsoupUtils.getValue4key(jxNode, ruleUpdateData))
                println("   ")

            }
            println("---结束单个书籍------")

        }
        println("---结束------")
    }
    private fun readJson(): BookSource {
       var jsonString =readFileContent("/Users/huangqiqiang/Desktop/Android_studio/ReadBook/app/src/main/assets/testSearch.json")
        val sourceList = Gson().fromJson<BookSource>(
            jsonString,
            object : TypeToken<BookSource>() {}.type
        )
        return sourceList
    }


    fun readFileContent(fileName: String?): String? {
        val file = File(fileName)
        var reader: BufferedReader? = null
        val sbf = StringBuffer()
        try {
            reader = BufferedReader(FileReader(file))
            var tempStr: String?
            while (reader.readLine().also { tempStr = it } != null) {
                sbf.append(tempStr)
            }
            reader.close()
            return sbf.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
        return sbf.toString()
    }


}