package com.hqq.core.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.base.BaseFragment
import com.hqq.core.ui.list.BaseListModel.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseListFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseListFragment :
        BaseFragment(), IBaseListModelView {

    // Fragment 的用法与 Activity保持一致  注释


    override var listView: RecyclerView? = null

    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var pageCount = 1

    override val layoutViewId: Int = 0;

    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

    override lateinit var listModel: BaseListModel

    override fun getLayoutView(group: ViewGroup): View? {
        return if (layoutViewId <= 0) {
            context?.let {
                BaseListModel.createRecycleView(it)
            }
        } else {
            null
        }
    }

    override fun initView() {
        listModel = BaseListModel(this, rootViewImpl)
        initData()
    }

    override fun addPageCount() {
        pageCount += 1
    }

    override fun onRefreshBegin() {
        if (adapter is LoadMoreModule) {

            pageCount = 1
            adapter.loadMoreModule.loadMoreComplete()
            onLoadMore()
        }
    }

    override fun onLoadMore() {
    }


}