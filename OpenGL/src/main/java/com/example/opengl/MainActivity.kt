package com.example.opengl

import com.example.opengl.error.Part3Activity
import com.example.opengl.error.TriangleCppActivity
import com.hqq.core.ui.base.open
import com.hqq.core.ui.list.BaseListActivity

class MainActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
//        open(CreateOpenGLActivity::class.java)
     open(Part3Activity::class.java)
//        open(TriangleCppActivity::class.java)
//        open(Part1Activity::class.java)
//        open(Part2Activity::class.java)
    }

}