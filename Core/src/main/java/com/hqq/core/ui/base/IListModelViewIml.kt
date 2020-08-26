package com.hqq.core.ui.base

import com.hqq.core.ui.base.BaseListModelView.IListModelView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.base
 * @FileName :   IListModelViewIml
 * @Date : 2020/8/26 0026  下午 4:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class IListModelViewIml : IListModelView {
    override val pageCount: Int
        get() = 0
    override val pageSize: Int
        get() = 0

    override fun addPageCount() {}
    override fun onRefreshBegin() {}
}