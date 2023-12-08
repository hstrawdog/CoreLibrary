package com.easy.readbook.room.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.weight.page
 * @FileName :   BookRecordBean.kt
 * @Date  : 2020/12/15 0015  上午 8:58
 * @Email :  qiqiang213@gmail.com
 * @Describe : 阅读记录
 */
@Entity
class LocalRecord : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    /**
     * 所属的书的id
     */
    var bookId: String = ""

    /**
     * 阅读到了第几章
     */
    var chapter: Int = 0

    /**
     * 当前的页码
     */
    var pagePos: Int = 0

}
