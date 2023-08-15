package com.easy.example.ui.bar

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity.bar
 * @FileName :   SettingToolBarActivity
 * @Date : 2019/5/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class SettingToolBarActivity : BaseActivity() {


    override fun getLayoutViewId(): Int {
        return   R.layout.activity_setting_tool_bar
    }

    override fun initView() {
        iToolBar?.setToolBarColor(R.color.color_main)
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, SettingToolBarActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}