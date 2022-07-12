package com.hqq.core.ui.base

import android.content.Context
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

interface OnFragmentVisibilityChangedListener {
    fun onFragmentVisibilityChanged(visible: Boolean)
}

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFragment
 * @Date : 2018/5/28 0028  下午 2:14
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseFragment : Fragment(), IFragmentRootView, BundleAction, View.OnClickListener, View.OnAttachStateChangeListener,
    OnFragmentVisibilityChangedListener {
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
        LogUtils.d(this.javaClass.name + "onCreateView " + javaClass.simpleName + this.toString())
        return rootView
    }

    /**
     * 延迟加载
     * 当页面在前台显示时 才开始加载数据
     * Tab切换时会回调此方法。对于没有Tab的页面，[Fragment.getUserVisibleHint]默认为true。
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        info("setUserVisibleHint = $isVisibleToUser")
        super.setUserVisibleHint(isVisibleToUser)
        checkVisibility(isVisibleToUser)

    }

    /**
     * 保证一个fragment 只会执行一次
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 处理直接 replace 的 case
        view.addOnAttachStateChangeListener(this)
        if (!isCreate && rootView != null) {
            isCreate = true
            if (!isLazyLoad) {
                initView()
                LogUtils.d("onViewCreated initBasic   false  " + javaClass.simpleName + this.toString())
            } else if (isLazyLoad && visible) {
                lazyInitEnd = true
                LogUtils.d("onViewCreated initBasic   True " + javaClass.simpleName + this.toString())
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
        LogUtils.dInfo("onDestroy " + javaClass.simpleName + this.toString())
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

    /******************************** 衍生方法   *********************************/
    fun findViewById(id: Int): View? {
        return if (rootView == null || id < 0) null else rootView!!.findViewById(id)

    }

    // 参考https://juejin.cn/post/6899429993231679501
    /******************************** Fragment 可进性   *********************************/


    /**
     * ParentActivity是否可见
     */
    private var parentActivityVisible = false

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    private var visible = false

    private var localParentFragment: BaseFragment? = null

    private val listeners = ArrayList<OnFragmentVisibilityChangedListener>()

    fun addOnVisibilityChangedListener(listener: OnFragmentVisibilityChangedListener?) {
        listener?.apply {
            listeners.add(this)
        }
    }

    fun removeOnVisibilityChangedListener(listener: OnFragmentVisibilityChangedListener?) {
        listener?.apply {
            listeners.remove(this)
        }

    }

    override fun onAttach(context: Context) {
        info("onAttach")
        super.onAttach(context)
        val parentFragment = parentFragment
        if (parentFragment != null && parentFragment is BaseFragment) {
            this.localParentFragment = parentFragment
            localParentFragment?.addOnVisibilityChangedListener(this)
        }
        checkVisibility(true)
    }

    override fun onDetach() {
        info("onDetach")
        localParentFragment?.removeOnVisibilityChangedListener(this)
        super.onDetach()
        checkVisibility(false)
        localParentFragment = null
    }

    override fun onResume() {
        info("onResume")
        super.onResume()
        onActivityVisibilityChanged(true)
    }


    override fun onPause() {
        info("onPause")
        super.onPause()
        onActivityVisibilityChanged(false)
    }

    /**
     * ParentActivity可见性改变
     */
    protected fun onActivityVisibilityChanged(visible: Boolean) {
        parentActivityVisible = visible
        checkVisibility(visible)
        if (isLazyLoad && isCreate && !lazyInitEnd && visible) {
            initView()
            lazyInitEnd = true
            LogUtils.dInfo("setUserVisibleHint  initBasic " + javaClass.simpleName + this.toString())
        } else if (isLazyLoad && isCreate && lazyInitEnd && !visible) {
            onFragmentHit()
        }
    }

    /**
     * ParentFragment可见性改变
     */
    override fun onFragmentVisibilityChanged(visible: Boolean) {
        checkVisibility(visible)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        info("onCreate")
        super.onCreate(savedInstanceState)
    }

    /**
     * 调用 fragment add hide 的时候回调用这个方法
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        checkVisibility(hidden)
    }

    override fun onViewAttachedToWindow(v: View?) {
        info("onViewAttachedToWindow")
        checkVisibility(true)
    }

    override fun onViewDetachedFromWindow(v: View) {
        info("onViewDetachedFromWindow")
        v.removeOnAttachStateChangeListener(this)
        checkVisibility(false)
    }

    /**
     * 检查可见性是否变化
     *
     * @param expected 可见性期望的值。只有当前值和expected不同，才需要做判断
     */
    private fun checkVisibility(expected: Boolean) {
        if (expected == visible) return
        val parentVisible =
            if (localParentFragment == null) parentActivityVisible
            else localParentFragment?.isFragmentVisible() ?: false
        val superVisible = super.isVisible()
        val hintVisible = userVisibleHint
        val visible = parentVisible && superVisible && hintVisible
        info(
            String.format(
                "==> checkVisibility = %s  ( parent = %s, super = %s, hint = %s )", visible, parentVisible, superVisible, hintVisible
            )
        )
        if (visible != this.visible) {
            this.visible = visible
            onVisibilityChanged(this.visible)
        }
    }

    /**
     * 可见性改变
     */
    private fun onVisibilityChanged(visible: Boolean) {
        info("==> onVisibilityChanged = $visible")
        listeners.forEach {
            it.onFragmentVisibilityChanged(visible)
        }
    }

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    fun isFragmentVisible(): Boolean {
        return visible
    }

    private fun info(s: String) {
        LogUtils.d("${this.javaClass.simpleName} ; $s ; this is $this")
    }


}