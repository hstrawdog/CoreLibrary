package com.hqq.example.ui.bar

import android.app.Activity
import android.content.Intent
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.bar
 * @FileName :   SettingToolBarActivity
 * @Date : 2019/5/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class SettingToolBarActivity : BaseActivity() {

    override val layoutViewId: Int
        get() = R.layout.activity_setting_tool_bar

    override fun initView() {
        rootViewBuild?.getDefToolBar()?.setToolBarColor(R.color.color_main)
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, SettingToolBarActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}