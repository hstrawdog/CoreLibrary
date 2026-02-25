package com.easy.example.demo.login

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseVmActivity
import com.easy.core.utils.keyboard.SoftKeyboardListener
import com.easy.core.utils.keyboard.SoftKeyboardRedraw
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityLoginBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo
 * @FileName :   LoginActivity
 * @Date  : 2020/7/28 0028  下午 4:06
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class LoginActivity : BaseVmActivity<LoginViewModel, ActivityLoginBinding>() {


    override fun getLayoutId(): Int {
       return R.layout.activity_login
    }

//    override fun bindingViewModelId(): Int {
//       return  0
//    }

    override fun initViews() {

        SoftKeyboardRedraw.assistActivity(this)

        SoftKeyboardListener.setListener(this, object : SoftKeyboardListener.SoftKeyBoardChangeListener {
            override fun onKeyBoardShow(height: Int) {
                LogUtils.dInfo{"onKeyBoardShow  显示 " + height}
            }

            override fun onKeyBoardHide(height: Int) {
                LogUtils.dInfo{"onKeyBoardHide  隐藏" + height}
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