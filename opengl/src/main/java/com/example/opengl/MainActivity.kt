package com.example.opengl

import com.example.opengl.triangle.TriangleActivity
import com.hqq.core.ui.base.open
import com.hqq.core.ui.list.BaseListActivity

class MainActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
//        open(CreateOpenGLActivity::class.java)
        open(TriangleActivity::class.java)
    }

}