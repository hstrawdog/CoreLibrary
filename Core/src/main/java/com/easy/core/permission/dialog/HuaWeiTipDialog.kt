package com.easy.core.permission.dialog

import android.content.DialogInterface
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import com.easy.core.databinding.DialogHuaweiTipBinding
import com.easy.core.ui.dialog.BaseBindingDialog

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.permission.dialog
 * @Date  : 15:53
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class HuaWeiTipDialog : BaseBindingDialog<DialogHuaweiTipBinding>() , DialogInterface.OnKeyListener{

    var call: ((isDefine: Boolean) -> Unit)? = null

    var tipText = ""

    override val isDismissBackground: Boolean=false
    override fun getDialogWeight(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    override fun getGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun initView() {
        binding.tvTip.text = tipText
        binding.tvCancel.setOnClickListener {
            call?.invoke(false)
            dismiss()
        }
        binding.tvSure.setOnClickListener {
            call?.invoke(true)
            dismiss()
        }
        dialog?.setOnKeyListener(this)

    }
    /**
     * 禁用返回键
     * @param dialog DialogInterface
     * @param keyCode Int
     * @param event KeyEvent
     * @return Boolean
     */
    override fun onKey(dialog:DialogInterface?, keyCode:Int, event:KeyEvent?):Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK
    }
}