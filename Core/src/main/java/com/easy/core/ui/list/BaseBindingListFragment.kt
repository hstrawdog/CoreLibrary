package com.easy.core.ui.list

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.loadState.LoadState
import com.easy.core.BaseCommonsKey
import com.easy.core.R
import com.easy.core.ui.base.BaseDataBindingFragment
import com.easy.core.ui.base.BaseViewBindingFragment

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.binding
 * @FileName :   BaseBindingListFragment
 * @Date : 2020/8/27 0027  上午 10:54
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseBindingListFragment<T : ViewBinding> : BaseViewBindingFragment<T>(),
    BaseListModel.IBaseListModelView {

    /**
     *  是否允许加载更多
     */
    override var isLoadMore: Boolean = true

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
            pageCount = 1
            listModel?.helper?.leadingLoadState =  LoadState.NotLoading(true)
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