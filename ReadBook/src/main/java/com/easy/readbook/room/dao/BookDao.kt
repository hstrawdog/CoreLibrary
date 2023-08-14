package com.easy.readbook.room.dao

import androidx.room.*
import com.easy.readbook.room.entity.Book

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room.dao
 * @Date  : 下午 4:05
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Dao
interface BookDao {
    @Insert
    fun insertAll(vararg book: com.easy.readbook.room.entity.Book)

    @Delete
    fun delete(book: com.easy.readbook.room.entity.Book)

    @Query("delete FROM Book where name=:bookName And author=:author")
    fun deleteBook(bookName: String, author: String)

    @Update
    fun update(book: com.easy.readbook.room.entity.Book)

    @Query("SELECT * FROM Book WhERE localType=1  ORDER BY topTime DESC ,lastRead DESC")
    fun getFollowAll(): List<com.easy.readbook.room.entity.Book>

    @Query("SELECT * FROM Book  ORDER BY lastRead DESC")
    fun getAll(): List<com.easy.readbook.room.entity.Book>

    @Query("SELECT * FROM Book where bookId =:bookId")
    fun getBookById(bookId: String): com.easy.readbook.room.entity.Book?

}