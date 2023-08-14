package com.easy.readbook.repository

import com.easy.core.net.net.ok.OkHttp
import com.easy.core.net.net.ok.OkNetCallback
import com.easy.core.utils.gson.GsonUtil
import com.easy.core.utils.log.LogUtils
import com.qq.readbook.bean.book.BookSource
import com.qq.readbook.repository.read.JsoupUtils
import com.qq.readbook.room.RoomUtils
import com.easy.readbook.room.entity.Book
import com.easy.readbook.room.entity.SearchLog
import org.seimicrawler.xpath.JXDocument
import java.net.URLEncoder


/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.repository
 * @Date : 上午 10:24
 * @Email : qiqiang213@gmail.com
 * @Describe :搜索小说
 */
object SearchBookRepository {
    /**
     *
     * @param key
     */
    @JvmStatic
    fun doSearch(source: BookSource, key: String, callback: SearchRepositoryCallback) {
        val call = object : OkNetCallback {
            override fun onSuccess(statusCode: String?, response: String?) {
                response?.let { it ->
                    LogUtils.e4Debug("-------------start-------------------")
                    LogUtils.e4Debug(GsonUtil.toJsonString(source))
                    LogUtils.e4Debug("----------------end----------------")
                    val books = doReadBookList(it.replace("gbk", "utf-8"), source)
                    callback.onSearchBook(books as ArrayList<com.easy.readbook.room.entity.Book>?, true)
                    // 获取下一页数据地址
                    val nextPageUrl = JsoupUtils.getNextPageUrl(response)
                    if (nextPageUrl.isNotEmpty()) {

                    }
                }
            }

            override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                callback.onSearchBook(null, false)
            }
        }
        LogUtils.e4Debug("doSearch     " + source.searchUrl + "  " + key)
        LogUtils.e4Debug(source.sourceName)
        if (source.searchUrl.contains("@")) {
            //post 表单请求
            doPost(source, key, call)
        } else {
            //get 表单请求
            doGet(source, key, call)
        }
    }

    fun doReadBookList(html: String, source: BookSource): ArrayList<com.easy.readbook.room.entity.Book> {
        val books = doReadBookList4Source(source.sourceName, source, html)
        for (book in books) {
            addSearchLog(book)
        }
        return books
    }

    /**
     *
     * @param searchElement RuleSearchBean?
     * @param html String
     * @return ArrayList<Book>
     */
    fun doReadBookList4Source(sourceName: String, bookSource: BookSource, html: String): ArrayList<com.easy.readbook.room.entity.Book> {
        val books: ArrayList<com.easy.readbook.room.entity.Book> = ArrayList<com.easy.readbook.room.entity.Book>()
        val ruleSearchBean = RoomUtils.getSearchRuleDao().getRuleSearch(bookSource.sourceName)
        ruleSearchBean?.let {
            val list = JXDocument.create(html).selN(ruleSearchBean.searchListRule)
            val bookInfoRule = RoomUtils.getBookInfoRuleDao().getBookInfoRule(bookSource.sourceName)
            bookInfoRule?.let {
                for (child in list) {
                    val book = JsoupUtils.doReadBook(child, bookInfoRule, sourceName)
                    if (book != null) {
                        books.add(book)
                    }
                }
            }
        }

        return books
    }


    /**
     *  post 提交
     *
     */
    private fun doPost(source: BookSource, key: String, callback: OkNetCallback) {
        val keys = source.searchUrl.split("@")
        OkHttp.newHttpCompat().postExecute(keys[0], OkHttp.newParamsCompat().apply { put(keys[1], key) }, callback)
    }

    /**
     *  get 请求
     */
    private fun doGet(source: BookSource, key: String, callback: OkNetCallback) {
        val url = if (source.encode.isNotEmpty()) {
            String.format(source.searchUrl, URLEncoder.encode(key, source.encode))
        } else {
            String.format(source.searchUrl, key)
        }
        LogUtils.e4Debug("-searchEncode----------     " + url)
        OkHttp.newHttpCompat().getExecute(url, ParamsUtils.getParams(source), callback)
    }

    /**
     *  保存搜索记录 以便切换数据源使用
     * @param book Book
     */
    private fun addSearchLog(book: com.easy.readbook.room.entity.Book) {
        val bookSources = com.easy.readbook.room.entity.SearchLog()
            .apply {
            this.bookId = book.bookId
            this.bookName = book.name
            this.sourcesName = book.sourceName
            this.bookDetailUrl = book.bookDetailUrl
            this.bookChapterUrl = book.chapterUrl
        }

        RoomUtils.getSearchLogDao().apply {
            book.sourceName?.let { it1 ->
                val b = getBookSource(it1, book.bookId)
                if (b == null) {
                    insertAll(bookSources);
                } else {
                    bookSources.id = b.id
                    update(bookSources)
                }
            }
        }
    }

    interface SearchRepositoryCallback {
        fun onSearchBook(book: ArrayList<com.easy.readbook.room.entity.Book>?, isSuccess: Boolean)
    }


}

