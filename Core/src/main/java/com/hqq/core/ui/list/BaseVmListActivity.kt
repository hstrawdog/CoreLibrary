package com.hqq.core.ui.list

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.hqq.core.R
import com.hqq.core.lifecycle.LiveEventObserver
import com.hqq.core.ui.base.BaseVmActivity
import com.hqq.core.ui.list.BaseListModel.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListActivity
 * @Date : 2020/7/29 0029  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * BaseListViewModel  包含 pageSize  PageCount 以及驱动列表的 mData
 */
abstract class BaseVmListActivity<K : BaseListViewModel, T : ViewDataBinding> : BaseVmActivity<K, T>(), IBaseListModelView {

    override val layoutId: Int = R.layout.activity_recycle_view

    override val pageCount: Int = viewMode.pageCount

    override val pageSize: Int = viewMode.pageSize

    override var listView: RecyclerView? = null

    override lateinit var layoutManager: RecyclerView.LayoutManager

    override lateinit var listModel: BaseListModel

    override fun initViews() {
        layoutManager = LinearLayoutManager(activity)
        listModel = BaseListModel(this, rootViewImpl)
        LiveEventObserver.bind(viewMode.data, this) { arrayList ->
            listModel.fillingData(arrayList )
        }
        LiveEventObserver.bind(viewMode.requestAdapterError, this ){ arrayList ->
            listModel.loadMoreError()
        }
        initData()
    }

    override fun addPageCount() {
        viewMode.pageCount = viewMode.pageCount + 1
    }

    override fun onRefreshBegin() {
        if (adapter is LoadMoreModule) {
            viewMode.pageCount = 1
            adapter.loadMoreModule.loadMoreComplete()
            viewMode.onLoadMore()
        }
    }

    override fun onLoadMore() {
        viewMode.onLoadMore()
    }


}