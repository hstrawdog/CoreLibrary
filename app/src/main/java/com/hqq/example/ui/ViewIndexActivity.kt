package com.hqq.example.ui

import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.adaptation.DefImgActivity
import com.hqq.example.ui.layout.fold.FoldViewActivity
import com.hqq.example.ui.recycle.RecycleIndexActivity
import com.hqq.example.ui.tab.layout.TabLayoutActivity
import com.hqq.example.ui.web.WebActivity

class ViewIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(MainBean("网页测试", WebActivity::class.java))
        adapter.addData(MainBean("RecycleView 相关", RecycleIndexActivity::class.java))
        adapter.addData(MainBean("折叠布局", FoldViewActivity::class.java))
        adapter.addData(MainBean("默认图显示", DefImgActivity::class.java))
        adapter.addData(MainBean("TabLayoutActivity 相关", TabLayoutActivity::class.java))
    }
}