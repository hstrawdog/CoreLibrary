package com.hqq.core.utils.keyboard

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import com.hqq.core.utils.keyboard.SoftKeyboardListener.SoftKeyBoardChangeListener
import com.hqq.core.utils.log.LogUtils

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
        SoftKeyboardListener.setListener(
            root.context as Activity,
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
    @JvmStatic
    private fun computeUsableHeight(activity :Activity, frameLayout: FrameLayout) {
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
    @JvmStatic
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
    @JvmStatic
    fun showSoftInput(window: Window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    @JvmStatic
    fun showSoftInput(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }


    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    @JvmStatic
    fun hideSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    /**
     * 避免输入法面板遮挡
     *
     * 在manifest.xml中activity中设置
     *
     * android:windowSoftInputMode="stateVisible|adjustResize"
     */
    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    @JvmStatic
    fun hideSoftInput(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputmanger =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputmanger.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 点击隐藏软键盘
     *
     * @param view
     */
    @JvmStatic
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 动态隐藏软键盘
     *
     * @param edit    输入框
     */
    @JvmStatic
    fun hideSoftInput(edit: EditText) {
        edit.clearFocus()
        val inputmanger =
            edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputmanger.hideSoftInputFromWindow(edit.windowToken, 0)
    }

    /**
     * 点击屏幕空白区域隐藏软键盘（方法1）
     *
     * 在onTouch中处理，未获焦点则隐藏
     *
     * 参照以下注释代码
     */
    @JvmStatic
    fun clickBlankArea2HideSoftInput0() {
        LogUtils.i("tips", "U should copy the following code.")/*
        @Override
        public boolean onTouchEvent (MotionEvent event){
            if (null != this.getCurrentFocus()) {
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            return super.onTouchEvent(event);
        }
        */
    }

    /**
     * 点击屏幕空白区域隐藏软键盘（方法2）
     *
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * 需重写dispatchTouchEvent
     *
     * 参照以下注释代码
     */
    @JvmStatic
    fun clickBlankArea2HideSoftInput1() {
        LogUtils.i("tips", "U should copy the following code.")/*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    hideKeyboard(v.getWindowToken());
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }

        // 获取InputMethodManager，隐藏软键盘
        private void hideKeyboard(IBinder token) {
            if (token != null) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        */
    }

    /**
     * 动态显示软键盘
     *
     * @param context 上下文
     * @param edit    输入框
     */
    @JvmStatic
    fun showSoftInput(context: Context, edit: EditText) {
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(edit, 0)
    }

    /**
     * 切换键盘显示与否状态
     *
     * @param context 上下文
     * @param edit    输入框
     */
    @JvmStatic
    fun toggleSoftInput(context: Context, edit: EditText) {
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}