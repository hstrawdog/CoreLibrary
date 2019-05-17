package com.hqq.core.ui;

import android.os.Bundle;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   CreateRootView
 * @Date : 2019/5/17 0017  下午 4:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface CreateRootView {
    /**
     * 获取id
     *
     * @return
     */
    int getViewId();

    /**
     * @return
     */
    View getRootView();

    /**
     *
     */
    void initDefConfig();

    interface IActivity extends CreateRootView {
        /**
         * 初始化
         */
        void initView();


        /**
         * @return
         */
        boolean fullScreen();


        /**
         * @return
         */
        boolean alwaysPortrait();

    }

    interface IFragment extends CreateRootView {
        /**
         * 基础 初始化
         *
         * @param savedInstanceState 对象  在延迟加载的时候可能会出现null
         */
        void initBasic(Bundle savedInstanceState);

        boolean isLazyLoad();
    }

}
