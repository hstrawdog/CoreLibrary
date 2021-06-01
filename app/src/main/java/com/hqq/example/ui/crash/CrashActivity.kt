package com.hqq.example.ui.crash

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.databinding.ActivityCrashBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
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