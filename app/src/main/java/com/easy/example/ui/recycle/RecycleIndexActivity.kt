package com.easy.example.ui.recycle

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.ui.view.page.IFragmentActivityBuilder

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.recycle
 * @FileName :   RecycleIndexActivity
 * @Date : 2019/6/18 0018  下午 2:16
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class RecycleIndexActivity : BaseListActivity() {
    override fun initData() {
        adapter.add(MainBean("轮播图", BannerActivity::class.java))
        adapter.add(MainBean("多Item 分页滑动", FullPagerSnapActivity::class.java))
        adapter.add(MainBean("画廊 分页滑动", GallerySnapActivity::class.java))
        adapter.add(MainBean("空数据测试", EmptyListActivity::class.java))
        adapter.add(MainBean("加载数据", LoadMoreActivity::class.java))
        adapter.add(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        adapter.add(MainBean("Activity 悬停加载", ListActivity::class.java))
    }

    override var adapter = MainAdapter()
}