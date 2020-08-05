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
public interface ICreateRootView {
    /**
     * 获取id
     *
     * @return
     */
    int getLayoutViewId();

    /**
     * 设置跟布局
     *
     * @return
     */
    View getLayoutView(ViewGroup parent);

    /**
     * 初始化默认配置 RootView
     */
    void initDefConfig();

    /**
     * 初始化 view 等
     */
    void initView();

    interface IActivityRootView extends ICreateRootView {

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

    interface IFragmentRootView extends ICreateRootView {

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
        int getHeight();
    }

    interface IBottomDialogFragment extends IBaseDialogFragment {

    }

    interface IDialogFragment extends IBaseDialogFragment {
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

    interface IDialogActivity extends ICreateRootView {

    }

    interface IBaseViewBuilderHolder extends ICreateRootView {
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

    /**
     * dataBinding Activity 使用
     */
    interface IBanding {
        /**
         * 通过布局id 生成对应的 banding类
         * 只是单独使用ViewBanding的话直接用banding生成的对应类就可以
         *
         * @return
         */
        int getLayoutId();


    }

    /**
     * 同上  多了一个ViewModel要banding
     */
    interface IBaseViewModel extends IBanding {
        /**
         * 添加ViewModel 与布局使用的对象
         */
        void addViewModel();

        /**
         * 绑定ViewModel
         *
         * @return
         */
        int getBindingViewModelId();

        /**
         * 初始化View的要实现的默认值 正常应该通过ViewModel驱动到xml 或者Activity中
         */
        void initViews();
    }

}
