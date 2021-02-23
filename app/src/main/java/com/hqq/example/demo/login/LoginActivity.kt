package com.hqq.example.demo.login

import android.app.Activity
import android.content.Intent
import android.view.KeyEvent
import com.hqq.core.ui.base.BaseVmActivity
import com.hqq.core.utils.keyboard.SoftKeyboardListener
import com.hqq.core.utils.keyboard.SoftKeyboardRedraw
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import com.hqq.example.databinding.ActivityLoginBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   LoginActivity
 * @Date  : 2020/7/28 0028  下午 4:06
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class LoginActivity : BaseVmActivity<LoginViewModel, ActivityLoginBinding>() {

    override val bindingViewModelId: Int = 0

    override val layoutId: Int = R.layout.activity_login

    override fun initViews() {

        SoftKeyboardRedraw.assistActivity(this)

        SoftKeyboardListener.setListener(this, object : SoftKeyboardListener.SoftKeyBoardChangeListener {
            override fun onKeyBoardShow(height: Int) {
                LogUtils.e("onKeyBoardShow  显示 " + height)
            }

            override fun onKeyBoardHide(height: Int) {
                LogUtils.e("onKeyBoardHide  隐藏" + height)
            }

        })

    }


    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}