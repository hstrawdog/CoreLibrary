package com.easy.example.ui

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.ui.adaptation.DefImgActivity
import com.easy.example.ui.layout.fold.FoldViewActivity
import com.easy.example.ui.recycle.RecycleIndexActivity
import com.easy.example.ui.tab.layout.TabLayoutActivity
import com.easy.example.ui.web.WebActivity

class ViewIndexActivity2 : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.add(MainBean("网页测试", WebActivity::class.java))
        adapter.add(MainBean("RecycleView 相关", RecycleIndexActivity::class.java))
        adapter.add(MainBean("折叠布局", FoldViewActivity::class.java))
        adapter.add(MainBean("默认图显示", DefImgActivity::class.java))
        adapter.add(MainBean("TabLayoutActivity 相关", TabLayoutActivity::class.java))
    }
}