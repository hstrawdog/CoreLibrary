package com.easy.example.ui.toast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.toast
 * @FileName :   ToastActivity
 * @Date : 2020/6/23 0023  下午 1:57
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class ToastActivity : BaseActivity() {


    override fun getLayoutViewId(): Int {
        return  R.layout.activity_toast
    }

    override fun initView() {
        findViewById<View>(R.id.button35).setOnClickListener { v: View? -> onClickButton(v) }
        findViewById<View>(R.id.button36).setOnClickListener { v: View? -> onClickButton2(v) }
    }

    private fun onClickButton(v: View?) {
        val toast = Toast(activity)
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.view_toast, null)
        toast.view = view
        toast.show()
    }

    private fun onClickButton2(v: View?) {
        val toast = Toast(activity)
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.view_toast, null)
        toast.view = view
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, ToastActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}