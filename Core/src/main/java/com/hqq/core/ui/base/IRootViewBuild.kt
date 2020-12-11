package com.hqq.core.ui.base

import android.view.View

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.build
 * @FileName :   IRootViewBuilder
 * @Date : 2019/5/24 0024  上午 10:20
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
interface IRootViewBuild {

    /**
     * 初始化activity的配置
     * 是否全屏
     * 是否竖屏
     */
    fun initActivity(fullScreen: Boolean)

    /**
     * 构建后的页面布局
     *
     * @return rootView
     */
    fun buildContentView(rootViewBuilder: IRootView): View?
}