/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.hqq.core.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.hqq.core.CoreBuildConfig

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   ToastUtils
 * @Date : 2018/12/14 0014
 * @Descrive : 输出工具类
 * @Email :  qiqiang213@gmail.com
 */
object ToastUtils {
    /**
     * 全局单利
     * 吐司部分，只显示一个
     */
    private var mToast: Toast? = null
    private fun showToast(context: Context?, text: String, duration: Int) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (mToast != null) {
            mToast!!.cancel()
        }
        mToast = Toast.makeText(context!!.applicationContext, text, duration)
        mToast.setGravity(Gravity.CENTER, 0, 0)
        mToast.setText(text)
        mToast.show()
    }

    fun showToast(context: Context?, text: String) {
        showToast(context, text, Toast.LENGTH_SHORT)
    }

    /**
     * @param context  content
     * @param strId    String id
     * @param duration 显示时间
     */
    fun showToast(context: Context, @StringRes strId: Int, duration: Int) {
        showToast(context, context.getString(strId), duration)
    }

    /**
     * 同上
     *
     * @param context content
     * @param strId   内容
     */
    fun showToast(context: Context, @StringRes strId: Int) {
        showToast(context, context.getString(strId), Toast.LENGTH_LONG)
    }

    /**
     * 默认显示   content 是Application
     *
     * @param text 内容
     */
    @kotlin.jvm.JvmStatic
    fun showToast(text: String) {
        showToast(CoreBuildConfig.Companion.getInstance().getApplication().getBaseContext(), text)
    }
}