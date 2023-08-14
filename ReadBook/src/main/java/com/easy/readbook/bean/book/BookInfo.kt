package com.easy.readbook.bean.book

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easy.core.utils.gson.GsonExclude

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.bean.book
 * @Date  : 下午 4:37
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Entity
data class BookInfoRule(var bookName: String) {
    @PrimaryKey(autoGenerate = true)
    @GsonExclude
    var id = 0

    @GsonExclude
    var sourceName: String? = null
    var type: String? = null
    var desc: String? = null
    var author: String? = null
    var newestChapterTitle: String? = null
    var chapterUrl: String? = null
    var img: String? = null
    var bookDetailUrl: String? = null
    var wordCount: String? = null
    var updateData: String? = null

}

/**
 *
 * @property chapterList String
 * @property title String?
 * @property url String?
 * @constructor
 */
@Entity
data class ChapterInfoRule(var chapterList: String) {
    @GsonExclude
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @GsonExclude
    var sourceName: String? = null
    var title: String? = null
    var url: String? = null
}

/**
 *
 * @property searchListRule String
 * @property nextPageRule String?
 * @constructor
 */
@Entity
data class SearchRule(var searchListRule: String) {
    @GsonExclude
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @GsonExclude
    var sourceName: String? = null

    /**
     * 列表规则
     * 部分网站 存在重定向 指向具体书籍
     */

    /**
     * 下一页规则
     */
    var nextPageRule: String? = null
}

@Entity
data class ArticleContentRule(var content: String) {
    @GsonExclude
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @GsonExclude
    var sourceName: String? = null

}
