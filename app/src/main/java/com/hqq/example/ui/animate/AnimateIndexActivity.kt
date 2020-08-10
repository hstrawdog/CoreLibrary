package com.hqq.example.ui.animate

import android.content.Context
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.ui.base.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean

class AnimateIndexActivity : BaseListActivity<MainAdapter?>() {
    override fun initAdapter(): MainAdapter? {
        return MainAdapter()
    }

    override fun initData() {
        adapter!!.addData(MainBean("隐藏动画", ViewAnimateActivity::class.java))
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, AnimateIndexActivity::class.java)
            context.startActivity(starter)
        }
    }
}