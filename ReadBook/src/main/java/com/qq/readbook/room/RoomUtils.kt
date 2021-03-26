package com.qq.readbook.room

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hqq.core.CoreConfig
import com.qq.readbook.room.dao.*


/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room
 * @Date : 上午 9:36
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */

/**
 *  1. 一库 多表 表对应书籍
 *  2. 多库 多表   一库对应一本数据
 *  3.  缓存成本地文件 使用文件读取
 */
object RoomUtils {

    /**
     *  核心数据库
     */
    private val bookDatabase: AppDatabase by lazy {
        Room.databaseBuilder(CoreConfig.get().application, AppDatabase::class.java, "books.db")
                //设置是否允许在主线程做查询操作
                .allowMainThreadQueries()
                //设置数据库升级(迁移)的逻辑
                .addMigrations(CHAPTER_1_2,CHAPTER_8_9)
                //默认值是FrameworkSQLiteOpenHelperFactory，设置数据库的factory。比如我们想改变数据库的存储路径可以通过这个函数来实现
//                .openHelperFactory {  }
                //设置迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
//            .fallbackToDestructiveMigration()
                //监听数据库，创建和打开的操作
//            .addCallback()
                .build()
    }

    /**
     *  书源  数据库
     */
    private val bookSourceDatabase: BookSourceDatabase by lazy {
        Room.databaseBuilder(CoreConfig.get().application, BookSourceDatabase::class.java, "BookSource.db")
                .allowMainThreadQueries()
                .addMigrations(BOOK_SOURCE_MIGRATION_1_2)
                .build()
    }

    /**
     *   缓存的 书籍信息 包含 章节列表与内容
     *   分库 比较容易统计书籍缓存内容大小
     */
    private val chapterDatabase = HashMap<String, ChapterDatabase>()

    /**
     *
     * @param name String 书籍名称_作者
     * @return ChapterDatabase
     */
    fun getChapterDataBase(name: String): ChapterDatabase {
        val dbName = "$name.db"
        var dataBase = chapterDatabase.get(dbName)
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(CoreConfig.get().application, ChapterDatabase::class.java, dbName)
                    //设置是否允许在主线程做查询操作
                    .allowMainThreadQueries()
                    //设置数据库升级(迁移)的逻辑
                    .addMigrations()
                    .addMigrations(CHAPTER_1_2)
                    //默认值是FrameworkSQLiteOpenHelperFactory，设置数据库的factory。比如我们想改变数据库的存储路径可以通过这个函数来实现
//                .openHelperFactory {  }
                    //设置迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                    .fallbackToDestructiveMigration()
                    //监听数据库，创建和打开的操作
//                .addCallback()
                    .build()
            chapterDatabase[dbName] = dataBase
        }
        return dataBase
    }

    //region dao
    /**
     * 阅读记录  用记录阅读信息
     * @return BookRecordDao
     */
    fun getBookRecordDao(): BookRecordDao {
        return bookDatabase.bookRecordBeanDao()
    }

    /**
     *  搜索记录列表 保存具体的详情地址 与章节列表
     * @return SearchLogDao
     */
    fun getSearchLogDao(): SearchLogDao {
        return bookDatabase.searchLogDao()
    }

    /**
     *  本地搜索关键字缓存
     * @return LocalSearchKeyDao
     */
    fun getLocalSearchKeyDao(): LocalSearchKeyDao {
        return bookDatabase.searchKeyDao()
    }

    /**
     *  书籍资源信息
     * @return SourceDao
     */
    fun getBookSourceDao(): BookSourceDao {
        return bookSourceDatabase.bookSourceDao()
    }

    /**
     *  具体章节内容
     * @return RuleArticleContentDao
     */
    fun getArticleContentRuleDao(): ArticleContentRuleDao {
        return bookSourceDatabase.ruleArticleContentDao()
    }

    /**
     *  书籍详情规则
     * @return BookInfoRuleDao
     */
    fun getBookInfoRuleDao(): BookInfoRuleDao {
        return bookSourceDatabase.bookInfoRuleDao()
    }

    /**
     * 章节列表规则
     * @return ChapterInfoRuleDao
     */
    fun getChapterInfoRuleDao(): ChapterInfoRuleDao {
        return bookSourceDatabase.chapterInfoRuleDao()
    }

    /**
     * 搜索规则
     * @return SearchRuleDao
     */
    fun getSearchRuleDao(): SearchRuleDao {
        return bookSourceDatabase.searchRuleDao()
    }

    /**
     *  书籍
     * @return BookDao
     */
    fun getBookDao(): BookDao {
        return bookDatabase.bookDao()
    }


    /**
     *  书籍文章 正文
     * @param bookIdName String
     * @return BookContentDao
     */
    fun getBookContentDao(bookIdName: String): BookContentDao {
        return getChapterDataBase(bookIdName).bookContentDao()
    }
    //endregion

    //region Migration

    private val BOOK_SOURCE_MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 添加一个列
            database.execSQL("ALTER TABLE BookSource ADD COLUMN requestHeads TEXT ");
        }
    }

    /**
     *  替换数据库
     */
    private val CHAPTER_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 创建临时表
            database.execSQL("CREATE TABLE BookContent_New (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  number INTEGER ,content Text,sourceName Text)")
            // 拷贝数据
            database.execSQL("INSERT INTO  BookContent_New (id, number ,content ) SELECT  id,number , content FROM BookContent")
            // 删除老的表
            database.execSQL("DROP TABLE BookContent")
            // 改名
            database.execSQL("ALTER TABLE BookContent_New RENAME TO BookContent")
        }
    }
    private val CHAPTER_8_9: Migration = object : Migration(8, 9) {
        override fun migrate(database: SupportSQLiteDatabase) {
        }
    }


    //endregion
}


