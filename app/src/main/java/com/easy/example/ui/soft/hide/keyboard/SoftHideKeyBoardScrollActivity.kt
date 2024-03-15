package com.easy.example.ui.soft.hide.keyboard

import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.keyboard.SoftKeyboardUtils.keepViewNotOverOnScroll
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core_app.ui.activity.soft_hide_keyboard
 * @FileName :   SoftHideKeyBoardScrollActivity
 * @Date : 2019/6/4 0004  上午 11:43
 * @Email :  qiqiang213@gmail.com
 * @Describe : 键盘遮挡 部分界面滑动
 */
class SoftHideKeyBoardScrollActivity : BaseActivity() {

    override fun getLayoutViewId(): Int {
        return  R.layout.activity_soft_hide_key_board_scoller
    }

    override fun initView() {

        val offset = -20f

        keepViewNotOverOnScroll(findViewById(R.id.ll_login), findViewById(R.id.tv_login3), -offset)
//        softwareObserverAndTranslation()
    }

}