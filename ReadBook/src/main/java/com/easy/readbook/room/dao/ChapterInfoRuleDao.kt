package com.easy.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qq.readbook.bean.book.ChapterInfoRule

/**
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.room.dao.ChapterInfoRuleDao.java
 * @emain: 593979591@qq.com
 * @date: 2021-03-15 23:03
 * @version V1.0 <描述当前版本功能>
 *
 */
@Dao
interface ChapterInfoRuleDao {
    @Insert
    fun insertAll(vararg source: ChapterInfoRule)

    @Query("select * from chapterInfoRule where sourceName=:sourceName")
    fun getChapterInfoRule(sourceName: String): ChapterInfoRule?

    @Query("delete  FROM ChapterInfoRule")
    fun deleteAll()
}