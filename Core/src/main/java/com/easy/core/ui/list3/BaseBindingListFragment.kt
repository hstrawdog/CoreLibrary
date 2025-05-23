package com.easy.core.ui.list3

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.module.LoadMoreModule
import com.easy.core.BaseCommonsKey
import com.easy.core.ui.base.BaseViewBindingFragment

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.binding
 * @FileName :   BaseBindingListFragment
 * @Date : 2020/8/27 0027  上午 10:54
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Deprecated("已废弃")
abstract class BaseBindingListFragment<T : ViewBinding> : BaseViewBindingFragment<T>(),
    BaseListModel3.IBaseListModelView3 {



//    override fun getLayoutId(): Int {
//        return  R.layout.activity_recycle_view
//    }
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