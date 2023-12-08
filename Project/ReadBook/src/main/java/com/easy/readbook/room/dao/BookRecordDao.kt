package com.easy.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.easy.readbook.room.entity.LocalRecord


/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.room.dao
 * @Date  : 下午 4:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Dao
interface BookRecordDao {

    @Query("SELECT * FROM LocalRecord ")
    fun getAll(): List<LocalRecord>

    @Query("SELECT * FROM LocalRecord WHERE  bookId =:bookId")
    fun getBookRecord4BookId(bookId: String): List<LocalRecord>

    @Insert
    fun insert(localRecordDao: LocalRecord)

    @Update
    fun update(localRecordDao: LocalRecord): Int
}
