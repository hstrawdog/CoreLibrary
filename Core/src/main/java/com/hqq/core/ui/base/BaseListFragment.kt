package com.hqq.core.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.model.BaseListModelView
import com.hqq.core.ui.model.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseListFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseListFragment<T : BaseQuickAdapter<*, *>?> : BaseFragment(), RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, IBaseListModelView<T?> {
    protected var mRcList: RecyclerView? = null
    override var adapter: T? = null
        protected set

    /**
     * 默认的填充数据
     *
     * @param data
     */
    override var pageSize = BaseCommonsKey.PAGE_SIZE
        protected set
    override var pageCount = 1
        protected set
    protected var mLayoutManager: RecyclerView.LayoutManager? = null
    @kotlin.jvm.JvmField
    protected var mBaseListModel: BaseListModelView? = null
    override fun getLayoutViewId(): Int {
        return 0
    }

    override fun getLayoutView(group: ViewGroup): View? {
        return if (layoutViewId <= 0) {
            createRecycleView(context)
        } else {
            null
        }
    }

    override fun initView() {
        mBaseListModel = BaseListModelView(this, context)
        mLayoutManager = rcLayoutManager
        adapter = initAdapter()
        mRcList = mBaseListModel!!.checkRecycleView(mRcList, mRootViewBuild.getRootView())
        mBaseListModel!!.initRecycleView(mRcList, adapter, mLayoutManager)
        mBaseListModel!!.initPtrPullDown(mRootViewBuild.getRootView())
        initData()
    }

    override fun onLoadMoreRequested() {
        onLoadMore()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {}
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {}

    override fun addPageCount() {
        pageCount++
    }

    override fun onRefreshBegin() {
        pageCount = 1
        adapter!!.loadMoreComplete()
        onLoadMore()
    }

    override val listView: ViewGroup?
        get() = mRcList

    override val isShowLoadMore: Boolean
        get() = false

    override val rcLayoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(mActivity)

    /**
     * 加载更过数据
     */
    protected open fun onLoadMore() {}
}