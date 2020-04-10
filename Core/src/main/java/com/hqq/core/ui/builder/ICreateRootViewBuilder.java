package com.hqq.core.ui.builder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   ICreateRootViewBuilder
 * @Date : 2019/5/17 0017  下午 4:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * - 目的 进行接口隔离
 */
public interface ICreateRootViewBuilder {
    /**
     * 获取id
     *
     * @return
     */
    int getViewId();

    /**
     * 设置跟布局
     *
     * @return
     */
    View getRootView();

    /**
     * 初始化默认配置 RootView
     */
    void initDefConfig();

    /**
     * 初始化 view 等
     */
    void initView();

    interface IActivityBuilder extends ICreateRootViewBuilder {

        /**
         * 进入动画
         */
        void initAnimEnter();

        /**
         * 结束动画
         */
        void initAnimExit();

        /**
         * 判断后的 onActivityResult
         *
         * @param requestCode
         * @param resultCode
         * @param data
         */
        void onResult(int requestCode, int resultCode, Intent data);

    }

    interface IFragmentBuilder extends ICreateRootViewBuilder {

        /**
         * 是否延迟加载
         * 延迟加载 需要当Fragment加载到View中并显示出来才会去执行 initBasic
         *
         * @return boolean
         */
        boolean isLazyLoad();
    }

    interface IBaseDialogFragmentBuilder extends ICreateRootViewBuilder {

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
        int getHeight();
    }

    interface IBottomDialogFragmentBuilder extends IBaseDialogFragmentBuilder {

    }

    interface IDialogFragmentBuilder extends IBaseDialogFragmentBuilder {
        /**
         * dialog 的背景颜色
         *
         * @return int
         */
        int getBackground();

        /**
         * dialog gravity 模式
         *
         * @return int
         */
        int getGravity();

        /**
         * dialog  宽度
         *
         * @return int
         */
        int getWeight();

        /**
         * dialog 动画
         *
         * @return int
         */
        int getAnimation();

    }

    interface IDialogActivityBuilder extends ICreateRootViewBuilder {

    }

    interface IBaseViewBuilderHolder extends ICreateRootViewBuilder {
        /**
         * 构建更布局
         *
         * @param parentView
         * @param activity
         * @param context
         * @param lifecycle
         */
        void createRootView(ViewGroup parentView, Activity activity, Context context, Lifecycle lifecycle);
    }

}
