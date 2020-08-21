package com.hqq.example.ui.dialog

import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.dialog
 * @FileName :   DialogIndexActivity
 * @Date : 2019/5/25 0025  下午 2:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class DialogIndexActivity(override val baseAdapter: MainAdapter?=MainAdapter()) : BaseListActivity<MainAdapter?>() {

    override fun initData() {
        baseAdapter?.addData(MainBean("仿知乎评论列表", BottomSheetActivity::class.java))
        baseAdapter?.addData(MainBean("dialog样式测试", TestDialogActivity::class.java))
    }
}