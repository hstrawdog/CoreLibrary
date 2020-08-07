package com.hqq.core.utils.keyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.hqq.core.utils.keyboard.SoftHideKeyboardListener.OnSoftKeyBoardChangeListener
import com.hqq.core.utils.keyboard.SoftHideKeyboardRedraw

/**
 * 键盘 开启/关闭
 *
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   SoftHideKeyboardUtils
 * @Date : 2018/9/26 0026  下午 6:20
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 * https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
 */
object SoftHideKeyboardUtils {
    /**
     * Show the soft input.
     *
     * @param activity The activity.
     */
    fun showSoftInput(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ?: return
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
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ?: return
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

    /**
     * 重绘Activity
     *
     * @param activity
     */
    fun softHideKeyboardRedraw(activity: Activity) {
        SoftHideKeyboardRedraw.Companion.assistActivity(activity)
    }

    /**
     * 同上
     *
     * @param activity
     * @param frameLayout
     */
    fun softHideKeyboardRedraw(activity: Activity, frameLayout: FrameLayout) {
        SoftHideKeyboardRedraw.Companion.assistActivity(activity, frameLayout)
    }

    /**
     * 同上
     *
     * @param root  全屏的跟布局
     * @param child  更布局下的一个子View
     */
    @kotlin.jvm.JvmStatic
    fun addSoftHideKeyboardScrollView(root: View, child: View) {
        SoftHideKeyboardScrollView.keepLoginBtnNotOver(root, child)
    }

    /**
     * 监听 键盘是否显示 隐藏
     *
     * @param activity
     * @param listener
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun addSoftHideKeyboardListener(activity: Activity, listener: OnSoftKeyBoardChangeListener?): SoftHideKeyboardListener {
        return SoftHideKeyboardListener.Companion.setListener(activity, listener)
    }
}