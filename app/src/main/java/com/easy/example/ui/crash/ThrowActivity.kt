package com.easy.example.ui.crash

import android.view.View
import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.log.LogUtils.dInfo
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   ThrowActivity
 * @Date : 2019/5/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Describe :异常
 */
class ThrowActivity : BaseActivity() {


    override fun getLayoutViewId(): Int {
        return  R.layout.activity_throw
    }

    override fun initView() {
        findViewById<View>(R.id.button11).setOnClickListener { view: View -> onViewClicked(view) }
        findViewById<View>(R.id.button12).setOnClickListener { view: View -> onViewClicked(view) }
    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.button11 -> dInfo(Exception("一个异常 "))
            R.id.button12 -> dInfo(RuntimeException("一个运行异常 "))
            else -> {
            }
        }
    }
}