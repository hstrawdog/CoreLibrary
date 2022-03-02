package com.hqq.example.ui.view

import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.view
 * @Date : 15:13
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SwitchActivity : BaseActivity() {
    override val layoutViewId: Int
        get() = R.layout.activity_switch

    /**
     * 原生自带的switch
     * 关闭的文字 无法显示出来
     */
    override fun initView() {}
}