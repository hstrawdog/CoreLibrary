package com.easy.readbook.repository

import com.easy.core.net.net.ok.OkHttp
import com.easy.core.net.net.ok.OkNetCallback
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.room.entity.Book
import com.easy.readbook.room.entity.Chapter
import com.qq.readbook.repository.read.JsoupUtils
import com.qq.readbook.room.RoomUtils


/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.repository
 * @Date : 下午 1:45
 * @Email : qiqiang213@gmail.com
 * @Describe : 数据目录
 */
object BookChaptersRepository {
    /**
     *  读取数据量 目录列表
     *  根据源名称 查询对应的url
     *  爬取目录列表
     */
    fun getBookChapters(book: com.easy.readbook.room.entity.Book, bookChaptersCall: BookChaptersCall?) {

        if (book.chapterUrl.isNullOrEmpty()) {
            LogUtils.e4Debug("getBookChapters  Url 空的 ")
            return
        }
        // 解析规则
        val source = RoomUtils.getBookSourceDao().getSource4Name(book.sourceName)
        if (source == null) {
            LogUtils.e4Debug("未找到对应源    " + book.sourceName)
            return
        }
        LogUtils.e4Debug("     加载目录  :   ")
        LogUtils.e4Debug(source.sourceName)
        LogUtils.e4Debug(book.chapterUrl)
        OkHttp.newHttpCompat().get(book.chapterUrl, ParamsUtils.getParams(source), object : OkNetCallback {
            override fun onSuccess(statusCode: String, response: String) {
                LogUtils.e4Debug("-----目录-------")
//                LogUtils.e4Debug(response.replace(Regex("\r|\n\t"), ""))
                LogUtils.e4Debug("-----结束-------")
                val arrayList = JsoupUtils.readChapter(book.chapterUrl, response, source, book)
                bookChaptersCall?.onSuccess(arrayList)
                book.chapterListSize = arrayList.size
                RoomUtils.getBookDao().update(book)
                RoomUtils.getChapterDataBase(book.bookIdName).chapterDao().apply {
                    // 获取的章节数量不一致 则出现了新的章节
                    if (arrayList.isNotEmpty()) {
                        deleteAll()
                        resetId()
                        insert(arrayList)
                    }
                }
            }

            override fun onFailure(statusCode: String, errMsg: String, response: String) {}
        })
    }

    interface BookChaptersCall {
        fun onSuccess(arrayList: List<com.easy.readbook.room.entity.Chapter>);
    }

}