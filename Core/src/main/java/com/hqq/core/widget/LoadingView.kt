/*
 * Copyright (c) 2017. auth huangqiqiang Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.hqq.core.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hqq.core.R

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.widget
 * @FileName :   LoadingView
 * @Date  : 2017-02-11 22:02
 * @Email :  qiqiang213@gmail.com
 * @Descrive : loadingView
 */
class LoadingView //        this.rlContent.setVisibility(0);
//        if(this.circleAnim != null) {
//            this.ivLoading.startAnimation(this.circleAnim);
//        }
//
//    }
//
//    public void stop() {
//        if(this.ivLoading != null) {
//            this.ivLoading.clearAnimation();
//        }
//
//        this.rlContent.setVisibility(8);
//    }
@JvmOverloads constructor(var mContext: Context?, themeResId: Int = R.style.LoadingDialogStyle) : Dialog(mContext!!, themeResId) {
    private var tvMsg: TextView? = null
    override fun dismiss() {
        if (this == null) {
            return
        }
        super.dismiss()
    }

    var mMsg = "正在加载中"

    constructor(context: Context?, msg: String) : this(context, R.style.LoadingDialogStyle) {
        mMsg = msg
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_layout)
        tvMsg = findViewById<View>(R.id.tv_msg) as TextView
        tvMsg!!.text = mMsg
        //这里我选了最高级
        // getWindow().setType(WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL);
    }

    fun setBg() {
        (findViewById<View>(R.id.rl_loading) as ImageView).setBackgroundResource(R.color.transparent)
    }

    fun setTipMsg(msg: String?) {
        tvMsg!!.text = msg
    }

    fun setTipMsg(msg: Int) {
        tvMsg!!.setText(msg)
    } //    public void start() {

}