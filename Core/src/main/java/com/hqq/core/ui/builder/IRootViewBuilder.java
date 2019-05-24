package com.hqq.core.ui.builder;

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
     * 构建rootView的类型
     *
     * @param rootView
     * @param vid
     * @return
     */
    View createRootView(View rootView, int vid);

    /**
     * 初始化activity的配置
     * 是否全屏
     * 是否竖屏
     */
    void initActivity();

    /**
     * @return
     */
    View initContentView(int layoutId, View rootView);


}
