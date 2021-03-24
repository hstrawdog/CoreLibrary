package com.qq.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qq.readbook.bean.book.ArticleContentRule

/**
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.room.dao.RuleArticleContentDao.java
 * @emain: 593979591@qq.com
 * @date: 2021-03-15 23:02
 * @version V1.0 <描述当前版本功能>
 *
 */
@Dao
interface ArticleContentRuleDao {
    @Insert
    fun insertAll(vararg source: ArticleContentRule)

    @Query("select * from ArticleContentRule where sourceName=:sourceName")
    fun gerArticleContentRule(sourceName: String): ArticleContentRule?

    @Query("delete  FROM ArticleContentRule")
    fun deleteAll()
}