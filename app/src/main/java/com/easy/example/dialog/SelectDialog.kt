package com.easy.example.dialog

import android.view.Gravity
import androidx.fragment.app.FragmentManager
import com.easy.core.ui.dialog.BaseDialog
import com.easy.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.easy.core.app.dialog.RightDialog.java
 * @emain: 593979591@qq.com
 * @date: 2019-07-03 22:22
</描述当前版本功能> */
class SelectDialog : BaseDialog() {
    companion object {
        fun showDialog(fragmentManager: FragmentManager?) {
            val fragment = SelectDialog()
            fragment.show(fragmentManager!!)
        }
    }


    override fun getGravity(): Int {
        return Gravity.CENTER
    }

    override fun getAnimation(): Int {
        return  R.style.dialogAnimation_fade_in2fade_out
    }


    override fun getDialogLayoutId(): Int {
        return R.layout.dialog_select
    }

    override fun initView() {}

}