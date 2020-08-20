package com.hqq.core.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.model.BaseListModelView
import com.hqq.core.ui.model.BaseListModelView.Companion.createRecycleView
import com.hqq.core.ui.model.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseListFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseListFragment<T : BaseQuickAdapter<*, *>?> :
        BaseFragment(), IBaseListModelView<T?> {
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
    override val layoutViewId: Int = 0;

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
        mRcList = mBaseListModel!!.checkRecycleView(mRcList, rootViewBuild?.rootView)
        mBaseListModel!!.initRecycleView(mRcList, adapter, mLayoutManager)
        mBaseListModel!!.initPtrPullDown(rootViewBuild?.rootView)
        initData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

    override fun addPageCount() {
        pageCount++
    }

    override fun onRefreshBegin() {
        pageCount = 1
        adapter!!.loadMoreModule.loadMoreComplete()
        onLoadMore()
    }

    override val listView: ViewGroup?
        get() = mRcList


    override val rcLayoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(activity)

    override fun onLoadMore() {
    }

}