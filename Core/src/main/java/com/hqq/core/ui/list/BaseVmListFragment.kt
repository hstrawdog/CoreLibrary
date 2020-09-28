package com.hqq.core.ui.list

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.hqq.core.R
import com.hqq.core.ui.base.BaseVmFragment
import com.hqq.core.ui.list.BaseListModel.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListFragment
 * @Date : 2020/7/29 0029  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseVmListFragment<T : ViewDataBinding, K : BaseListViewModel>
    : BaseVmFragment<T, K>(), IBaseListModelView {

    override var listView: RecyclerView? = null

    override val layoutId: Int = R.layout.activity_recycle_view
    override val pageCount: Int get() = viewMode!!.pageCount
    override val pageSize: Int get() = viewMode!!.pageSize

    override lateinit var baseListModel: BaseListModel
    override var rcLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
    override fun initViews() {
        baseListModel = BaseListModel(this, rootViewBuild)
        viewMode!!.data.observe(this, Observer<List<*>> { arrayList ->
            baseListModel!!.fillingData(arrayList as List<Nothing>)
        })
        initData()
    }


    override fun addPageCount() {
        viewMode!!.setPageCount(viewMode!!.pageCount + 1)
    }

    override fun onRefreshBegin() {
        if (baseAdapter is LoadMoreModule) {
            viewMode!!.setPageCount(1)
            baseAdapter.loadMoreModule.loadMoreComplete()
            viewMode!!.onLoadMore()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onLoadMore() {
        viewMode!!.onLoadMore()

    }

}


