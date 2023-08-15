package com.easy.example.ui.adaptation

import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.activity
 * @FileName :   LayoutTestActivity
 * @Date : 2019/7/2 0002  上午 10:11
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class LayoutTestActivity : BaseActivity() {

    override fun getLayoutViewId(): Int {
        return    R.layout.activity_layout_test
    }

    override fun initView() {}
}