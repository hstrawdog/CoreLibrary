package com.hqq.example.ui.soft.hide.keyboard

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ToastUtils.showToast
import com.hqq.core.utils.keyboard.SoftKeyboardListener.SoftKeyBoardChangeListener
import com.hqq.core.utils.keyboard.SoftKeyboardUtils.addSoftHideKeyboardListener
import com.hqq.core.utils.keyboard.SoftKeyboardUtils.softHideKeyboardRedraw
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   SoftHideKeyBoardActivity
 * @Date : 2019/6/4 0004
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class SoftHideKeyBoardActivity : BaseActivity() {


    override val layoutViewId: Int
        get() = R.layout.activity_soft_hide_key_board
    override fun initView() {
        softHideKeyboardRedraw(this)
        addSoftHideKeyboardListener(this, object : SoftKeyBoardChangeListener {
            override fun onKeyBoardShow(height: Int) {
                showToast("键盘显示")
            }

            override fun onKeyBoardHide(height: Int) {
                showToast("键盘隐藏")
            }
        })
    }
}