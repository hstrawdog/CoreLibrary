package com.hqq.example.ui.soft.hide.keyboard

import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.demo.login.LoginActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.soft_hide_keyboard
 * @FileName :   SoftHidKeyboardIndexActivity
 * @Date  : 2019/6/4 0004  上午 11:40
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class SoftHidKeyboardIndexActivity(override val adapter: MainAdapter = MainAdapter()) :
    BaseListActivity() {

    override fun initData() {
        adapter.addData(MainBean("键盘遮挡适配 整页滑动", SoftHideKeyBoardActivity::class.java))
        adapter.addData(MainBean("键盘遮挡适配 部分界面滑动", SoftHideKeyBoardScrollActivity::class.java))
        adapter.addData(MainBean("键盘适配 部分界面折叠 效果与滑动类似 ", LoginActivity::class.java))
        adapter.addData(MainBean("键盘常用操作 ", DefSofKeyBoardAndShowActivity::class.java))

    }
}