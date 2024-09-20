package com.easy.example.ui.animate

import android.content.Context
import android.content.Intent
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter

class AnimateIndexActivity2(override val adapter: MainAdapter=MainAdapter()) : BaseListActivity() {
    override fun initData() {
        adapter!!.add(com.easy.example.bean.MainBean("隐藏动画", com.easy.example.ui.animate.ViewAnimateActivity::class.java))
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, AnimateIndexActivity2::class.java)
            context.startActivity(starter)
        }
    }
}