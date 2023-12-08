package com.easy.core.permission.dialog

import com.easy.core.R
import com.easy.core.databinding.DialogHuaweiTipBinding
import com.easy.core.ui.dialog.BaseBindingDialog
import com.easy.core.ui.dialog.BaseDialog

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.permission.dialog
 * @Date  : 15:53
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class HuaWeiTipDialog : BaseBindingDialog<DialogHuaweiTipBinding>() {

    var call: (() -> Unit)? = null
    var tipText = ""

    override fun initView() {
        binding.tvTip.text = tipText
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        binding.tvSure.setOnClickListener {
            call?.invoke()
            dismiss()
        }
    }

}