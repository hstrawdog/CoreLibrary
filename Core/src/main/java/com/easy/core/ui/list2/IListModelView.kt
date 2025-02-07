package com.easy.core.ui.list2

import android.content.Context

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.list2
 * @Date  :2024/12/23 22:43
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface IListModelView {
    /**
     *  避开上下文的缓存
     * @return Context
     */
    fun getContext(): Context

    /**
     * 开始下拉刷新
     */
    fun onRefreshBegin()

    /**
     *  加载更多
     */
    fun onLoadMore()



}