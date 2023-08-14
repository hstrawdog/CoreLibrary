package com.easy.example.dialog

import com.easy.core.kt.open
import com.easy.core.ui.dialog.BaseBindingDialog
import com.easy.core.utils.ToastUtils
import com.easy.example.databinding.DialogOpenActivityBinding
import com.easy.example.ui.ResultActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.dialog
 * @Date : 19:00
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class OpenActivityDialog : BaseBindingDialog<DialogOpenActivityBinding>() {
    override fun initView() {
        binding.button73.setOnClickListener {
            open(ResultActivity::class.java) {
                it?.data?.getStringExtra("data")?.let {
                    ToastUtils.showToast("button73:  " + it)
                }
            }
        }
        binding.button74.setOnClickListener {
            open(ResultActivity::class.java) {
                it?.data?.getStringExtra("data")?.let {
                    ToastUtils.showToast("button74:  " + it)
                }
            }
        }

    }
}