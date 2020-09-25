package com.hqq.core.ui.vm

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.R
import com.hqq.core.ui.base.BaseListModelView
import com.hqq.core.ui.base.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListFragment
 * @Date : 2020/7/29 0029  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseVmListFragment<T : ViewDataBinding, K : BaseListViewModel<B>, B>
    : BaseVmFragment<T, K>(), IBaseListModelView<B> {

    override var listView: RecyclerView? = null

    override val layoutId: Int = R.layout.activity_recycle_view
    override val pageCount: Int get() = viewMode!!.pageCount
    override val pageSize: Int get() = viewMode!!.pageSize

    override lateinit var baseListModel: BaseListModelView<*>
    override val rcLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
    override fun initViews() {
        baseListModel = BaseListModelView(this, rootViewBuild)
        viewMode!!.data.observe(this, Observer<List<*>> { arrayList ->
            baseListModel!!.fillingData(arrayList as List<Nothing>)
        })
        initData()
    }


    override fun addPageCount() {
        viewMode!!.setPageCount(viewMode!!.pageCount + 1)
    }

    override fun onRefreshBegin() {
        viewMode!!.setPageCount(1)
        baseAdapter!!.loadMoreModule.loadMoreComplete()
        viewMode!!.onLoadMore()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onLoadMore() {
        viewMode!!.onLoadMore()

    }

}


