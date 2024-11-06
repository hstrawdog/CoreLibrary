package com.easy.core.ui.list3

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.easy.core.R
import com.easy.core.lifecycle.LiveEventObserver
import com.easy.core.ui.base.BaseVmFragment
import com.easy.core.ui.list.BaseListModel.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.vm
 * @FileName :   BaseListFragment
 * @Date : 2020/7/29 0029  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseVmListFragment<K : BaseListViewModel, T : ViewDataBinding> : BaseVmFragment<K, T>(),
    BaseListModel3.IBaseListModelView3 {

    override var listView: RecyclerView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view
    }
    override val pageCount: Int get() = viewModel.pageCount

    override val pageSize: Int get() = viewModel.pageSize

    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

    override lateinit var listModel: BaseListModel3

    override fun initViews() {
        listModel = BaseListModel3(this, rootViewImpl)
        LiveEventObserver.bind(viewModel.data, this) { arrayList ->
            listModel.fillingData(arrayList)
        }
        LiveEventObserver.bind(viewModel.requestAdapterError, this) { arrayList ->
            listModel.loadMoreError()
        }
        initData()
    }

    override fun addPageCount() {
        viewModel.setPageCount(viewModel.pageCount + 1)
    }

    override fun onRefreshBegin() {
        if (adapter is LoadMoreModule) {
            viewModel.setPageCount(1)
            adapter.loadMoreModule.loadMoreComplete()
            viewModel.onLoadMore()
        }
    }

    override fun onLoadMore() {
        viewModel.onLoadMore()
    }

}


