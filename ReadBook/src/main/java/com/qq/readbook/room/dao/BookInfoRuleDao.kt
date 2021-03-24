package com.qq.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qq.readbook.bean.book.BookInfoRule

/**
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.room.dao.BookInfoRuleDao.java
 * @emain: 593979591@qq.com
 * @date: 2021-03-15 23:03
 * @version V1.0 <描述当前版本功能>
 *
 */
@Dao
interface BookInfoRuleDao {
    @Insert
    fun insertAll(vararg source: BookInfoRule)

    @Query("select * from BookInfoRule where sourceName=:sourceName")
    fun getBookInfoRule(sourceName: String): BookInfoRule?

    @Query("delete  FROM BookInfoRule")
    fun deleteAll()
}