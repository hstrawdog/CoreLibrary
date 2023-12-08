package com.easy.readbook.repository

import com.easy.core.net.net.ok.OkHttp
import com.easy.core.net.net.ok.OkNetCallback
import com.easy.core.utils.TimeUtils
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.bean.book.BookSource
import com.easy.readbook.room.entity.Book
import com.easy.readbook.repository.read.JsoupUtils
import com.easy.readbook.room.RoomUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.repository
 * @Date : 下午 4:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object BookDetailRepository {
    fun readBookDetail(book: com.easy.readbook.room.entity.Book, readSource: BookSource, latestChapter: ILatestChapter) {
        CoroutineScope(Dispatchers.IO).launch {
            LogUtils.e4Debug("详情页面:  " + book.bookDetailUrl)
            OkHttp.newHttpCompat().getExecute(book.bookDetailUrl, ParamsUtils.getParams(readSource), object :
                OkNetCallback {
                override fun onSuccess(statusCode: String, response: String) {
                    val b = JsoupUtils.getBookDetail(response, book, readSource)
                    b.refreshTime = TimeUtils.nowDate
                    // 更新书籍
                    RoomUtils.getBookDao().update(b)
                    CoroutineScope(Dispatchers.Main).launch {
                        latestChapter.onEndCall(b, true)
                    }
                }

                override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                    latestChapter.onEndCall(book, false)
                }
            })
        }
    }

    interface ILatestChapter {
        fun onEndCall(book: com.easy.readbook.room.entity.Book, isSuccess: Boolean)
    }
}