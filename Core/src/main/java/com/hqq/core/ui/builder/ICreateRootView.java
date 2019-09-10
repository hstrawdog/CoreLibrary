package com.hqq.core.ui.builder;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   ICreateRootView
 * @Date : 2019/5/17 0017  下午 4:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * - 目的 进行接口隔离
 */
public interface ICreateRootView {
    /**
     * 获取id
     *
     * @return
     */
    int setViewId();

    /**
     * 设置跟布局
     *
     * @return
     */
    View setRootView();

    /**
     * 初始化默认配置 RootView
     */
    void initDefConfig();

    /**
     * 初始化 view 等
     */
    void initView();

    interface IActivity extends ICreateRootView {


    }

    interface IFragment extends ICreateRootView {

        /**
         * 是否延迟加载
         * 延迟加载 需要当Fragment加载到View中并显示出来才会去执行 initBasic
         *
         * @return boolean
         */
        boolean isLazyLoad();
    }

    interface IBaseDialogFragment extends ICreateRootView {

        /**
         * 显示dialog
         *
         * @param manager void
         */
        void show(FragmentManager manager);

        /**
         * dialog 高度
         *
         * @return int
         */
        int setHeight();
    }

    interface IBottomDialogFragment extends IBaseDialogFragment {

    }

    interface IDialogFragment extends IBaseDialogFragment {
        /**
         * dialog 的背景颜色
         *
         * @return int
         */
        int setBackground();

        /**
         * dialog gravity 模式
         *
         * @return int
         */
        int setGravity();

        /**
         * dialog  宽度
         *
         * @return int
         */
        int setWeight();

        /**
         * dialog 动画
         *
         * @return int
         */
        int setAnimation();

    }

    interface IDialogActivity extends ICreateRootView {

    }
}
