package com.hqq.example.dialog

import android.view.Gravity
import androidx.fragment.app.FragmentManager
import com.hqq.core.ui.dialog.BaseDialog
import com.hqq.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.core.app.dialog.RightDialog.java
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

    override val layoutId: Int = R.layout.dialog_select

    override val gravity: Int = Gravity.CENTER

    override val animation: Int = R.style.dialogAnimation_fade_in2fade_out

    override fun initView() {}

}