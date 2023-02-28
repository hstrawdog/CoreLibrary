package com.hqq.example.ui

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.rx.RxVibrateTool
import com.hqq.example.R
import com.hqq.example.databinding.ActivityVibrateBinding
import com.hqq.example.databinding.ActivityViewBindingBinding

/**
 * 震动相关类
 */
class VibrateActivity : BaseViewBindingActivity<ActivityVibrateBinding>() {

    override fun initView() {
        binding.button72.setOnClickListener {
            RxVibrateTool.vibrateOnce(this, 200 )
        }

    }
}