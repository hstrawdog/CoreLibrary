package com.easy.readbook.repository

import com.easy.core.net.net.ok.OkHttp
import com.easy.core.net.net.ok.OkNetCallback
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.room.entity.BookContent
import com.easy.readbook.room.entity.Chapter
import com.easy.readbook.repository.read.JsoupUtils
import com.easy.readbook.room.RoomUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.repository
 * @Date : 下午 4:34
 * @Email : qiqiang213@gmail.com
 * @Describe :  书籍正文
 */
object BookArticleRepository {
    /**
     * 获取章节正文
     * @param url
     */
    fun getChapterContent(chapter: com.easy.readbook.room.entity.Chapter, bookName: String, param: ArticleNetCallBack) {
        try {
            executeHttp(chapter, bookName, param)
        } catch (e: Exception) {
            if (e.message == "Expected URL scheme 'http' or 'https' but no colon was found") {
                LogUtils.e4Debug("Expected URL scheme 'http' or 'https' but no colon was found")
                LogUtils.e4Debug("删除当前对应的资源")
                param.onSuccess(false)
                RoomUtils.getBookSourceDao().run {
                    RoomUtils.getChapterDataBase(bookName).chapterDao().apply {
                        // 删除所有的章节
                        deleteAll()
                    }
                }
            }
        }
    }

    /**
     *  执行请求
     * @param chapter Chapter
     * @param bookName String
     * @param param ArticleNetCallBack
     */
    private fun executeHttp(chapter: com.easy.readbook.room.entity.Chapter, bookName: String, param: ArticleNetCallBack) {
        val source = RoomUtils.getBookSourceDao().getSource4Name(chapter.sources)
        if (source == null) {
            ToastUtils.showToast("当前源不可用")
            return
        }
        val params = ParamsUtils.getParams(source)
        OkHttp.newHttpCompat().getExecute(
            chapter.url, params, object : OkNetCallback {
                override fun onSuccess(statusCode: String?, response: String?) {
                    val content = JsoupUtils.getArticleDetail(response, source)
                    if (!content.isNullOrBlank()) {
                        val bookContent = com.easy.readbook.room.entity.BookContent()
                            .apply {
                            number = chapter.number
                            this.content = content
                            this.sourceName = chapter.sources
                        }
                        chapter.isCache = true
                        RoomUtils.getChapterDataBase(bookName).bookContentDao().insert(bookContent)
                        RoomUtils.getChapterDataBase(bookName).chapterDao().update(chapter)
                        param.onSuccess(true)
                    } else {
                        LogUtils.e4Debug("解析内容失败     " + chapter.title + "   :  " + chapter.url)
                        param.onSuccess(false)
                    }
                }

                override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                    param.onSuccess(false)
                }

            }
        )
    }


    interface ArticleNetCallBack {
        fun onSuccess(boolean: Boolean);
    }

}