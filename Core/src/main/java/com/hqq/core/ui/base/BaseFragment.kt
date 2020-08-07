package com.hqq.core.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hqq.core.ui.builder.ICreateRootView.IFragmentRootView
import com.hqq.core.utils.log.LogUtils
import com.hqq.core.widget.LoadingView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFragment
 * @Date : 2018/5/28 0028  下午 2:14
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseFragment : Fragment(), IFragmentRootView, View.OnClickListener {
    /**
     * 缓存根布局对象
     */
    protected var mRootView: View? = null

    /**
     * 当前Activity
     */
    @JvmField
    protected var mActivity: Activity? = null

    /**
     * LoadingDialog
     */
    var mLoadingView: LoadingView? = null

    /**
     * fragment 是否创建
     */
    var mIsCreate = false

    /**
     * 延迟加载是否结束
     */
    var mLazyInitEnd = false

    /**
     * 布局创建 容器
     */
    @JvmField
    protected var mRootViewBuild: IRootViewImpl? = null

    /**
     * 在viewPage 中不断的切换 fragment  都会不断的去执行 onCreateView 的方法
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mActivity = activity
            mLoadingView = LoadingView(mActivity)
            mRootViewBuild = IRootViewImpl(this)
            initDefConfig()
            mRootView = mRootViewBuild!!.buildContentView(this)
        }
        LogUtils.d(this.javaClass.name, "onCreateView " + javaClass.simpleName + this.toString())
        return mRootView
    }

    /**
     * 延迟加载
     * 当页面在前台显示时 才开始记载数据
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isLazyLoad && mIsCreate && !mLazyInitEnd && isVisibleToUser) {
            initView()
            mLazyInitEnd = true
            LogUtils.d(this.javaClass.name, "setUserVisibleHint  initBasic " + javaClass.simpleName + this.toString())
        } else if (isLazyLoad && mIsCreate && mLazyInitEnd && !isVisibleToUser) {
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
        if (!mIsCreate && mRootView != null) {
            mIsCreate = true
            if (!isLazyLoad) {
                initView()
                LogUtils.d(this.javaClass.name, "onViewCreated initBasic   false  " + javaClass.simpleName + this.toString())
            } else if (isLazyLoad && userVisibleHint) {
                mLazyInitEnd = true
                LogUtils.d(this.javaClass.name, "onViewCreated initBasic   True " + javaClass.simpleName + this.toString())
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
        LogUtils.d(this.javaClass.name, "onDestroy " + javaClass.simpleName + this.toString())
        if (mRootView != null) {
            if (mRootView is ViewGroup) {
                (mRootView as ViewGroup).removeAllViews()
            }
            if (null != mRootView!!.parent) {
                (mRootView!!.parent as ViewGroup).removeView(mRootView)
                mRootView = null
            }
        }
        if (mRootViewBuild != null) {
            mRootViewBuild = null
        }
        if (mLoadingView != null) {
            mLoadingView = null
        }
        mActivity = null
    }

    override fun initDefConfig() {}

    /**
     * 默认不开启延迟 加载
     *
     * @return
     */
    override fun isLazyLoad(): Boolean {
        return false
    }

    /**
     * 关联主界面 **只有在使用自定义View时使用**
     */
    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }

    override fun onClick(v: View) {}

    /**
     * fragment 隐藏
     */
    protected fun onFragmentHit() {}

    /******************************** 繁生方法   */
    fun findViewById(id: Int): View? {
        if (mRootView == null) {
            return null
        }
        return if (id < 0) {
            null
        } else mRootView!!.findViewById(id)
    }
}