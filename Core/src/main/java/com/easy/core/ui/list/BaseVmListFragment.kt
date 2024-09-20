package com.easy.core.ui.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.loadState.LoadState
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
    /**
     *  是否允许加载更多
     */
    override var isLoadMore: Boolean = true
    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view
    }
    override val pageCount: Int get() = viewModel.pageCount

    override val pageSize: Int get() = viewModel.pageSize

    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

    override lateinit var listModel: BaseListModel

    override fun initViews() {
        listModel = BaseListModel(this, rootViewImpl)
        LiveEventObserver.bind(viewModel.data, this) { arrayList ->
            listModel.fillingData(arrayList as ArrayList<Nothing>)
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
            viewModel.setPageCount(1)
        listModel.helper?.leadingLoadState= LoadState.NotLoading(true)

        viewModel.onLoadMore()
    }

    override fun onLoadMore() {
        viewModel.onLoadMore()
    }

    override fun onDestroy() {
        super.onDestroy()


    }
}


