package com.hqq.example.ui.crash

import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.jetpack.databinding.DataBindingActivity
import com.hqq.example.ui.jetpack.databinding.FragmentTestActivity
import com.hqq.example.ui.jetpack.livedata.LiveDateActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.crash
 * @Date : 9:58
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ThrowIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(MainBean("奔溃后启动", CrashActivity::class.java))
        adapter.addData(MainBean("Throw异常测试重启App", ThrowActivity::class.java))
    }
}