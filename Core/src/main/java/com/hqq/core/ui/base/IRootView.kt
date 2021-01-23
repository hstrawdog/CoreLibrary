package com.hqq.core.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.hqq.core.lifecycle.BaseLifecycleObserver

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   ICreateRootViewBuilder
 * @Date : 2019/5/17 0017  下午 4:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * - 目的 进行接口隔离
 */
interface IRootView {
    /**
     * 获取id
     *
     * @return
     */
    val layoutViewId: Int

    /**
     * 设置跟布局
     *
     * @return
     */
    fun getLayoutView(parent: ViewGroup): View?

    /**
     * 初始化默认配置 RootView
     */
    fun initConfig()

    /**
     * 初始化 view 等
     * initView 是父类onCreate 中执行的最后方法
     * 之后才会是liveCycle监听的 onCreate
     * 子类必须实现super.initView
     */
    fun initView()

    interface IActivityRootView : IRootView {
        /**
         * 进入动画
         */
        fun initAnimEnter()

        /**
         * 结束动画
         */
        fun initAnimExit()

        /**
         * 判断后的 onActivityResult
         *
         * @param requestCode
         * @param resultCode
         * @param data
         */
        fun onResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    interface IFragmentRootView : IRootView {
        /**
         * 是否延迟加载
         * 延迟加载 需要当Fragment加载到View中并显示出来才会去执行 initBasic
         *
         * @return boolean
         */
        val isLazyLoad: Boolean
    }

    interface IBaseDialogFragment : IRootView {
        /**
         * 显示dialog
         *
         * @param manager void
         */
        fun show(manager: FragmentManager)

        /**
         * dialog 高度
         *
         * @return int
         */
        val height: Int
    }

    interface IDialogFragment : IBaseDialogFragment {
        /**
         * dialog 的背景颜色
         *
         * @return int
         */
        val background: Int

        /**
         * dialog gravity 模式
         *
         * @return int
         */
        val gravity: Int

        /**
         * dialog  宽度
         *
         * @return int
         */
        val weight: Int

        /**
         * dialog 动画
         *
         * @return int
         */
        val animation: Int
    }

    interface IBaseViewBuilderHolder : IRootView {
        /**
         * 构建更布局
         *
         * @param parentView
         * @param activity
         * @param context
         * @param lifecycle
         */
        fun createRootView(
                parentView: ViewGroup?,
                activity: Activity?,
                context: Context?,
                lifecycle: Lifecycle
        )
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
        val layoutId: Int
    }

    /**
     * 同上  多了一个ViewModel要banding
     */
    interface IBaseViewModelActivity : IBanding {

        /**
         * 独立出ViewModel的初始化
         * 默认使用 ViewModelProviders 创建
         * 当有Hilt时 可以重写此方法
         * 搭配 @ViewModelInject  与by viewModes() 构建 注入参数
         *
         */
        fun getViewModel(): ViewModel

        /**
         * 绑定ViewModel id
         * 用于xml 中viewModel 对象的绑定
         * 正常情况下一个界面(Activity  xml) 对应一个viewModel
         * @return
         */
        val bindingViewModelId: Int

        /**
         * 添加ViewModel 与布局使用的对象
         */
        fun addViewModel()

        /**
         * 初始化View的要实现的默认值 正常应该通过ViewModel驱动到xml 或者Activity中
         */
        fun initViews()
    }


    interface IBaseViewModel : BaseLifecycleObserver {
        /**
         * ViewModel的onCreate 是最后执行的  比子类的 onCreate 执行还晚
         * Fragment 不一样  Fragment 涉及到延迟加载  需要当界面显示出来后再执行ViewModel 所以需要initData
         * 共享界面数据到ViewModel中
         */
        fun initData(extras: Bundle?)

        /**
         *  传递数据
         * @param requestCode Int
         * @param resultCode Int
         * @param data Intent?
         */
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    }


}