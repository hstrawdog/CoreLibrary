package com.easy.core.ui.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.easy.core.BaseCommonsKey
import com.easy.core.ui.base.BaseViewModel

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.vm
 * @FileName :   BaseListModel
 * @Date : 2020/7/29 0029  上午 10:28
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseListViewModel : BaseViewModel() {
    /**
     *   数据集
     */
    var data: MutableLiveData<List<*>> = MediatorLiveData()

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

    fun setData(date: List<*>): BaseListViewModel {
        data.value = date
        return this
    }

    /**
     * 加载更多
     */
    open fun onLoadMore() {}
}