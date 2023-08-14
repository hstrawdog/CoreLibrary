package com.qq.readbook.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hqq.core.ui.list.BaseListViewModel
import com.hqq.core.utils.TimeUtils
import com.hqq.core.utils.ToastUtils
import com.qq.readbook.bean.book.BookSource
import com.qq.readbook.repository.SearchBookRepository
import com.qq.readbook.repository.SearchMatchRepository
import com.qq.readbook.room.RoomUtils
import com.qq.readbook.room.entity.Book
import com.qq.readbook.room.entity.LocalSearchKey
import kotlinx.coroutines.*

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui
 * @Date : 下午 3:09
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SearchViewModel : BaseListViewModel() {
    /**
     * 关键字
     */
    val searchKey = MutableLiveData<String>("")

    /**
     *  书籍源
     */
    val liveBooks = MutableLiveData<ArrayList<Book>>()

    /**
     *  匹配后的书籍名称
     */
    val bookNames = MutableLiveData<ArrayList<String>>()

    /**
     *  是否需要进行匹配
     */
    val bookNameMatch = MutableLiveData<Boolean>(false)

    /**
     *  显示的模式   模糊与精准
     */
    var searchModel = MutableLiveData<Boolean>(true)
    fun onSearch(key: String) {
        if (key.isNullOrEmpty() || key.trim().isNullOrEmpty()) {
            ToastUtils.showToast("请输入关键字")
            return
        }
        pageCount = 1
        pageSize = 20
        val logs = RoomUtils.getLocalSearchKeyDao().getLog4Key(key)
        var log = if (logs.isNullOrEmpty()) null else logs.first()

        if (log == null) {
            log = LocalSearchKey().apply {
                this.key = key
                this.searchTime = TimeUtils.nowDate
            }
            RoomUtils.getLocalSearchKeyDao().insertAll(log)
        } else {
            log.searchTime = TimeUtils.nowDate
            RoomUtils.getLocalSearchKeyDao().update(log)
        }
        CoroutineScope(Dispatchers.Main).launch {
            for (bookSource in RoomUtils.getBookSourceDao().getAll()) {
                doSearch(bookSource, key)
            }
        }
    }

    /**
     * 执行搜索
     * @param readSource ReadSource
     * @param key String
     */
    private suspend fun doSearch(readSource: BookSource, key: String) {
        withContext(Dispatchers.IO) {
            SearchBookRepository.doSearch(readSource, key, object : SearchBookRepository.SearchRepositoryCallback {
                override fun onSearchBook(book: ArrayList<Book>?, isSuccess: Boolean) {
                    GlobalScope.launch(Dispatchers.Main) {
                        liveBooks.value = book
                    }
                }
            })
        }
    }

    /**
     *  搜索类型
     * @param v View
     */
    fun onSearchModel(v: View) {
        searchModel.value = !searchModel.value!!
    }

    /**
     * 搜索匹配
     */
    fun onBooks() {
        if (bookNameMatch.value == false) {
            return
        }
        // 置空书籍
        bookNames.value = ArrayList()
        SearchMatchRepository.doSearch(searchKey.value as String, object : SearchMatchRepository.SearchMatchCallback {
            override fun onSuccess(list: ArrayList<String>) {
                GlobalScope.launch(Dispatchers.Main) {
                    //todo 是否需要对list进行判断避免历史请求污染新数据
                    var arrayList = bookNames.value
                    if (arrayList == null) {
                        arrayList = ArrayList<String>()
                    }
                    arrayList.addAll(list)
                    arrayList.distinct()
                    bookNames.value = arrayList
                }
            }
        })
    }
}