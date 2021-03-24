package com.qq.readbook.room.dao

import androidx.room.*
import com.qq.readbook.room.entity.BookContent

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room.dao
 * @Date  : 下午 4:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Dao
interface BookContentDao {
    @Query("SELECT * FROM BookContent where number =:number")
    fun getContent(number: Int): BookContent?

    @Update
    fun update(bookContent: BookContent): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookContent: BookContent)
}