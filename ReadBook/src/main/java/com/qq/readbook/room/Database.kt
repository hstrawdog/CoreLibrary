package com.qq.readbook.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qq.readbook.bean.book.*
import com.qq.readbook.room.dao.*
import com.qq.readbook.room.entity.*

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room
 * @Date  : 下午 4:08
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Database(entities = [Book::class, SearchLog::class, LocalRecord::class, LocalSearchKey::class],
    version = 8,
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