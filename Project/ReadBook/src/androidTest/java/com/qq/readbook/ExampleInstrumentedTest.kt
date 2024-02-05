package com.easy.readbook

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.easy.core.utils.gson.GsonUtil
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.bean.book.BookSource
import com.easy.readbook.bean.RuleChapterBean
import com.easy.readbook.bean.RuleSearchBean
import com.easy.readbook.repository.read.JsoupUtils
import org.jsoup.Jsoup
import org.junit.Test
import org.junit.runner.RunWith
import org.seimicrawler.xpath.JXDocument
import java.io.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    /**
//     * 内容
//     */
//    @Test
//    fun testContent() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        val result = readHtml(appContext, "testContent.html")
//        val html = Jsoup.parse(result)
//        val sourceList = readJson(appContext)
//        val ruleArticleContent =
//            GsonUtil.fromJson(sourceList?.ruleArticleContent.toString(), RuleArticleContent::class.java)
//        val content = JsoupUtils.getValue4key(JXDocument.create(html), ruleArticleContent?.content)
//        LogUtils.e4Debug("" + ruleArticleContent?.content)
//        LogUtils.e4Debug("内容 :   " + content)
//
//    }
//
//    /**
//     * 章节 与 更新信息
//     */
//    @Test
//    fun testChapter() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        val result = readHtml(appContext, "testChapter.html")
//        val html = Jsoup.parse(result)
//        val sourceList = readJson(appContext)
//        val chapterElement = GsonUtil.fromJson(sourceList.ruleChapter, RuleChapterBean::class.java)
//        LogUtils.e4Debug(chapterElement?.chapterList)
//        val list = JXDocument.create(html).selN(chapterElement?.chapterList)
//        LogUtils.e4Debug("----------------------")
//        if (list != null) {
//            for (child in list) {
//                val title = JsoupUtils.getValue4key(child, chapterElement?.title)
//                val url = JsoupUtils.getValue4key(child, chapterElement?.url)
//                LogUtils.e4Debug(chapterElement?.title)
//                LogUtils.e4Debug("标题:   " + title)
//                LogUtils.e4Debug(chapterElement?.url)
//                LogUtils.e4Debug("章节:   " + url)
//                break
//            }
//        }
//        LogUtils.e4Debug("--------------------------------")
//        val newestChapterTitle = JsoupUtils.getValue4key(JXDocument.create(html), sourceList.ruleNewestChapter)
//        LogUtils.e4Debug("" + sourceList.ruleNewestChapter)
//        LogUtils.e4Debug("最新章节:       " + newestChapterTitle)
//        LogUtils.e4Debug("--------------------------------")
//        val bookDetail = GsonUtil.fromJson(sourceList.ruleBookDetail.toString(), RuleSearchBean::class.java)
//        val updateDate = JsoupUtils.getValue4key(JXDocument.create(html), bookDetail?.ruleUpdateData)
//        LogUtils.e4Debug("" + sourceList.ruleBookDetail)
//        LogUtils.e4Debug("最新更新时间:       " + updateDate)
//
//    }
//
//    /**
//     *  搜索
//     */
//    @Test
//    fun useAppContext() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        // Context of the app under test.
//        val result = readHtml(appContext, "testSearch.html")
//        val doc = Jsoup.parse(result)
//        var sourceList = readJson(appContext)
//        val searchRuleBean = GsonUtil.fromJson<RuleSearchBean>(sourceList.ruleSearch, RuleSearchBean::class.java)
//        LogUtils.e4Debug("---开始------")
//        var list = JXDocument.create(doc).selN(searchRuleBean?.ruleSearchList)
//        for (jxNode in list) {
//            LogUtils.e4Debug("---开始单个书籍------")
//            LogUtils.e4Debug(jxNode.toString())
//            searchRuleBean?.apply {
//                println("   ")
//                println("   ")
//                LogUtils.e4Debug(ruleBookName)
//                LogUtils.e4Debug("ruleBookName:  " + JsoupUtils.getValue4key(jxNode, ruleBookName))
//                println("   ")
//                LogUtils.e4Debug(ruleAuthor)
//                LogUtils.e4Debug("ruleAuthor:  " + JsoupUtils.getValue4key(jxNode, ruleAuthor))
//                println("   ")
//
//                LogUtils.e4Debug(ruleImg)
//                LogUtils.e4Debug("ruleImg:  " + JsoupUtils.getValue4key(jxNode, ruleImg))
//                println("   ")
//
//                LogUtils.e4Debug(ruleDesc)
//                LogUtils.e4Debug("ruleDesc:  " + JsoupUtils.getValue4key(jxNode, ruleDesc))
//                println("   ")
//
//                LogUtils.e4Debug(ruleChapterUrl)
//                LogUtils.e4Debug("ruleChapterUrl:  " + JsoupUtils.getValue4key(jxNode, ruleChapterUrl))
//                println("   ")
//
//                LogUtils.e4Debug(ruleBookDetailUrl)
//                LogUtils.e4Debug("ruleBookDetailUrl:  " + JsoupUtils.getValue4key(jxNode, ruleBookDetailUrl))
//                println("   ")
//
//                LogUtils.e4Debug(ruleNewestChapterTitle)
//                LogUtils.e4Debug("ruleNewestChapterTitle:  " + JsoupUtils.getValue4key(jxNode, ruleNewestChapterTitle))
//                println("   ")
//
//                LogUtils.e4Debug(ruleType)
//                LogUtils.e4Debug("ruleType:  " + JsoupUtils.getValue4key(jxNode, ruleType))
//                println("   ")
//
//                LogUtils.e4Debug(ruleWordCount)
//                LogUtils.e4Debug("ruleWordCount:  " + JsoupUtils.getValue4key(jxNode, ruleWordCount))
//                println("   ")
//
//                LogUtils.e4Debug(ruleUpdateData)
//                LogUtils.e4Debug("ruleUpdateData:  " + JsoupUtils.getValue4key(jxNode, ruleUpdateData))
//                println("   ")
//
//            }
//            LogUtils.e4Debug("---结束单个书籍------")
//
//        }
//        LogUtils.e4Debug("---结束------")
//    }
//
//    private fun readJson(appContext: Context): BookSource {
//        val inputStream2 = appContext.assets.open("testSearch.json")
//        val inputStreamReader = InputStreamReader(inputStream2)
//        val jsonReader = JsonReader(inputStreamReader)
//        val sourceList = Gson().fromJson<BookSource>(
//            jsonReader,
//            object : TypeToken<BookSource>() {}.type
//        )
//        return sourceList
//    }
//
//    private fun readHtml(appContext: Context, fileName: String): String {
//        val inputStream: InputStream = appContext.getAssets().open(fileName)
//        val lenght: Int = inputStream.available()
//        val buffer = ByteArray(lenght)
//        inputStream.read(buffer)
//        val result = String(buffer)
//        return result
//    }

}