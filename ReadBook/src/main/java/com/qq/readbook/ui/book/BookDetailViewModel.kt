package com.qq.readbook.ui.book

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hqq.core.ui.base.BaseViewModel
import com.hqq.core.utils.ToastUtils
import com.qq.readbook.Keys
import com.qq.readbook.repository.BookChaptersRepository
import com.qq.readbook.repository.BookDetailRepository
import com.qq.readbook.repository.ReadRepository.getBookRecord
import com.qq.readbook.utils.CommentUtils
import com.qq.readbook.room.RoomUtils
import com.qq.readbook.room.entity.Book
import com.qq.readbook.room.entity.Chapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.book
 * @Date  : 上午 9:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BookDetailViewModel : BaseViewModel() {

    val bookLiveData = MutableLiveData<Book>()
    val chapters = MutableLiveData<List<Chapter>>()
    var addBookMenu = MutableLiveData<Boolean>(true)
    val currChapter = MutableLiveData<Int>(0)
    override fun initData(extras: Bundle?) {
        super.initData(extras)
        extras?.getParcelable<Book>(Keys.BOOK)?.let {
            CoroutineScope(Dispatchers.IO).launch {
                readLocalBook(it)
                readChapters(it)
                getBookDetail(it)
            }
        }
    }

    /**
     *  切换源后
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val sourceName = data?.getStringExtra(Keys.BOOK_SOURCE_NAME)
            if (sourceName != null) {
                bookLiveData.value?.let {
                    val bookSources = RoomUtils.getSearchLogDao().getBookSource(sourceName, it.bookId)
                    if (bookSources?.bookChapterUrl?.isNotEmpty() == true) {
                        it.sourceName = sourceName
                        it.chapterUrl = bookSources?.bookChapterUrl
                        RoomUtils.getBookDao().update(it)
                        // 更新信息
                        readChapters(it)
                        bookLiveData.value = bookLiveData.value
                    }

                }
            }
        }
    }

    /**
     *  获取章节信息
     * @param it Book
     */
    private fun readChapters(it: Book) {
        // 读取本地章节
        RoomUtils.getChapterDataBase(it.name + "_" + it.author).chapterDao().apply {
            CoroutineScope(Dispatchers.Main).launch {
                chapters.value = it.sourceName?.let { it1 -> getAll(it1) }
            }
        }
        // 爬取新目录
        BookChaptersRepository.getBookChapters(it, object : BookChaptersRepository.BookChaptersCall {
            override fun onSuccess(arrayList: List<Chapter>) {
                CoroutineScope(Dispatchers.Main).launch {
                    chapters.value = arrayList
                }
            }
        })
    }

    /**
     * 加载本地书本信息
     * @param it Book
     */
    private fun readLocalBook(it: Book) {
        // 插入数据
        val dataBaseBook = RoomUtils.getBookDao().getBookById(it.bookId)
        if (dataBaseBook == null) {
            RoomUtils.getBookDao().apply {
                insertAll(it)
                it.id = getBookById(it.bookId)?.id ?: 0

            }
            bookLiveData.postValue(it)
            getBookRecord(it)?.let { it2 ->
                currChapter.postValue(it2.chapter)
            }
        } else {
            // 用本地数据替换
            bookLiveData.postValue(dataBaseBook)
        }
        addBookMenu.postValue(bookLiveData.value?.localType == 0)


    }

    /**
     * 判断是否需要加载详情 部分搜索页面获取的书籍是完整的
     * @param it Book
     */
    private fun getBookDetail(it: Book) {
        // 爬取详情页面
        val readSource = RoomUtils.getBookSourceDao().getSource4Name(it.sourceName)
        if (readSource == null) {
            // 当前源不可以用
            ToastUtils.showToast("当前源不可用 请切换其他来源")
        } else {
            if (CommentUtils.isRefresh(it, readSource.searchDetail)) {
                BookDetailRepository.readBookDetail(it, readSource, object : BookDetailRepository.ILatestChapter {
                    override fun onEndCall(book: Book, isSuccess: Boolean) {
                        bookLiveData.postValue(book)
                    }
                })
            }
        }

    }

    /**
     * 追更
     */
    fun onAddBook(view: View) {
        bookLiveData.value?.let {
            if (addBookMenu.value == true) {
                it.localType = 1
                showToast("添加成功")
            } else {
                it.localType = 10
                // 避免 book 对象与数据库不一致   book 表中只会存在一种 书名
                showToast("取消成功")
            }
            RoomUtils.getBookDao().update(bookLiveData.value!!)
            addBookMenu.value = !(addBookMenu.value as Boolean)
        }
    }

    /**
     *  开始阅读
     */
    fun onReadBook(view: View) {
        bookLiveData.value?.let {
            if (chapters.value.isNullOrEmpty()) {
                ToastUtils.showToast("正在获取章节目录 请稍后")
                return
            }
            // 判断是否爬取到章节列表
            startActivity(ReadBookActivity::class.java, Bundle().apply {
                putParcelable(Keys.BOOK, bookLiveData.value)
            })
        }
    }

    /**
     *  切换源
     */
    fun onOtherSources(view: View) {
        startActivity(SwitchBookSourceActivity::class.java, Bundle().apply {
            putParcelable(Keys.BOOK, bookLiveData.value)
        }, 1)
    }

}