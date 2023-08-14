package com.easy.example.ui.jetpack.databinding

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.ui.jetpack.livedata.LiveDateActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.databinding
 * @Date : 9:50
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BindingIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(com.easy.example.bean.MainBean("Fragment 测试 viewModel", FragmentTestActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("DateBinding测试", LiveDateActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("dataBinding测试", DataBindingActivity::class.java))
    }
}