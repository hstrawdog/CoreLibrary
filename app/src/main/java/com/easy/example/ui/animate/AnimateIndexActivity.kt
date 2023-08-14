package com.easy.example.ui.animate

import android.content.Context
import android.content.Intent
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean

class AnimateIndexActivity(override val adapter: MainAdapter=MainAdapter()) : BaseListActivity() {
    override fun initData() {
        adapter!!.addData(com.easy.example.bean.MainBean("隐藏动画", com.easy.example.ui.animate.ViewAnimateActivity::class.java))
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, AnimateIndexActivity::class.java)
            context.startActivity(starter)
        }
    }
}