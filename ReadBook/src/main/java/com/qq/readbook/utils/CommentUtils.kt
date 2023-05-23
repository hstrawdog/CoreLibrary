package com.qq.readbook.utils

import com.hqq.core.utils.DateUtils
import com.hqq.core.utils.TimeTool
import com.qq.readbook.room.entity.Book

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.utils
 * @Date : 下午 2:44
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object CommentUtils {
    fun isRefresh(book: Book, searchDetail: Int): Boolean {
        if (searchDetail == 2) {
            if (System.currentTimeMillis() - TimeTool.string2Millisecond(book.refreshTime) > 1000 * 60) {
                return true
            }
        }
        return false
    }

}