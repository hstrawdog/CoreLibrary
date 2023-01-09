package com.hqq.example.ui

import com.hqq.core.ui.list.BaseListActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.launch.mode.SingleInstanceActivity
import com.hqq.example.ui.tab.layout.TabLayoutActivity

class ViewIndexActivity : BaseListActivity() {
    override val adapter:MainAdapter = MainAdapter()

    override fun initData() {

    adapter.addData(MainBean("TabLayoutActivity 相关", TabLayoutActivity::class.java))
    }
}