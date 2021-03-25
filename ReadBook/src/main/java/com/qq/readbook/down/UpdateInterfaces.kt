package com.qq.readbook.down

import com.qq.readbook.room.entity.Book
import java.util.HashMap

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.down
 * @Date : 上午 9:40
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface UpdateInterfaces {


    /**
     *  最大线程数
     */
    var maxThread: Int

    /**
     *  需要加载的书籍列表
     */
    var needLoadBookMap: HashMap<String, Book>

    /**
     *  正在加载的书籍
     */
    var LoadBookMap: HashMap<String, Book>


    /**
     * 添加需要处理的 书籍
     *
     * @param book
     */
    fun handlerBook(book: Book)

    /**
     * 下一本书
     *
     * @return
     */
    fun nextBookBook(): Book?

    /**
     * 执行下一个请求
     */
    fun executeNextRequest()

    /**
     *  执行请求
     * @param book Book
     */
    fun executeRequest(book: Book)

    /**
     *  加载成功
     */
    fun loadSuccess(book: Book?)

}