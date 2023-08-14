package com.easy.readbook.room.dao

import androidx.room.*
import com.easy.readbook.room.entity.Chapter

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
    fun getAll(sources: String): List<com.easy.readbook.room.entity.Chapter>

    @Insert
    fun insertAll(vararg chapter: com.easy.readbook.room.entity.Chapter)

    @Query("delete  FROM Chapter")
    fun deleteAll()

    @Query("delete  FROM sqlite_sequence WHERE name = 'Chapter'")
    fun resetId()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chapter: ArrayList<com.easy.readbook.room.entity.Chapter>)

    @Update
    fun update(chapter: com.easy.readbook.room.entity.Chapter): Int
}
