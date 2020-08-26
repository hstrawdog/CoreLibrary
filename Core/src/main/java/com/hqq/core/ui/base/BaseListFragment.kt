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
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseListFragment<T : BaseQuickAdapter<*, *>?> :
        BaseFragment(), IBaseListModelView<T?> {

    // Fragment 的用法与 Activity保持一致  注释

    var mBaseListModel: BaseListModelView? = null

    override var listView: RecyclerView? = null

    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var pageCount = 1

    override val layoutViewId: Int = 0;

    override val rcLayoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(activity)

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
        initData()
    }

    override fun addPageCount() {
        pageCount += 1
    }

    override fun onRefreshBegin() {
        pageCount = 1
        baseAdapter?.loadMoreModule?.loadMoreComplete()
        onLoadMore()
    }

    override fun onLoadMore() {
    }

}