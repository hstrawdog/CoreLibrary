package com.easy.core.ui.list3

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.easy.core.BaseCommonsKey
import com.easy.core.ui.base.BaseFragment
import com.easy.core.ui.list.BaseListModel.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   BaseListFragment
 * @Date : 2018/11/23 0023  上午 11:47
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseListFragment : BaseFragment(), BaseListModel3.IBaseListModelView3 {

    // Fragment 的用法与 Activity保持一致  注释


    override var listView: RecyclerView? = null

    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var pageCount = 1

    override fun getLayoutViewId(): Int {
        return  0
    }
    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

    override lateinit var listModel: BaseListModel3

    override fun getLayoutView(group: ViewGroup): View? {
        return if (getLayoutViewId() <= 0) {
            context?.let {
                BaseListModel3.createRecycleView(it)
            }
        } else {
            null
        }
    }

    override fun initView() {
        listModel = BaseListModel3(this, rootViewImpl)
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