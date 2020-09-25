package com.hqq.example.ui.list

import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.view.page.IFragmentActivityBuilder

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.list
 * @FileName :   ListIndexActivity
 * @Date : 2019/5/25 0025  下午 5:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class ListIndexActivity(override val baseAdapter: MainAdapter=MainAdapter()) : BaseListActivity<MainAdapter>() {

    override fun initData() {
        baseAdapter!!.addData(MainBean("加载数据", LoadMoreActivity::class.java))
        baseAdapter!!.addData(MainBean("fragment 加载", IFragmentActivityBuilder::class.java))
        baseAdapter!!.addData(MainBean("ListActivity 加载", ListActivity::class.java))
    }

}