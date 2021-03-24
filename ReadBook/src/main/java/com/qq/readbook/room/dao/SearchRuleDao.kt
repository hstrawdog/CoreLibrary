package com.qq.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qq.readbook.bean.book.SearchRule
import com.qq.readbook.room.entity.SearchLog

/**
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.room.dao.SearchRuleDao.java
 * @emain: 593979591@qq.com
 * @date: 2021-03-15 23:01
 * @version V1.0 <描述当前版本功能>
 *
 */
@Dao
interface SearchRuleDao {
    @Insert
    fun insertAll(vararg source: SearchRule)

    @Query("select * fROM SearchRule where  sourceName=:sourceName")
    fun getRuleSearch(sourceName: String): SearchRule?

    @Query("delete  FROM SearchRule")
    fun deleteAll()
}