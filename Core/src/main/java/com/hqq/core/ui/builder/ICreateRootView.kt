package com.hqq.core.ui.builder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   ICreateRootViewBuilder
 * @Date : 2019/5/17 0017  下午 4:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * - 目的 进行接口隔离
 */
interface ICreateRootView {
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
    fun getLayoutView(parent: ViewGroup?): View?

    /**
     * 初始化默认配置 RootView
     */
    fun initDefConfig()

    /**
     * 初始化 view 等
     */
    fun initView()
    interface IActivityRootView : ICreateRootView {
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

    interface IFragmentRootView : ICreateRootView {
        /**
         * 是否延迟加载
         * 延迟加载 需要当Fragment加载到View中并显示出来才会去执行 initBasic
         *
         * @return boolean
         */
        val isLazyLoad: Boolean
    }

    interface IBaseDialogFragment : ICreateRootView {
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

    interface IBottomDialogFragment : IBaseDialogFragment
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

    interface IDialogActivity : ICreateRootView
    interface IBaseViewBuilderHolder : ICreateRootView {
        /**
         * 构建更布局
         *
         * @param parentView
         * @param activity
         * @param context
         * @param lifecycle
         */
        fun createRootView(parentView: ViewGroup?, activity: Activity?, context: Context?, lifecycle: Lifecycle)
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
    interface IBaseViewModel : IBanding {
        /**
         * 添加ViewModel 与布局使用的对象
         */
        fun addViewModel()

        /**
         * 绑定ViewModel
         *
         * @return
         */
        val bindingViewModelId: Int

        /**
         * 初始化View的要实现的默认值 正常应该通过ViewModel驱动到xml 或者Activity中
         */
        fun initViews()
    }
}