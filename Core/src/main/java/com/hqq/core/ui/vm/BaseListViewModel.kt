package com.hqq.core.ui.vm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hqq.core.BaseCommonsKey

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListModel
 * @Date : 2020/7/29 0029  上午 10:28
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseListViewModel : BaseViewModel() {
    var mDate: MutableLiveData<List<*>> = MediatorLiveData()
    var pageCount = 1
        get
        set
    var pageSize = BaseCommonsKey.PAGE_SIZE
        set

    /**
     * 加载更多
     */
    open fun onLoadMore() {}

    fun setPageSize(pageSize: Int): BaseListViewModel {
        this.pageSize = pageSize
        return this
    }

    fun setPageCount(pageCount: Int): BaseListViewModel {
        this.pageCount = pageCount
        return this
    }

    val date: List<*>
        get() = mDate.value!!

    fun setDate(date: List<*>): BaseListViewModel {
        mDate.value = date
        return this
    }
}