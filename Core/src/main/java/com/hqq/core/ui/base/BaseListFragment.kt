package com.hqq.core.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.base.BaseListModelView.IBaseListModelView

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

    override var listView: RecyclerView? = null

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
            context?.let {
                BaseListModelView.createRecycleView(it)
            }
        } else {
            null
        }
    }

    override fun initView() {
        mBaseListModel = BaseListModelView(this, rootViewBuild)
        mLayoutManager = rcLayoutManager
        initData()
    }


    override fun addPageCount() {
        pageCount++
    }

    override fun onRefreshBegin() {
        pageCount = 1
        baseAdapter!!.loadMoreModule.loadMoreComplete()
        onLoadMore()
    }


    override val rcLayoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(activity)

    override fun onLoadMore() {
    }

}