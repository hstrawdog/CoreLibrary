package com.hqq.example.ui

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.VibrateTool
import com.hqq.example.databinding.ActivityVibrateBinding

/**
 * 震动相关类
 */
class VibrateActivity : BaseViewBindingActivity<ActivityVibrateBinding>() {

    override fun initView() {
        binding.button72.setOnClickListener {
            VibrateTool.vibrateOnce(this, 200 )
        }

    }
}