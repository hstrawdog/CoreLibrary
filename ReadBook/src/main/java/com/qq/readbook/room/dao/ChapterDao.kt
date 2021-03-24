package com.qq.readbook.room.dao

import androidx.room.*
import com.qq.readbook.room.entity.Chapter

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room.dao
 * @Date  : 下午 4:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */

@Dao
interface ChapterDao {
    @Query("SELECT * FROM Chapter WHERE sources =:sources")
    fun getAll(sources: String): List<Chapter>

    @Insert
    fun insertAll(vararg chapter: Chapter)

    @Query("delete  FROM Chapter")
    fun deleteAll()

    @Query("delete  FROM sqlite_sequence WHERE name = 'Chapter'")
    fun resetId()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chapter: ArrayList<Chapter>)

    @Update
    fun update(chapter: Chapter): Int
}
