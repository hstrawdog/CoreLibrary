package com.hqq.example.ui.soft.hide.keyboard

import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.soft_hide_keyboard
 * @FileName :   SoftHidKeyboardIndexActivity
 * @Date  : 2019/6/4 0004  上午 11:40
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class SoftHidKeyboardIndexActivity(override val adapter: MainAdapter = MainAdapter()) : BaseListActivity() {

    override fun initData() {
        var bean = MainBean("底部按钮测试", SoftHideKeyBoardActivity::class.java);
        adapter.addData(bean)
        adapter.addData(MainBean("遮挡滑动测试", SoftHideKeyBoardScrollActivity::class.java))
    }
}