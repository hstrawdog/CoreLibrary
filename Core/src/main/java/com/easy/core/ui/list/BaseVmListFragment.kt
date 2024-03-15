package com.easy.core.ui.list

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
    IBaseListModelView {

    override var listView: RecyclerView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view
    }
    override val pageCount: Int get() = viewMode.pageCount

    override val pageSize: Int get() = viewMode.pageSize

    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

    override lateinit var listModel: BaseListModel

    override fun initViews() {
        listModel = BaseListModel(this, rootViewImpl)
        LiveEventObserver.bind(viewMode.data, this) { arrayList ->
            listModel.fillingData(arrayList)
        }
        LiveEventObserver.bind(viewMode.requestAdapterError, this) { arrayList ->
            listModel.loadMoreError()
        }
        initData()
    }

    override fun addPageCount() {
        viewMode.setPageCount(viewMode.pageCount + 1)
    }

    override fun onRefreshBegin() {
        if (adapter is LoadMoreModule) {
            viewMode.setPageCount(1)
            adapter.loadMoreModule.loadMoreComplete()
            viewMode.onLoadMore()
        }
    }

    override fun onLoadMore() {
        viewMode.onLoadMore()
    }

}


