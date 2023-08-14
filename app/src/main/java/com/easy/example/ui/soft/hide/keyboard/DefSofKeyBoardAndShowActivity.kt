package com.easy.example.ui.soft.hide.keyboard

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.keyboard.SoftKeyboardUtils
import com.easy.example.databinding.ActivityDefSoftKeyBoradAndShowBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.soft.hide.keyboard
 * @Date : 18:43
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DefSofKeyBoardAndShowActivity :
    BaseViewBindingActivity<ActivityDefSoftKeyBoradAndShowBinding>() {


    override fun initView() {
//        binding.editTextTextPersonName.requestFocus()
//        SoftKeyboardUtils.showSoftInput(this)

        binding.button77.setOnClickListener {
            SoftKeyboardUtils.hideSoftInput(this)
        }
        binding.button78.setOnClickListener {
            SoftKeyboardUtils.showSoftInput(binding.editTextTextPersonName, this)
        }
    }
}