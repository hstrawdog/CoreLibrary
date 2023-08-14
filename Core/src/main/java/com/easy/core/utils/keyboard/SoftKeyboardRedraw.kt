package com.easy.core.utils.keyboard

import android.R
import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout
import com.easy.core.utils.ScreenUtils

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   SoftHideKeyboardRedraw
 * @Date : 2018/12/21 0021
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  解决键盘档住输入框
 * 键盘显示的时候 重新绘制view 解决底部按钮被遮挡的问题
 */
class SoftKeyboardRedraw private constructor(
    activity: Activity,
    content: FrameLayout = activity.findViewById<View>(R.id.content) as FrameLayout
) {
    /**
     * 根布局
     */
    private val mChildOfContent: View = content.getChildAt(0)

    /**
     * 布局当前实际高度
     */
    private var mUsableHeightPrevious = 0

    /**
     * 为适应华为小米等手机键盘上方出现黑条或不适配
     */
    private val mFrameLayoutParams: FrameLayout.LayoutParams

    /**
     * 获取setContentView本来view的高度
     */
    private var mContentHeight = 0

    /**
     * 状态栏高度
     */
    private val mStatusBarHeight: Int = ScreenUtils.getStatusBarHeight(activity)

    init {
        //1､找到Activity的最外层布局控件，它其实是一个DecorView,它所用的控件就是FrameLayout
        //2､获取到setContentView放进去的View
        //3､给Activity的xml布局设置View树监听，当布局有变化，如键盘弹出或收起时，都会回调此监听
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener {
            if (mContentHeight <= 0) {
                //兼容华为等机型
                mContentHeight = mChildOfContent.height
            }
            //5､当前布局发生变化时，对Activity的xml布局进行重绘
            possiblyResizeChildOfContent()
        }
        //6､获取到Activity的xml布局的放置参数
        mFrameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    /**
     * 获取界面可用高度，如果软键盘弹起后，Activity的xml布局可用高度需要减去键盘高度
     */
    private fun possiblyResizeChildOfContent() {
        //1､获取当前界面可用高度，键盘弹起后，当前界面可用布局会减少键盘的高度
        val usableHeightNow = computeUsableHeight()
        //2､如果当前可用高度和原始值不一样
        if (usableHeightNow != mUsableHeightPrevious) {
            //3､获取Activity中xml中布局在当前界面显示的高度
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            //4､Activity中xml布局的高度-当前可用高度
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            //5､高度差大于屏幕1/4时，说明键盘弹出
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // 6､键盘弹出了，Activity的xml布局高度应当减去键盘高度
                mFrameLayoutParams.height =
                    usableHeightSansKeyboard - heightDifference + mStatusBarHeight
            } else {
                mFrameLayoutParams.height = mContentHeight
            }
            //7､ 重绘Activity的xml布局
            mChildOfContent.requestLayout()
            mUsableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        // 全屏模式下：直接返回r.bottom，r.top其实是状态栏的高度
        return r.bottom - r.top
    }

    companion object {
        fun assistActivity(activity: Activity) {
            SoftKeyboardRedraw(activity)
        }

        fun assistActivity(activity: Activity, content: FrameLayout) {
            SoftKeyboardRedraw(activity, content)
        }
    }


}