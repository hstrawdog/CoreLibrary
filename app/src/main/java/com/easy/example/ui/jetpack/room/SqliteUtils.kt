package com.easy.example.ui.jetpack.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.room
 * @FileName :   SqliteUtils
 * @Date  : 2020/8/18 0018  上午 11:16
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * 参考
 * https://www.jianshu.com/p/3e358eb9ac43
 */
@Database(entities = [User::class, Student::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
}

@Module
@InstallIn(SingletonComponent::class)
object UserHitModel {
    @Provides
    @Singleton
    fun getUserDataBase(context: Application): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "dh.db"
        )
                //设置是否允许在主线程做查询操作
                .allowMainThreadQueries()
                //设置数据库升级(迁移)的逻辑
                .addMigrations(MIGRATION_1_2)
                //默认值是FrameworkSQLiteOpenHelperFactory，设置数据库的factory。比如我们想改变数据库的存储路径可以通过这个函数来实现
//                .openHelperFactory {  }
                //设置迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
//                .fallbackToDestructiveMigration()
                //监听数据库，创建和打开的操作
//                .addCallback()
                .build()

    }

    @Provides
    @Singleton
    fun getUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun getStudentDao(appDatabase: AppDatabase): StudentDao {
        return appDatabase.studentDao()
    }

    /**
     *  数据库版本升级使用的
     */
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
        }
    }
}


