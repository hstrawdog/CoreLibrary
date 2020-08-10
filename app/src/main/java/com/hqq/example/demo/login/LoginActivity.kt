package com.hqq.example.demo.login

import android.app.Activity
import android.content.Intent
import com.hqq.core.ui.vm.BaseVmActivity
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
class LoginActivity : BaseVmActivity<ActivityLoginBinding?, LoginModel?>() {
    override fun addViewModel() {
        mBinding!!.vm = mViewModel
    }

    override val bindingViewModelId: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.activity_login



    override fun initViews() {}

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}