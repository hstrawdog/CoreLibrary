package com.easy.example.ui.crash

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.databinding.ActivityCrashBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 下午 2:43
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class CrashActivity : BaseViewBindingActivity<ActivityCrashBinding>()  {
    override fun initView() {
        binding.button55.setOnClickListener {
            throw IllegalStateException("are you ok?")
        }
        binding.button56.setOnClickListener {
            throw NullPointerException("are you ok?")
        }
    }
}