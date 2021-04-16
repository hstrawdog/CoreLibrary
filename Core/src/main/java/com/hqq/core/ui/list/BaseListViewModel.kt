package com.hqq.core.ui.list

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
abstract class BaseListViewModel : BaseViewModel() {
    /**
     *   数据集
     */
    var data: MutableLiveData<List<Any>> = MediatorLiveData()

    /**
     *  是否加载失败
     */
    var requestAdapterError = MediatorLiveData<Boolean>()

    var pageCount = 1

    var pageSize = BaseCommonsKey.PAGE_SIZE

    fun setPageSize(pageSize: Int): BaseListViewModel {
        this.pageSize = pageSize
        return this
    }

    fun setPageCount(pageCount: Int): BaseListViewModel {
        this.pageCount = pageCount
        return this
    }

    fun setData(date: List<Any>): BaseListViewModel {
        data.value = date
        return this
    }

    /**
     * 加载更多
     */
    open fun onLoadMore() {}
}