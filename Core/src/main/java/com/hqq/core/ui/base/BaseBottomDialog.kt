package com.hqq.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hqq.core.R
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseBottomDialog
 * @Date : 2019/4/29 0029  下午 2:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 宽度是全屏
 * 高度自定义
 */
abstract class BaseBottomDialog : BottomSheetDialogFragment(), ICreateRootView.IBaseDialogFragment {
    var mRootView: View? = null
    var mLoaded = false
    var behavior: BottomSheetBehavior<FrameLayout>? = null

    /**
     * 布局创建 容器
     */
    lateinit var mRootViewBuild: IRootViewImpl<BaseBottomDialog>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, transparentBottomSheetStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootViewBuild = IRootViewImpl(this)
            initConfig()
            mRootView = mRootViewBuild!!.buildContentView(this)
        }
        LogUtils.d("onCreateView " + javaClass.simpleName + this.toString())
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!mLoaded && mRootView != null) {
            mLoaded = true
            initView()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        val dialog = dialog as BottomSheetDialog?
        val bottomSheet = dialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = height
            behavior = BottomSheetBehavior.from(bottomSheet)
            // 初始为展开状态
            behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRootView = null
    }

    override val height: Int
        get() = CoordinatorLayout.LayoutParams.MATCH_PARENT

    override fun initConfig() {}
    override fun show(manager: FragmentManager) {
        val ft = manager.beginTransaction()
        val prev = manager.findFragmentByTag(javaClass.simpleName)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.add(this, javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }

    protected open val transparentBottomSheetStyle: Int
        protected get() = R.style.TransparentBottomSheetStyle

    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }
}