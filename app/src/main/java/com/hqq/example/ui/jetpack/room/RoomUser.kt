package com.hqq.example.ui.jetpack.room

import android.app.Application
import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.room
 * @FileName :   User
 * @Date  : 2020/8/14 0014  下午 5:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@Entity
data class User(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}


@Module
@InstallIn(ApplicationComponent::class)
object UserHitModel {
    @Provides
    @Singleton
    fun getUserDataBase(context: Application): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "dhl.db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun getUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }


}

