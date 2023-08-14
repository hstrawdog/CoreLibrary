package com.easy.readbook.repository

import com.easy.core.net.net.ok.OkHttp
import com.easy.core.net.net.ok.OkNetCallback
import com.qq.readbook.repository.read.JsoupUtils
import com.qq.readbook.room.RoomUtils

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.repository
 * @Date : 下午 2:38
 * @Email : qiqiang213@gmail.com
 * @Describe : 最新章节
 */
object NewestChapterRepository {
    fun doChapterUrl(url: String, sourceName: String, latestChapter: LatestChapter) {
        var source = RoomUtils.getBookSourceDao().getSource4Name(sourceName)
        val params = OkHttp.newParamsCompat()
        params.decodeCharset = source?.encode
        OkHttp.newHttpCompat().get(url, params, object : OkNetCallback {
            override fun onSuccess(statusCode: String, response: String) {
                val b = JsoupUtils.getNewChapterFormHtml(response, sourceName)
                latestChapter.onEnd(b, true)
            }

            override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                latestChapter.onEnd("", false)
            }
        })
    }

    interface LatestChapter {
        fun onEnd(chapterTitle: String, isSuccess: Boolean)
    }
}