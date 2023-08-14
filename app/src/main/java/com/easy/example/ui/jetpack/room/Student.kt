package com.easy.example.ui.jetpack.room

import androidx.room.*

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.room
 * @FileName :   Student
 * @Date  : 2020/8/18 0018  上午 11:14
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@Entity
data class Student(val grade: String,
                   val age: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface StudentDao {
    @Query("SELECT * FROM Student")
    fun getAll(): List<Student>

    @Insert
    fun insertAll(vararg student: Student)
}
