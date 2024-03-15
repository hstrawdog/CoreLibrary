package com.easy.core.ui.dialog

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
import com.easy.core.R
import com.easy.core.ui.base.IBaseDialogFragment
import com.easy.core.ui.base.IRootViewBuildBuild
import com.easy.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui
 * @FileName :   BaseBottomDialog
 * @Date : 2019/4/29 0029  下午 2:58
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * BottomSheetDialogFragment  二级显示dialog 可以滑动两次显示关闭 类似知乎评论效果
 * 宽度是全屏
 * 高度自定义
 */
abstract class BaseBottomDialog : BottomSheetDialogFragment(), IBaseDialogFragment {
    var rootView: View? = null
    var loaded = false
    var behavior: BottomSheetBehavior<FrameLayout>? = null
    open val transparentBottomSheetStyle: Int = R.style.TransparentBottomSheetStyle

    /**
     * 布局创建 容器
     */
    lateinit var rootViewBuild: IRootViewBuildBuild

    override fun getDialogHeight(): Int {
        return CoordinatorLayout.LayoutParams.MATCH_PARENT
    }
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

    override fun getDialogWeight(): Int {
        return  CoordinatorLayout.LayoutParams.WRAP_CONTENT
    }
    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, transparentBottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootViewBuild = IRootViewBuildBuild(this)
            initConfig()
            rootView = rootViewBuild.buildContentView(this)
        }
        LogUtils.e4Debug("onCreateView " + javaClass.simpleName + this.toString())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!loaded && rootView != null) {
            loaded = true
            initView()
        }
    }

    override fun onStart() {
        super.onStart()
        // 设置软键盘不自动弹出
        val dialog = dialog as BottomSheetDialog?
        val bottomSheet = dialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = getDialogHeight()
            behavior = BottomSheetBehavior.from(bottomSheet)
            // 初始为展开状态
            behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
    }


}