package com.hqq.core.ui.vm

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.R
import com.hqq.core.ui.base.BaseListModelView
import com.hqq.core.ui.base.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListActivity
 * @Date : 2020/7/29 0029  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * BaseListViewModel  包含 pageSize  PageCount 以及驱动列表的 mData
 */
abstract class BaseVmListActivity<T : ViewDataBinding, K : BaseListViewModel<B>, B>
    : BaseVmActivity<T, K>(), IBaseListModelView<B> {
    private var mBaseListModel: BaseListModelView<B>? = null

    override val layoutId: Int
        get() = R.layout.activity_recycle_view

    override val pageCount: Int
        get() = viewMode!!.pageCount

    override val pageSize: Int
        get() = viewMode!!.pageSize

    override var listView: RecyclerView? = null

    override val rcLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
    override lateinit var baseListModel: BaseListModelView<*>

    override fun initViews() {
        mBaseListModel = BaseListModelView(this, iCreateRootView)
        viewMode?.data?.observe(this, Observer {
            mBaseListModel?.fillingData(it)
        })
        initData()
    }


    override fun addPageCount() {
        viewMode!!.pageCount = viewMode!!.pageCount + 1
    }

    override fun onRefreshBegin() {
        viewMode?.pageCount = 1
        baseAdapter?.loadMoreModule.loadMoreComplete()
        viewMode?.onLoadMore()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBaseListModel = null
    }

    override fun onLoadMore() {
        viewMode?.onLoadMore()
    }


}