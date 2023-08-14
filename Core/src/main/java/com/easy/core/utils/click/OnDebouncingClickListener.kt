package com.easy.core.utils.click

import android.view.View

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.click
 * @Date : 11:09
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class OnDebouncingClickListener(private val mDuration: Long) : View.OnClickListener {

    abstract fun onDebouncingClick(v: View?)

    override fun onClick(v: View) {
        if (isValid(v, mDuration)) {
            onDebouncingClick(v)
        }
    }

    /**
     *
     * @param view View
     * @param duration Long
     * @return Boolean
     */
    private fun isValid(view: View, duration: Long): Boolean {
        return DebouncingUtils.isValid(view, duration)
    }
}