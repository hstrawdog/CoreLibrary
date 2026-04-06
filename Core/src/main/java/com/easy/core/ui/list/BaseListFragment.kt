package com.easy.core.ui.list

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.loadState.LoadState
import com.easy.core.BaseCommonsKey
import com.easy.core.R
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
abstract class BaseListFragment : BaseFragment(), IBaseListModelView {

    // Fragment 的用法与 Activity保持一致  注释
    /**
     *  是否允许加载更多
     */
    override var isLoadMore: Boolean = true
    override var listView: RecyclerView? = null

    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var pageCount = 1

    override fun getLayoutViewId(): Int {
        return 0
    }

    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

    override lateinit var listModel: BaseListModel

    override fun getLayoutView(group: ViewGroup): View? {
        return if (getLayoutViewId() <= 0) {
            context?.let { createDefaultRecyclerView(it) }
        } else {
            null
        }
    }

    protected open fun createDefaultRecyclerView(context: android.content.Context): RecyclerView {
        return RecyclerView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            id = R.id.rc_list
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
        pageCount = 1
        listModel.helper?.trailingLoadState = LoadState.NotLoading(true)
        onLoadMore()
    }

    override fun onLoadMore() {
    }


}
