package com.hqq.example.ui.soft.hide.keyboard

import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import com.hqq.core.toolbar.DefToolBar
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ResourcesUtils
import com.hqq.core.utils.ScreenUtils
import com.hqq.core.utils.keyboard.SoftKeyboardUtils.keepViewNotOverOnScroll
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.soft_hide_keyboard
 * @FileName :   SoftHideKeyBoardScrollActivity
 * @Date : 2019/6/4 0004  上午 11:43
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 键盘遮挡 部分界面滑动
 */
class SoftHideKeyBoardScrollActivity : BaseActivity() {

    override val layoutViewId: Int = R.layout.activity_soft_hide_key_board_scoller

    override fun initView() {

        val offset = -20f

        keepViewNotOverOnScroll(findViewById(R.id.ll_login), findViewById(R.id.tv_login3), -offset)
//        softwareObserverAndTranslation()
    }

}