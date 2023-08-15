package com.easy.core.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.annotation.ToolBarMode
import com.easy.core.lifecycle.SingleLiveEvent
import com.easy.core.listenner.DialogClickListener
import com.easy.core.ui.base.IDialogFragment
import com.easy.core.ui.base.IRootViewBuildBuild
import com.easy.core.ui.base.RootViewImpl
import com.easy.core.utils.log.LogUtils
import com.easy.core.utils.statusbar.StatusBarManager
import com.easy.core.widget.LoadingView

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
 *
 * --  考虑是否重构 引入Binding框架
 */
abstract class BaseDialog : DialogFragment(), IDialogFragment {
    /**
     *  回调
     */
    var activityResult = SingleLiveEvent<ActivityResult>()

    /**
     *  页面跳转
     */
    var registerForActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        activityResult.value = result
    }

    var loadingView: LoadingView? = null

    var loaded = false

    var dialogClickListener: DialogClickListener<*>? = null

    var rootView: View? = null


    abstract fun getDialogLayoutId(): Int

    override fun getLayoutViewId(): Int {
        return R.layout.dialog_new

    }

    /**
     * 布局创建
     */
    protected val rootViewBuild: IRootViewBuildBuild by lazy {
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
     *  是否点击空白关闭dialog
     */
    open val isDismissBackground: Boolean
        get() = true

    /**
     * 背景颜色
     */
    override fun getBackground(): Int {
        return 0x00000000
    }

    /**
     *方向
     */
    override fun getGravity(): Int {
        return Gravity.CENTER
    }

    /**
     *  宽
     */
    override fun getDialogWeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     *  高
     */
    override fun getDialogHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     *  动画
     *   R.style.dialogAnimation_fade_in2fade_out  淡入淡出
     *   R.style.DialogAnimation_bottom2top  下到上
     */
    override fun getAnimation(): Int {
        return R.style.DialogAnimation_bottom2top
    }

    /**
     * 状态栏模式
     */
    @ToolBarMode
    var statusBarMode: Int = CoreConfig.get().isStatusMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        //代码设置 无标题 无边框
//        if (setWeight() == WindowManager.LayoutParams.WRAP_CONTENT) {
//            setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
//        } else {
//        }
        //  设置主题
        setStyle(STYLE_NORMAL, R.style.DefDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setWindowAnimations(getAnimation())
        if (rootView == null) {
            activity?.let {
                loadingView = LoadingView(it)
            }
            initConfig()
            rootView = rootViewBuild.buildContentView(this)
            initContentView()
            initView()
            LogUtils.e4Debug("onCreateView " + javaClass.simpleName + this.toString())
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!loaded && rootView != null) {
            if (statusBarMode == ToolBarMode.Companion.LIGHT_MODE) {
                StatusBarManager.setStatusBarModel(dialog?.window, true)
            } else if (statusBarMode == ToolBarMode.Companion.DARK_MODE) {
                StatusBarManager.setStatusBarModel(dialog?.window, false)
            } else {
                // 默认进行全屏显示
                StatusBarManager.transparencyBar(dialog?.window)
            }
            loaded = true
            LogUtils.e4Debug(rootView!!.width)
        }
    }

    /**
     * 嵌套内容
     */
    open fun initContentView() {
        val linearLayout = rootView!!.findViewById<LinearLayout>(R.id.ll_rootView)
        val view = LayoutInflater.from(context)
            .inflate(getDialogLayoutId(), linearLayout, false)
        linearLayout.gravity = getGravity()
        linearLayout.addView(view)
        view.setOnClickListener { }
        if (isDismissBackground) {
            linearLayout.setOnClickListener { dismiss() }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.e4Debug(rootView?.measuredWidth)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(getBackground()))
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        dialog?.window?.setGravity(getGravity())
    }

    override fun onStart() {
        super.onStart()
        dialog!!.setCanceledOnTouchOutside(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingView = null
        rootView = null
    }

    override fun getLayoutView(parent: ViewGroup): View? {
        return null
    }

    override fun show(manager: FragmentManager) {
        val ft = manager.beginTransaction()
        val prev = manager.findFragmentByTag(javaClass.simpleName)
        if (prev != null) {
            if (prev is BaseDialog) {
                try {
                    if (prev.dialog?.isShowing == true) {
                        prev.dismiss()
                    }
                } catch (e: Exception) {
                    LogUtils.e(e.toString())
                }
            }
            ft.remove(prev)
        }
        ft.add(this, javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }

    /**
     * 默认配置
     */
    override fun initConfig() {}

    fun setDialogClickListener(dialogClickListener: DialogClickListener<*>?): BaseDialog {
        this.dialogClickListener = dialogClickListener
        return this
    }
}