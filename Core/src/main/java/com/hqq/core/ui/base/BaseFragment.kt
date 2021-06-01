package com.hqq.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hqq.core.toolbar.IToolBar
import com.hqq.core.ui.base.IRootView.IFragmentRootView
import com.hqq.core.utils.BundleAction
import com.hqq.core.utils.log.LogUtils
import com.hqq.core.widget.LoadingView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFragment
 * @Date : 2018/5/28 0028  下午 2:14
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseFragment : Fragment(), IFragmentRootView, BundleAction, View.OnClickListener {
    /**
     * 缓存根布局对象
     */
    protected var rootView: View? = null

    /**
     * LoadingDialog
     */
    var loadingView: LoadingView? = null

    /**
     * fragment 是否创建
     */
    var isCreate = false

    /**
     * 延迟加载是否结束
     */
    var lazyInitEnd = false

    /**
     * bundle 集合
     */
    override val bundle: Bundle?
        get() = arguments
    /**
     * 布局创建 容器
     */
    private val rootViewBuild: IRootViewBuildBuild by lazy {
        IRootViewBuildBuild(this)
    }

    /**
     *  根布局
     */
    val rootViewImpl: RootViewImpl
        get() {
            return rootViewBuild.rootViewImpl
        }

    /**
     *  是否延迟加载
     */
    override val isLazyLoad: Boolean = false

    /**
     * 标题栏
     * get  类似代理 每次从 rootViewBuild.iRootViewImp 获取对象
     */
    val iToolBar: IToolBar?
        get() {
            return rootViewImpl.iToolBar
        }

    /**
     * 在viewPage 中不断的切换 fragment  都会不断的去执行 onCreateView 的方法
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            activity?.let {
                loadingView = LoadingView(it)
            }
            initConfig()
            rootView = rootViewBuild.buildContentView(this)
        }
        LogUtils.e(this.javaClass.name, "onCreateView " + javaClass.simpleName + this.toString())
        return rootView
    }

    /**
     * 延迟加载
     * 当页面在前台显示时 才开始加载数据
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isLazyLoad && isCreate && !lazyInitEnd && isVisibleToUser) {
            initView()
            lazyInitEnd = true
            LogUtils.e(this.javaClass.name, "setUserVisibleHint  initBasic " + javaClass.simpleName + this.toString())
        } else if (isLazyLoad && isCreate && lazyInitEnd && !isVisibleToUser) {
            onFragmentHit()
        }
    }

    /**
     * 保证一个fragment 只会执行一次
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isCreate && rootView != null) {
            isCreate = true
            if (!isLazyLoad) {
                initView()
                LogUtils.e(this.javaClass.name, "onViewCreated initBasic   false  " + javaClass.simpleName + this.toString())
            } else if (isLazyLoad && userVisibleHint) {
                lazyInitEnd = true
                LogUtils.e(this.javaClass.name, "onViewCreated initBasic   True " + javaClass.simpleName + this.toString())
                initView()
            }
        }
    }

    /**
     * 在没有使用 status 的情况下
     * 只有fragment 完全脱离activity 的回执onDestroy  也就是activity 销毁时
     */
    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e(this.javaClass.name, "onDestroy " + javaClass.simpleName + this.toString())
        if (rootView != null) {
            if (rootView is ViewGroup) {
                (rootView as ViewGroup).removeAllViews()
            }
            if (null != rootView!!.parent) {
                (rootView!!.parent as ViewGroup).removeView(rootView)
                rootView = null
            }
        }

        if (loadingView != null) {
            loadingView = null
        }
    }

    /**
     *   初始化配置
     */
    override fun initConfig() {

    }

    /**
     * 关联主界面 **只有在使用自定义View时使用**
     */
    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }

    /**
     *  重写点击接口
     */
    override fun onClick(v: View) {

    }

    /**
     * fragment 隐藏
     */
    private fun onFragmentHit() {}

    /******************************** 繁生方法   */
    fun findViewById(id: Int): View? {
        return if (rootView == null || id < 0) null else rootView!!.findViewById(id)

    }
}