package com.easy.example.ui.soft.hide.keyboard

import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.ToastUtils.showToast
import com.easy.core.utils.keyboard.SoftKeyboardListener.SoftKeyBoardChangeListener
import com.easy.core.utils.keyboard.SoftKeyboardUtils.addSoftHideKeyboardListener
import com.easy.core.utils.keyboard.SoftKeyboardUtils.softHideKeyboardRedraw
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   SoftHideKeyBoardActivity
 * @Date : 2019/6/4 0004
 * @Email :  qiqiang213@gmail.com
 * @Describe :  键盘折叠  整个页面滑动
 */
class SoftHideKeyBoardActivity : BaseActivity() {


    override fun getLayoutViewId(): Int {
        return  R.layout.activity_soft_hide_key_board
    }

    /**
     *  布局中 存在一个  layout_weight  填充高度
     *  softHideKeyboardRedraw  才能达到 一个理想效果
     *
     */
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