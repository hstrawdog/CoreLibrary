package com.hqq.core.listenner

import android.view.View

/**
 * ClassName: OnPreventMultiClickListener
 * Package: com.qi.core.listenner
 * Author : zzy
 * Time : 2019/03/27
 * Description : 替换OnClickListener的防止多次点击事件监听
 * History:
 */
abstract class OnPreventMultiClickListener : View.OnClickListener {
    /**
     * 替换onClick的点击事件
     * @param v
     */
    abstract fun onMultiClick(v: View?)

    /**
     * 重写点击事件防止多次点击
     * @param v
     */
    override fun onClick(v: View) {
        val curClickTime = System.currentTimeMillis()
        if (curClickTime - mLastClickTime >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            mLastClickTime = curClickTime
            onMultiClick(v)
        }
    }

    companion object {
        /**
         * 两次点击按钮之间的点击间隔不能少于1000毫秒
         */
        private const val MIN_CLICK_DELAY_TIME = 1000

        /**
         * 最后一次点击时间
         */
        private var mLastClickTime: Long = 0
    }
}