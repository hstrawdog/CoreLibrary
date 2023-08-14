package com.easy.example.ui.dialog

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.databinding.ActivityPopupWindowIndexBinding
import com.easy.example.dialog.MyPopupWindow

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.dialog
 * @Date : 17:26
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class PopupWindowIndexActivity : BaseViewBindingActivity<ActivityPopupWindowIndexBinding>() {

    override fun initView() {
        binding.textView41.setOnClickListener {
            com.easy.example.dialog.MyPopupWindow(this)
                .showPopupWindow(binding.textView41)
        }

    }
}