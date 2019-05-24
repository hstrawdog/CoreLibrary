package com.hqq.core.ui.builder;

import android.os.Bundle;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   ICreateRootView
 * @Date : 2019/5/17 0017  下午 4:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface ICreateRootView {
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

    interface IActivity extends ICreateRootView {
        /**
         * 初始化
         */
        void initView();

    }

    interface IFragment extends ICreateRootView {
        /**
         * 基础 初始化
         *
         * @param savedInstanceState 对象  在延迟加载的时候可能会出现null
         */
        void initBasic(Bundle savedInstanceState);

        boolean isLazyLoad();
    }

}
