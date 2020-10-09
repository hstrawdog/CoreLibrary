package com.hqq.example.ui.recycle

import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.view.page.IFragmentActivityBuilder

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.recycle
 * @FileName :   RecycleIndexActivity
 * @Date : 2019/6/18 0018  下午 2:16
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class RecycleIndexActivity : BaseListActivity() {
    override fun initData() {
        adapter.addData(MainBean("轮播图", BannerActivity::class.java))
        adapter.addData(MainBean("多Item 分页滑动", FullPagerSnapActivity::class.java))
        adapter.addData(MainBean("画廊 分页滑动", GallerySnapActivity::class.java))
        adapter.addData(MainBean("空数据测试", EmptyListActivity::class.java))
        adapter.addData(MainBean("加载数据", LoadMoreActivity::class.java))
        adapter.addData(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        adapter.addData(MainBean("Activity 悬停加载", ListActivity::class.java))
    }

    override var adapter = MainAdapter()
}