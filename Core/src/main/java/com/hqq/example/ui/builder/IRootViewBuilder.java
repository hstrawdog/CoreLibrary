package com.hqq.example.ui.builder;

import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.build
 * @FileName :   IRootViewBuilder
 * @Date : 2019/5/24 0024  上午 10:20
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface IRootViewBuilder {

    /**
     * 初始化activity的配置
     * 是否全屏
     * 是否竖屏
     */
    void initActivity();

    /**
     * 构建后的页面布局
     *
     * @param layoutId xml ViewId
     * @param rootView 自定义View
     * @return rootView
     */
    View initContentView(int layoutId, View rootView);

}
