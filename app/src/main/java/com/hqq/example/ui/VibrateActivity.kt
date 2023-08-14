package com.hqq.example.ui

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.VibrateUtils
import com.hqq.example.databinding.ActivityVibrateBinding

 /**
  * @Author : huangqiqiang
  * @Package : com.hqq.example.ui
  * @FileName :   VibrateActivity.kt
  * @Date  : 2023/7/18  14:22
  * @Email :  qiqiang213@gmail.com
  * @Describe : 震动相关类
  */
class VibrateActivity : BaseViewBindingActivity<ActivityVibrateBinding>() {

    override fun initView() {
        binding.button72.setOnClickListener {
            VibrateUtils.vibrateOnce(this, 200 )
        }
    }
}