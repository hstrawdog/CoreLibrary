package com.easy.example.ui.view

import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.view
 * @Date : 15:13
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SwitchActivity : BaseActivity() {
    override fun getLayoutViewId(): Int {
        return R.layout.activity_switch
    }

    /**
     * 原生自带的switch
     * 关闭的文字 无法显示出来
     */
    override fun initView() {}
}