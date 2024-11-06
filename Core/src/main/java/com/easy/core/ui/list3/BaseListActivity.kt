package com.easy.core.ui.list3

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.module.LoadMoreModule
import com.easy.core.BaseCommonsKey
import com.easy.core.ui.base.BaseActivity

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseListActivity
 * @Date : 2018/11/5 0005  下午 4:23
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseListActivity : BaseActivity(), BaseListModel3.IBaseListModelView3 {

    /**
     *  分页大小 默认是20 可以在Config 中统一配置
     */
    override var pageSize = BaseCommonsKey.PAGE_SIZE

    /**
     *  分页下标
     */
    override var pageCount = 1

    /**
     *  正常List 默认使用 自带的ListView  如果页面包含其他内容 则使用 LayoutViewId
     */
    override fun getLayoutViewId(): Int {
        return 0
    }
    /**
     *  RecycleView Manager
     */
    override var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

    /**
     *  RecycleView 对象
     */
    override var listView: RecyclerView? = null

    /**
     * 重写了 setViewId    执行 setRootView
     * 默认都会执行一般这个方法 但是没有添加到rooView中
     *
     * @return
     */
    override fun getLayoutView(group: ViewGroup): View? {
        return if (getLayoutViewId() <= 0) {
            BaseListModel3.createRecycleView(this)
        } else {
            null
        }
    }

    override lateinit var listModel: BaseListModel3

    /**
     * 初始化  基础数据
     */
    override fun initView() {
        listModel = BaseListModel3(this, rootViewImpl)
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