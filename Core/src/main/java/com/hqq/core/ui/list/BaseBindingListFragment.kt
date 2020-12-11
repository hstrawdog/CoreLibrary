package com.hqq.core.ui.list

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.hqq.core.BaseCommonsKey
import com.hqq.core.R
import com.hqq.core.ui.base.BaseDataBindingFragment

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingListFragment
 * @Date : 2020/8/27 0027  上午 10:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseBindingListFragment<T : ViewDataBinding>
    : BaseDataBindingFragment<T>(), BaseListModel.IBaseListModelView {


    override val layoutId: Int
        get() = R.layout.activity_recycle_view

    /**
     *  分页下标
     */
    override var pageCount = 1
    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var listView: RecyclerView? = null
    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)


    /**
     * 初始化  基础数据
     */
    @CallSuper
    override fun initView() {
        listModel = BaseListModel(this, rootViewImpl)
        initData()
    }

    /**
     *  分页增加
     */
    override fun addPageCount() {
        pageCount += 1
    }

    /**
     *  下拉刷新 开始从第一页 获取数据
     */
    override fun onRefreshBegin() {
        if (adapter is LoadMoreModule) {
            pageCount = 1
            adapter.loadMoreModule.loadMoreComplete()
            onLoadMore()
        }
    }

    /**
     *  销毁
     */
    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     *  加载更多数据
     */
    override fun onLoadMore() {
    }


}