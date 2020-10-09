package com.hqq.core.ui.list

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.BaseCommonsKey
import com.hqq.core.R
import com.hqq.core.ui.base.BaseDataBindingActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingListActivity
 * @Date  : 2020/8/27 0027  上午 10:48
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseBindingListActivity<T : ViewDataBinding>
    : BaseDataBindingActivity<T>(), BaseListModel.IBaseListModelView {


    override val layoutId: Int
        get() = R.layout.activity_recycle_view

    /**
     *  分页下标
     */
    override var pageCount = 1
    override var pageSize = BaseCommonsKey.PAGE_SIZE

    override var listView: RecyclerView? = null
    override var layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(this)


    /**
     * 初始化  基础数据
     */
    @CallSuper
    override fun initView() {
        listModel = BaseListModel(this, iCreateRootView)
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
        pageCount = 1
        adapter?.loadMoreModule?.loadMoreComplete()
        onLoadMore()
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