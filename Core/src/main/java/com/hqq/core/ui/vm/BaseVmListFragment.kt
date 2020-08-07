package com.hqq.core.ui.vm

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.R
import com.hqq.core.ui.model.BaseListModelView
import com.hqq.core.ui.model.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListFragment
 * @Date : 2020/7/29 0029  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseVmListFragment<T : ViewDataBinding?, K : BaseListViewModel?, AD : BaseQuickAdapter<*, *>?> : BaseVmFragment<T, K>(), IBaseListModelView<AD> {
    protected var mRcList: RecyclerView? = null
    protected var mAdapter: AD? = null
    protected var mLayoutManager: RecyclerView.LayoutManager? = null
    protected var mBaseListModel: BaseListModelView? = null
    override fun addViewModel() {}
    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view
    }

    override fun initViews() {
        mBaseListModel = BaseListModelView(this, context)
        mLayoutManager = rcLayoutManager
        mAdapter = initAdapter()
        mRcList = mBaseListModel!!.checkRecycleView(mRcList, mRootViewBuild.rootView)
        mBaseListModel!!.initRecycleView(mRcList, mAdapter, mLayoutManager)
        mBaseListModel!!.initPtrPullDown(mRootViewBuild.rootView)
        mViewModel!!.mDate.observe(this, Observer { arrayList -> mBaseListModel!!.fillingData(arrayList) })
        initData()
    }

    override fun onLoadMoreRequested() {
        mViewModel!!.onLoadMore()
    }

    override fun isShowLoadMore(): Boolean {
        return false
    }

    override fun getAdapter(): AD {
        return mAdapter!!
    }

    override fun getPageCount(): Int {
        return mViewModel!!.pageCount
    }

    override fun getPageSize(): Int {
        return mViewModel!!.pageSize
    }

    override fun addPageCount() {
        mViewModel!!.setPageCount(mViewModel!!.pageCount + 1)
    }

    override fun onRefreshBegin() {
        mViewModel!!.setPageCount(1)
        mAdapter!!.loadMoreComplete()
        mViewModel!!.onLoadMore()
    }

    override fun getListView(): ViewGroup {
        return mRcList!!
    }

    override fun onDestroy() {
        super.onDestroy()
        mBaseListModel!!.onDestroy()
        mBaseListModel = null
    }

    override fun getRcLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {}
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {}
}