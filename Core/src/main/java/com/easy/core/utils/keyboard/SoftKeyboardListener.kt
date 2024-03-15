package com.easy.core.utils.keyboard

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener

/**
 * @Author : huangqiqiang
 * @FileName :   SoftHideKeyboardListener
 * @Date : 2018/6/21 0021  下午 2:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * 监听键盘弹出收起
 *  来源 https://blog.csdn.net/u011181222/article/details/52043001
 */
class SoftKeyboardListener private constructor(activity: Activity) {
    companion object {
        fun setListener(activity: Activity, softKeyBoardChangeListener: SoftKeyBoardChangeListener?): SoftKeyboardListener {
            val softHideKeyBoardListener = SoftKeyboardListener(activity)
            softHideKeyBoardListener.softKeyBoardChangeListener = softKeyBoardChangeListener
            return softHideKeyBoardListener
        }
    }

    /**
     * 纪录根视图的显示高度
     */
    private var rootViewVisibleHeight = 0

    /**
     *  回调方法
     */
    var softKeyBoardChangeListener: SoftKeyBoardChangeListener? = null

    init {
        val rootView: View = activity.window.decorView
        //获取activity的根视图
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.viewTreeObserver.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            //获取当前根视图在屏幕上显示的大小
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            // 当前视图显示的高度
            val visibleHeight = r.height()
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            } else if (rootViewVisibleHeight == visibleHeight) {
                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                return@OnGlobalLayoutListener
            } else if (rootViewVisibleHeight - visibleHeight > 200) {
                //根视图显示高度变小超过200，可以看作软键盘显示了
                softKeyBoardChangeListener?.onKeyBoardShow(rootViewVisibleHeight - visibleHeight)
                rootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            } else if (visibleHeight - rootViewVisibleHeight > 200) {
                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                softKeyBoardChangeListener?.onKeyBoardHide(visibleHeight - rootViewVisibleHeight)
                rootViewVisibleHeight = visibleHeight
            }
        })
    }


    interface SoftKeyBoardChangeListener {
        /**
         * 显示
         *
         * @param height
         */
        fun onKeyBoardShow(height: Int)

        /**
         * 隐藏
         *
         * @param height
         */
        fun onKeyBoardHide(height: Int)
    }


}