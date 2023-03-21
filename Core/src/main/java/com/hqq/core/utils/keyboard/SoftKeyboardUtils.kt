package com.hqq.core.utils.keyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.hqq.core.utils.keyboard.SoftKeyboardListener.SoftKeyBoardChangeListener

/**
 * 键盘 开启/关闭
 *
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   SoftHideKeyboardUtils
 * @Date : 2018/9/26 0026  下午 6:20
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
 */
object SoftKeyboardUtils {

    /**
     * 滑动 rootView  保证  subView 不被键盘遮挡
     * @param root View  需要滑动的布局
     * @param subView View  需要不被遮挡的布局
     * @param offset Int  偏移量:正数是底部距离  单位:px
     */
    @JvmStatic
    fun keepViewNotOverOnScroll(root: View, subView: View, offset: Float = 0f) {
        SoftKeyboardListener.setListener(root.context as Activity,
            object : SoftKeyBoardChangeListener {
                override fun onKeyBoardShow(height: Int) {
                    // view 距离底部的距离
                    val bottom = root.height - subView.bottom
                    val scrollHeight =
                        root.height - (root.height - height) - bottom + offset.toInt()
                    if (scrollHeight > 0) {
                        root.scrollTo(0, scrollHeight)
                    }
                }

                override fun onKeyBoardHide(height: Int) {
                    root.scrollTo(0, 0)
                }
            })


    }

    /**
     * 重绘Activity
     *  整个界面绘制    正常 是折叠 EditText
     * @param activity
     */
    @JvmStatic
    fun softHideKeyboardRedraw(activity: Activity) {
        SoftKeyboardRedraw.assistActivity(activity)
    }

    /**
     * 同上
     *
     * @param activity
     * @param frameLayout
     */
    fun softHideKeyboardRedraw(activity: Activity, frameLayout: FrameLayout) {
        SoftKeyboardRedraw.assistActivity(activity, frameLayout)
    }

    /**
     * 监听 键盘是否显示 隐藏
     *
     * @param activity
     * @param listener
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun addSoftHideKeyboardListener(
        activity: Activity, listener: SoftKeyBoardChangeListener?
    ): SoftKeyboardListener {
        return SoftKeyboardListener.setListener(activity, listener)
    }

    /**
     * Show the soft input.
     *
     * @param activity The activity.
     */
    fun showSoftInput(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * Show the soft input.
     *
     * @param window The Window.
     */
    @Deprecated("大部分情况下无效`")
    fun showSoftInput(window: Window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    fun showSoftInput(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    fun hideSoftInput(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    fun hideSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}