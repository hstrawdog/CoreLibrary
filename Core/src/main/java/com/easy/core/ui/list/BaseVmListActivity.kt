package com.easy.core.ui.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.easy.core.R
import com.easy.core.lifecycle.LiveEventObserver
import com.easy.core.ui.base.BaseVmActivity
import com.easy.core.ui.list.BaseListModel.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.vm
 * @FileName :   BaseListActivity
 * @Date : 2020/7/29 0029  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * BaseListViewModel  包含 pageSize  PageCount 以及驱动列表的 mData
 */
abstract class BaseVmListActivity<K : BaseListViewModel, T : ViewDataBinding> :
    BaseVmActivity<K, T>(), IBaseListModelView {

    override fun getLayoutId(): Int {
        return  R.layout.activity_recycle_view
    }
    override val pageCount: Int
        get() = viewModel.pageCount

    override val pageSize: Int get() = viewModel.pageSize

    override var listView: RecyclerView? = null

    override lateinit var layoutManager: RecyclerView.LayoutManager

    override lateinit var listModel: BaseListModel

    override fun initViews() {
        layoutManager = LinearLayoutManager(activity)
        listModel = BaseListModel(this, rootViewImpl)
        LiveEventObserver.bind(viewModel.data, this) { arrayList ->
            listModel.fillingData(arrayList)
        }
        LiveEventObserver.bind(viewModel.requestAdapterError, this) { arrayList ->
            listModel.loadMoreError()
        }
        initData()
    }

    override fun addPageCount() {
        viewModel.pageCount = viewModel.pageCount + 1
    }

    override fun onRefreshBegin() {
        if (adapter is LoadMoreModule) {
            viewModel.pageCount = 1
            adapter.loadMoreModule.loadMoreComplete()
            viewModel.onLoadMore()
        }
    }

    override fun onLoadMore() {
        viewModel.onLoadMore()
    }


}