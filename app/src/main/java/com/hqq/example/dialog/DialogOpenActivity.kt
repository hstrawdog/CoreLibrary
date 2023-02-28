package com.hqq.example.dialog

import com.hqq.core.kt.open
import com.hqq.core.ui.dialog.BaseBindingDialog
import com.hqq.core.utils.ToastUtils
import com.hqq.example.databinding.DialogOpenActivityBinding
import com.hqq.example.ui.ResultActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.dialog
 * @Date : 19:00
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DialogOpenActivity : BaseBindingDialog<DialogOpenActivityBinding>() {
    override fun initView() {

        binding.button73.setOnClickListener {
            open(ResultActivity::class.java) {
                it?.data?.getStringExtra("data")?.let {
                    ToastUtils.showToast(it)
                }
            }
        }

    }
}