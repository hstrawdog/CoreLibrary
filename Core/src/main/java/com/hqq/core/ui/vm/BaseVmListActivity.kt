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
 * @FileName :   BaseListActivity
 * @Date : 2020/7/29 0029  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * BaseListViewModel  包含 pageSize  PageCount 以及驱动列表的 mData
 */
abstract class BaseVmListActivity<T : ViewDataBinding?, K : BaseListViewModel?, AD : BaseQuickAdapter<*, *>?> : BaseVmActivity<T, K>(), IBaseListModelView<AD> {
    @JvmField
    protected var mRcList: RecyclerView? = null

    @JvmField
    protected var mAdapter: AD? = null
    protected var mLayoutManager: RecyclerView.LayoutManager? = null
    protected var mBaseListModel: BaseListModelView? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view
    }

    override fun initViews() {
        mBaseListModel = BaseListModelView(this, this)
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
        mViewModel!!.pageCount = mViewModel!!.pageCount + 1
    }

    override fun onRefreshBegin() {
        mViewModel!!.pageCount = 1
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
        return LinearLayoutManager(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {

    }
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {

    }
}