package com.hqq.example.ui.jetpack.databinding

import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.demo.weather.WeatherActivity
import com.hqq.example.ui.jetpack.livedata.LiveDateActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.databinding
 * @Date : 9:50
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BindingIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(MainBean("Fragment 测试 viewModel", FragmentTestActivity::class.java))
        adapter.addData(MainBean("DateBinding测试", LiveDateActivity::class.java))
        adapter.addData(MainBean("dataBinding测试", DataBindingActivity::class.java))
    }
}