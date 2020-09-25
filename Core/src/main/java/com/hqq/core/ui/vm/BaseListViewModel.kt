package com.hqq.core.ui.vm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.base.BaseViewModel

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListModel
 * @Date : 2020/7/29 0029  上午 10:28
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseListViewModel<B> : BaseViewModel() {
    var data: MutableLiveData<List<B>> = MediatorLiveData()
    var pageCount = 1
    var pageSize = BaseCommonsKey.PAGE_SIZE

    /**
     * 加载更多
     */
    open fun onLoadMore() {}

    fun setPageSize(pageSize: Int): BaseListViewModel <B>{
        this.pageSize = pageSize
        return this
    }

    fun setPageCount(pageCount: Int): BaseListViewModel<B> {
        this.pageCount = pageCount
        return this
    }


    fun setData(date: List<B>): BaseListViewModel<B> {
        data.value = date
        return this
    }
}