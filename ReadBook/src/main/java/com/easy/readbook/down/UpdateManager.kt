package com.easy.readbook.down

import androidx.lifecycle.MutableLiveData
import com.easy.core.utils.log.LogUtils
import com.qq.readbook.repository.BookDetailRepository
import com.qq.readbook.room.RoomUtils
import com.easy.readbook.room.entity.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashMap

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.down
 * @Date : 下午 5:18
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object UpdateManager : UpdateInterfaces {

    val liveBook = MutableLiveData<com.easy.readbook.room.entity.Book>()

    override var maxThread: Int = 3
    override var needLoadBookMap = HashMap<String, com.easy.readbook.room.entity.Book>()
    override var LoadBookMap = HashMap<String, com.easy.readbook.room.entity.Book>()
    override fun handlerBook(book: com.easy.readbook.room.entity.Book) {
        needLoadBookMap.put(book.bookId, book)
        executeNextRequest()
    }

    override fun nextBookBook(): com.easy.readbook.room.entity.Book? {

        if (needLoadBookMap.values.isEmpty()) {
            return null
        }

        //取出 书籍
        val book = needLoadBookMap.values.first()
        // 移除待加载
        needLoadBookMap.remove(book.bookId)
        return book
    }

    override fun executeNextRequest() {
        if (LoadBookMap.size < 3) {
            val book = nextBookBook()
            if (book == null) {
                loadSuccess(null)
            } else {
                executeRequest(book)
            }
        }
    }

    /**
     *  执行完毕
     */
    override fun loadSuccess(book: com.easy.readbook.room.entity.Book?) {
        if (book != null) {
            LoadBookMap.remove(book.bookId)
            liveBook.value = book!!
        }
        if (needLoadBookMap.size == 0) {
            // 全部执行完毕
            LogUtils.dInfo("全部执行完毕")
        } else {
            executeNextRequest()
        }


    }

    /**
     *  执行网络请求
     * @param book Book
     */
    override fun executeRequest(book: com.easy.readbook.room.entity.Book) {
        LoadBookMap.put(book.bookId, book)
        LogUtils.dInfo("执行请求 " + book.name)
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(2 * 1000)
            LogUtils.dInfo("执行完成" + book.name)
            val bookSource = RoomUtils.getBookSourceDao().getSource4Name(book.sourceName)
            if (bookSource != null) {
                BookDetailRepository.readBookDetail(book, bookSource, object : BookDetailRepository.ILatestChapter {
                    override fun onEndCall(book: com.easy.readbook.room.entity.Book, isSuccess: Boolean) {
                        loadSuccess(book)
                    }
                })
            } else {
                loadSuccess(book)
            }
        }
    }


}