package com.qq.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qq.readbook.bean.book.BookSource
import org.jetbrains.annotations.NotNull

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room.dao
 * @Date  : 下午 4:07
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */

@Dao
interface BookSourceDao {
    @Insert
    fun insertAll(vararg sources: BookSource)

    @Query("SELECT  * FROM BookSource  WHERE sourceName=:name LIMIT 1")
    fun getSource4Name(@NotNull name: String): BookSource?

    @Query("SELECT * FROM BookSource ORDER BY weight  DESC ")
    fun getAll(): MutableList<BookSource>

    @Query("delete  FROM BookSource")
    fun deleteAll()
}