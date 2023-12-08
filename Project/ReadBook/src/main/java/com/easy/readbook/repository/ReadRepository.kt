package com.easy.readbook.repository

import com.easy.readbook.room.RoomUtils
import com.easy.readbook.room.entity.Book
import com.easy.readbook.room.entity.LocalRecord

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.repository
 * @Date : 下午 5:01
 * @Email : qiqiang213@gmail.com
 * @Describe : 阅读记录
 */
object ReadRepository {
    @JvmStatic
    fun getBookRecord(book: Book): LocalRecord? {
        val list = RoomUtils.getBookRecordDao().getBookRecord4BookId(book.bookId)
        if (list.isNotEmpty()) {
            return list.first()
        }
        return null
    }

    @JvmStatic
    fun saveBookRecord(book: Book, localRecord: LocalRecord) {
        RoomUtils.getBookRecordDao().apply {
            val list = getBookRecord4BookId(book.bookId)
            if (list.isNotEmpty()) {
                val record = list.first()
                localRecord.id = record.id
                update(localRecord)
            } else {
                insert(localRecord)
            }
        }

    }
}