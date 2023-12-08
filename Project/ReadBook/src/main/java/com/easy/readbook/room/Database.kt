package com.easy.readbook.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easy.readbook.bean.book.ArticleContentRule
import com.easy.readbook.bean.book.BookInfoRule
import com.easy.readbook.bean.book.BookSource
import com.easy.readbook.bean.book.ChapterInfoRule
import com.easy.readbook.bean.book.SearchRule
import com.easy.readbook.room.dao.ArticleContentRuleDao
import com.easy.readbook.room.dao.BookContentDao
import com.easy.readbook.room.dao.BookDao
import com.easy.readbook.room.dao.BookInfoRuleDao
import com.easy.readbook.room.dao.BookRecordDao
import com.easy.readbook.room.dao.BookSourceDao
import com.easy.readbook.room.dao.ChapterDao
import com.easy.readbook.room.dao.ChapterInfoRuleDao
import com.easy.readbook.room.dao.LocalSearchKeyDao
import com.easy.readbook.room.dao.SearchLogDao
import com.easy.readbook.room.dao.SearchRuleDao
import com.easy.readbook.room.entity.Book
import com.easy.readbook.room.entity.BookContent
import com.easy.readbook.room.entity.Chapter
import com.easy.readbook.room.entity.LocalRecord
import com.easy.readbook.room.entity.LocalSearchKey
import com.easy.readbook.room.entity.SearchLog

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.room
 * @Date  : 下午 4:08
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Database(entities = [Book::class, SearchLog::class, LocalRecord::class, LocalSearchKey::class],
    version = 9,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun searchLogDao(): SearchLogDao
    abstract fun bookRecordBeanDao(): BookRecordDao
    abstract fun searchKeyDao(): LocalSearchKeyDao
}

@Database(entities = [BookSource::class, ArticleContentRule::class, BookInfoRule::class, ChapterInfoRule::class, SearchRule::class],
    version = 2,
    exportSchema = false)
abstract class BookSourceDatabase : RoomDatabase() {
    abstract fun bookSourceDao(): BookSourceDao
    abstract fun searchRuleDao(): SearchRuleDao
    abstract fun ruleArticleContentDao(): ArticleContentRuleDao
    abstract fun bookInfoRuleDao(): BookInfoRuleDao
    abstract fun chapterInfoRuleDao(): ChapterInfoRuleDao
}

@Database(entities = [Chapter::class, BookContent::class], version = 2, exportSchema = false)
abstract class ChapterDatabase : RoomDatabase() {
    abstract fun chapterDao(): ChapterDao
    abstract fun bookContentDao(): BookContentDao
}