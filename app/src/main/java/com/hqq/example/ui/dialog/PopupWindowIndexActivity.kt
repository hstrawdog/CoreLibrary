package com.hqq.example.ui.dialog

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityPopupWindowIndexBinding
import com.hqq.example.dialog.MyPopupWindow

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.dialog
 * @Date : 17:26
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class PopupWindowIndexActivity : BaseViewBindingActivity<ActivityPopupWindowIndexBinding>() {

    override fun initView() {
        binding.textView41.setOnClickListener {
            MyPopupWindow(this).showPopupWindow(binding.textView41)
        }

    }
}