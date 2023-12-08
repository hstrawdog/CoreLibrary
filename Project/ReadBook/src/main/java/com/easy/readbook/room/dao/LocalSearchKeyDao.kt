package com.easy.readbook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.easy.readbook.room.entity.LocalSearchKey

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.room.dao
 * @Date  : 下午 4:07
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Dao
interface LocalSearchKeyDao {
    @Insert
    fun insertAll(vararg localSearchKeyDao: com.easy.readbook.room.entity.LocalSearchKey)

    @Query("SELECT * FROM LocalSearchKey ORDER BY searchTime  DESC ")
    fun getAll(): List<com.easy.readbook.room.entity.LocalSearchKey>

    @Update
    fun update(localSearchKeyDao: com.easy.readbook.room.entity.LocalSearchKey): Int

    @Query("delete  FROM LocalSearchKey")
    fun deleteAll()

    @Query("delete  FROM sqlite_sequence WHERE name = 'SearchLog'")
    fun resetId()

    @Query("SELECT * FROM LocalSearchKey WHERE `key`=:key")
    fun getLog4Key(key: String): List<com.easy.readbook.room.entity.LocalSearchKey>
}
