package com.easy.readbook.bean.book

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean
 * @Date : 下午 12:00
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Entity
class BookSource {
    @Expose
    @PrimaryKey(autoGenerate = true)
    var id = 0

    /**
     *  资源名称
     */
    var sourceName: String = ""

    /**
     *  资源地址
     */
    var sourceUrl: String = ""

    /**
     *  资源搜索地址
     */
    var searchUrl: String = ""

    /**
     *   是否需要encode
     */
    var encode = ""

    /**
     * 是否需要decode
     */
    var decode = ""

    /**
     *  heads
     *  部分网站 有避免爬虫
     */
    var requestHeads = ""

    /**
     *  默认0   从搜索列表中读取书籍信息
     *  1  列表读取数据后 立即 从详情中 查询 数据
     */
    var requestType = 0

    /**
     *  搜索详情
     *  0 直接网页可以解析出所有数据
     *  1 需要在搜索后执行详情
     *  2 需要在详情界面爬取详情数据
     *
     */
    var searchDetail: Int = 0

    /**
     * 权重值 优先使用权重高的进行查询
     */
    var weight = 10

    /**
     *  解析类型
     * 0  JsoupXpath 解析
     * 1 Json 解析
     *
     */
    var parsingType = 0

    /**
     *  搜索列表
     */
    @Ignore
    var ruleSearch: SearchRule? = null

    /**
     *  包含
     */
    @Ignore
    var ruleChapter: ChapterInfoRule? = null

    /**
     *  解析文章详情
     */
    @Ignore
    var ruleArticleContent: ArticleContentRule? = null

    /**
     *  书籍 基本信息规则
     */
    @Ignore
    var ruleBookInfo: BookInfoRule? = null

}