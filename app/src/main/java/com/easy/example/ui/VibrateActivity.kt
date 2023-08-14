package com.easy.example.ui

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.VibrateUtils
import com.easy.example.databinding.ActivityVibrateBinding

 /**
  * @Author : huangqiqiang
  * @Package : com.easy.example.ui
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