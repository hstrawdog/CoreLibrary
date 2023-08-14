package com.easy.readbook.room.dao

import androidx.room.*
import com.easy.readbook.room.entity.BookContent

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
    fun getContent(number: Int): com.easy.readbook.room.entity.BookContent?

    @Update
    fun update(bookContent: com.easy.readbook.room.entity.BookContent): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookContent: com.easy.readbook.room.entity.BookContent)
}