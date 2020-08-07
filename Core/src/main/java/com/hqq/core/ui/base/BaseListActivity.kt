package com.hqq.core.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.model.BaseListModelView
import com.hqq.core.ui.model.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseListActivity
 * @Date : 2018/11/5 0005  下午 4:23
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseListActivity<T : BaseQuickAdapter<*, *>?> : BaseActivity(), RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, IBaseListModelView<T?> {
    @kotlin.jvm.JvmField
    protected var mRcList: RecyclerView? = null
    override var adapter: T? = null
        protected set
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 重写了 setViewId    执行 setRootView
     * 默认都会执行一般这个方法 但是没有添加到rooView中
     *
     * @return
     */
    override fun getLayoutView(group: ViewGroup): View? {
        return if (layoutViewId <= 0) {
            createRecycleView(this)
        } else {
            null
        }
    }

    /**
     * 初始化  基础数据
     */
    @CallSuper
    override fun initView() {
        mBaseListModel = BaseListModelView(this, this)
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

    override val isShowLoadMore: Boolean
        get() = false

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {}
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {}

    override fun addPageCount() {
        pageCount = pageCount + 1
    }

    override fun onRefreshBegin() {
        pageCount = 1
        adapter!!.loadMoreComplete()
        onLoadMore()
    }

    override val listView: ViewGroup?
        get() = mRcList

    override fun onDestroy() {
        super.onDestroy()
        mBaseListModel!!.onDestroy()
        mBaseListModel = null
    }

    override val rcLayoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(this)

    protected open fun onLoadMore() {}
}