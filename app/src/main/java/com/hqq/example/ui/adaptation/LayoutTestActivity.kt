package com.hqq.example.ui.adaptation

import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity
 * @FileName :   LayoutTestActivity
 * @Date : 2019/7/2 0002  上午 10:11
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class LayoutTestActivity : BaseActivity() {
    override val layoutViewId: Int
        get() = R.layout.activity_layout_test

    override fun initView() {}
}