package com.qq.readbook.repository.read

import android.text.Html
import com.hqq.core.utils.log.LogUtils
import com.qq.readbook.bean.*
import com.qq.readbook.bean.book.BookInfoRule
import com.qq.readbook.bean.book.BookSource
import com.qq.readbook.room.RoomUtils
import com.qq.readbook.room.entity.Book
import com.qq.readbook.room.entity.Chapter
import com.qq.readbook.utils.MD5Utils
import org.seimicrawler.xpath.JXDocument
import org.seimicrawler.xpath.JXNode
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.repository
 * @Date : 下午 5:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object JsoupUtils {

    /**
     *
     * @param element JXNode?
     * @param rule String?
     * @return String
     */
    fun getValue4key(element: JXNode?, rule: String?): String {

        if (!rule.isNullOrEmpty() && element != null) {
            try {
                val value = element.selOne(rule)?.asString()
                if (value.isNullOrEmpty()) {
                    return ""
                }
                return value
            } catch (e: Exception) {
                LogUtils.e4Debug("getValue4key 解析异常 ----------------------")
                LogUtils.e4Debug("html:      " + element.toString())
                LogUtils.e4Debug("规则 :   " + rule.toString())
                e.printStackTrace()
            }
        }

        return ""
    }

    /**
     *
     * @param element JXDocument?
     * @param ruleBookName String?
     * @return String
     */
    fun getValue4key(html: String?, rule: String?): String {
        if (!rule.isNullOrEmpty() && html != null) {
            val element = JXDocument.create(html)
            try {
                var value = "";
                // |  会影响取到的结果 尝试 分割后解析
                if (rule.contains("|")) {
                    val list = rule.split("|")
                    for (s in list) {
                        value = selN(element, s, value)
                        // 去获取到的第一个值
                        if (value.isNotEmpty()) {
                            break
                        }
                    }
                } else {
                    value = selN(element, rule, value)
                }

                if (value.isEmpty()) {
                    return "";
                }
                return value
            } catch (e: Exception) {
                LogUtils.e4Debug("getValue4key 解析异常 ----------------------")
                LogUtils.e4Debug("规则 :   " + rule.toString())
                e.printStackTrace()
            }
        }
        return ""

    }

    private fun selN(element: JXDocument,
                     rule: String?,
                     value: String): String {
        var value1 = value
        val list = element.selN(rule)
        for (jxNode in list) {
            if (jxNode != null) {
                value1 = jxNode.asString()
                if (value1.isNotEmpty()) {
                    break
                }
            }
        }
        return value1
    }


    /**
     *  获取下一页面的地址
     * @param response String
     * @return String
     */
    fun getNextPageUrl(response: String): String {
        val url = JXDocument.create(response).selOne("//div[@class=\"page\"]/a[1]/@href")
        val name = JXDocument.create(response).selOne("//div[@class=\"page\"]/a[1]/text()")
        if ("下一页" === name && url != null && url is String && url.length > 0) {
            return url
        } else {
            return "";
        }
    }

    /**
     *  解析文章内容
     * @param response String?
     * @param source ReadSource?
     * @return String
     */
    fun getArticleDetail(html: String?, source: BookSource?): String {
        if (source != null) {
            val ruleArticleContent = RoomUtils.getArticleContentRuleDao().gerArticleContentRule(source.sourceName)
            val nodeBoolean = getValue4key(html, ruleArticleContent?.content)
            var content = Html.fromHtml(nodeBoolean).toString()
            content = content.replace(" ", "  ")
            return content
        }
        return ""
    }

    /**
     *  读取Book数据
     * @param element Element html 数据源
     * @param source ReadSource 源
     * @param jsonElement JsonElement  key
     * @return Book
     */
    fun doReadBook(element: JXNode, jsonElement: BookInfoRule, sourceName: String): Book? {
        val book = Book()
        jsonElement.apply {
            book.name = getValue4key(element, bookName)
            book.author = getValue4key(element, author)
            book.imgUrl = getValue4key(element, img)
            book.desc = getValue4key(element, desc)
            book.chapterUrl = getValue4key(element, chapterUrl)
            book.bookDetailUrl = getValue4key(element, bookDetailUrl)
            book.newestChapterTitle = getValue4key(element, newestChapterTitle)
            book.type = getValue4key(element, type)
            book.wordCount = getValue4key(element, wordCount)
            book.updateDate = getValue4key(element, updateData)
            book.sourceName = sourceName
            book.bookId = MD5Utils.getStringMD5(book.name + book.author)
        }
        if (book.name.isEmpty() && book.author.isEmpty()) {
            return null
        }
        return book
    }

    /**
     *  读取章节列表
     * @param html String
     * @param source ReadSource?
     * @return ArrayList<Chapter>
     */
    fun readChapter(requestUrl: String, html: String, source: BookSource?, book: Book): ArrayList<Chapter> {
        val chapters = ArrayList<Chapter>()
        if (source != null) {
            val chapterElement = RoomUtils.getChapterInfoRuleDao().getChapterInfoRule(source.sourceName)
            LogUtils.e4Debug("解析: " + chapterElement.toString())
            if (chapterElement != null) {
                val list = JXDocument.create(html).selN(chapterElement.chapterList)
                if (list != null) {
                    for ((index, child) in list.withIndex()) {
                        val chapter = Chapter()
                        chapter.apply {
                            title = getValue4key(child, chapterElement.title)
                            url = getValue4key(child, chapterElement.url)
                            if (!url.startsWith("http")) {
                                // 相对路径 用当前请求进行补完地址
                                url = requestUrl + url
                            }
                            number = index
                            sources = source.sourceName
                            bookId = book.bookId
                            if (title.isNotEmpty() && "null" != title) {
                                chapters.add(chapter)
                            }
                        }
                    }
                }
            }
        }

        return chapters
    }

    /**
     * 解析最新章节
     * @param html String?
     * @param sourceName String
     * @return String
     */
    fun getNewChapterFormHtml(html: String?, sourceName: String): String {
        LogUtils.e4Debug("-----------------------")
        LogUtils.e4Debug("解析最新章节:")
        LogUtils.e4Debug(sourceName)
        val source = RoomUtils.getBookInfoRuleDao().getBookInfoRule(sourceName)
        LogUtils.e4Debug(source?.newestChapterTitle)
        LogUtils.e4Debug("-----------------------")
        val a1 = System.currentTimeMillis()
        val a2 = System.currentTimeMillis()
        val newestChapterTitle = getValue4key(html, source?.newestChapterTitle)
        val a3 = System.currentTimeMillis()
        LogUtils.e4Debug("解析最新章节结束1 " + newestChapterTitle + "         " + (a1 - a2))
        LogUtils.e4Debug("-----------------------")
        return newestChapterTitle
    }

    /**
     * 获取书籍详情
     * @param html String
     * @param book Book
     * @param readSource BookSource
     * @return Book
     */
    fun getBookDetail(html: String, book: Book, source: BookSource): Book {
        val bookDetail = RoomUtils.getBookInfoRuleDao().getBookInfoRule(source.sourceName)
        bookDetail?.apply {
            val name = getValue4key(html, bookName)
            val author = getValue4key(html, author)
            val imgUrl = getValue4key(html, img)
            val desc = getValue4key(html, desc)
            val type = getValue4key(html, type)
            val wordCount = getValue4key(html, wordCount)
            val updateDate = getValue4key(html, updateData)

            if (name.isNotEmpty()) {
                book.name = name
            }
            if (author.isNotEmpty()) {
                book.author = author
            }
            if (imgUrl.isNotEmpty()) {
                book.imgUrl = imgUrl
            }
            if (desc.isNotEmpty()) {
                book.desc = desc
            }
            if (type.isNotEmpty()) {
                book.type = type
            }
            if (wordCount.isNotEmpty()) {
                book.wordCount = wordCount
            }
            if (updateDate.isNotEmpty()) {
                book.updateDate = updateDate
            }
        }

        return book
    }

}

