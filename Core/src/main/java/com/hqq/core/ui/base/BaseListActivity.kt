package com.hqq.core.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.BaseCommonsKey
import com.hqq.core.ui.base.BaseListModelView.IBaseListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseListActivity
 * @Date : 2018/11/5 0005  下午 4:23
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseListActivity<T : BaseQuickAdapter<*, *>?> :
        BaseActivity(), IBaseListModelView<T?> {

    override var pageSize = BaseCommonsKey.PAGE_SIZE
    override var pageCount = 1
    override val layoutViewId: Int = 0;
    override val rcLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    override var listView: RecyclerView? = null

    var mBaseListModel: BaseListModelView? = null

    /**
     * 重写了 setViewId    执行 setRootView
     * 默认都会执行一般这个方法 但是没有添加到rooView中
     *
     * @return
     */
    override fun getLayoutView(group: ViewGroup): View? {
        return if (layoutViewId <= 0) {
            BaseListModelView.createRecycleView(this)
        } else {
            null
        }
    }

    /**
     * 初始化  基础数据
     */
    @CallSuper
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

    override fun onDestroy() {
        super.onDestroy()
        mBaseListModel = null
    }

    override fun onLoadMore() {
    }



}