package com.easy.example.ui.crash

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.crash
 * @Date : 9:58
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ThrowIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(com.easy.example.bean.MainBean("奔溃后启动", CrashActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("Throw异常测试重启App", ThrowActivity::class.java))
    }
}