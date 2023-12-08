package com.easy.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.easy.readbook.room.entity.SearchLog

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.room.dao
 * @Date  : 下午 4:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Dao
interface SearchLogDao {
    @Query("SELECT * FROM SearchLog  ")
    fun getAll(): List<com.easy.readbook.room.entity.SearchLog>

    @Query("SELECT * FROM SearchLog WHERE sourcesName =:sourcesName AND bookId =:bookId")
    fun getBookSource(sourcesName: String, bookId: String): com.easy.readbook.room.entity.SearchLog?

    @Insert
    fun insertAll(vararg source: com.easy.readbook.room.entity.SearchLog)

    @Update
    fun update(source: com.easy.readbook.room.entity.SearchLog)
}