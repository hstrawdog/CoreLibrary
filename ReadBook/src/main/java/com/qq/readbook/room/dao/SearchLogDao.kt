package com.qq.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.qq.readbook.room.entity.SearchLog

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room.dao
 * @Date  : 下午 4:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Dao
interface SearchLogDao {
    @Query("SELECT * FROM SearchLog  ")
    fun getAll(): List<SearchLog>

    @Query("SELECT * FROM SearchLog WHERE sourcesName =:sourcesName AND bookId =:bookId")
    fun getBookSource(sourcesName: String, bookId: String): SearchLog?

    @Insert
    fun insertAll(vararg source: SearchLog)

    @Update
    fun update(source: SearchLog)
}