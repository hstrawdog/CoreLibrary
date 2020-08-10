package com.hqq.core.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hqq.core.CoreBuildConfig
import com.hqq.core.R
import com.hqq.core.annotation.ToolBarMode
import com.hqq.core.listenner.DialogClickListener
import com.hqq.core.ui.base.IRootViewImpl
import com.hqq.core.ui.builder.ICreateRootView.IDialogFragment
import com.hqq.core.utils.log.LogUtils
import com.hqq.core.utils.statusbar.StatusBarManager
import com.hqq.core.widget.LoadingView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseDialog
 * @Date : 2018/6/19 0019  上午 11:33
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * - 默认 背景是透明的 状态栏亮色模式
 * - 在高度全屏的情况下 进行状态栏模式设置
 * - 宽度需要在代码上写死
 * - DialogFragment自带内存泄漏
 */
abstract class BaseDialog : DialogFragment(), IDialogFragment {
    protected var mLoadingView: LoadingView? = null
    var mLoaded = false
    protected var mDialogClickListener: DialogClickListener<*>? = null
    protected var mRootView: View? = null

    /**
     * 布局创建 容器
     */
   lateinit var mRootViewBuild: IRootViewImpl<BaseDialog>

    /**
     * 状态栏模式
     */
    @kotlin.jvm.JvmField
    @ToolBarMode
    protected var mStatusBarMode: Int = CoreBuildConfig.instance.isStatusMode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        //代码设置 无标题 无边框
//        if (setWeight() == WindowManager.LayoutParams.WRAP_CONTENT) {
//            setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
//        } else {
//        }
        setStyle(STYLE_NORMAL, R.style.DefDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window!!.setWindowAnimations(animation)
        if (mRootView == null) {
            mLoadingView = LoadingView(activity)
            mRootViewBuild = IRootViewImpl(this)
            initDefConfig()
            mRootView = mRootViewBuild!!.buildContentView(this)
            initContentView()
            initView()
            LogUtils.d("onCreateView " + javaClass.simpleName + this.toString())
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!mLoaded && mRootView != null) {
            if (mStatusBarMode == ToolBarMode.Companion.LIGHT_MODE) {
                StatusBarManager.setStatusBarModel(dialog!!.window, true)
            } else if (mStatusBarMode == ToolBarMode.Companion.DARK_MODE) {
                StatusBarManager.setStatusBarModel(dialog!!.window, false)
            } else {
                // 默认进行全屏显示
                StatusBarManager.transparencyBar(dialog!!.window)
            }
            mLoaded = true
            LogUtils.e(mRootView!!.width)
        }
    }

    private fun initContentView() {
        val linearLayout = mRootView!!.findViewById<LinearLayout>(R.id.ll_rootView)
        val view = LayoutInflater.from(context).inflate(viewId, linearLayout, false)
        linearLayout.gravity = gravity
        linearLayout.addView(view)
        view.setOnClickListener { view1: View? -> }
        if (isDismissBackground) {
            linearLayout.setOnClickListener { view2: View? -> dismiss() }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.e(mRootView!!.measuredWidth)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(background))
        dialog!!.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        dialog!!.window!!.setGravity(gravity)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.setCanceledOnTouchOutside(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoadingView = null
        mRootView = null
    }

    override fun getLayoutView(parent: ViewGroup?): View? {
        return null
    }

    protected abstract val viewId: Int

    /**
     * 禁止子类重写
     *
     * @return
     */

    final override fun getLayoutViewId(): Int {
        return R.layout.dialog_new
    }

    override fun show(manager: FragmentManager) {
        val ft = manager.beginTransaction()
        val prev = manager.findFragmentByTag(javaClass.simpleName)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.add(this, javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }

    /**
     * 默认配置
     */
    override fun initDefConfig() {}
    val isDismissBackground: Boolean
        get() = true

    /**
     * @return 背景颜色
     */
    override val background: Int
        get() = 0x00000000

    override val gravity: Int
        get() = Gravity.CENTER

    override val weight: Int
        get() = WindowManager.LayoutParams.WRAP_CONTENT

    override val height: Int
        get() = WindowManager.LayoutParams.WRAP_CONTENT

    override val animation: Int
        get() = R.style.DialogAnimation_bottom2top

    fun setDialogClickListener(dialogClickListener: DialogClickListener<*>?): BaseDialog {
        mDialogClickListener = dialogClickListener
        return this
    }
}