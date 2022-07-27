package com.example.opengl

import com.example.opengl.part.Part1Activity
import com.example.opengl.part.Part2Activity
import com.example.opengl.triangle.TriangleActivity
import com.example.opengl.triangle.TriangleCppActivity
import com.hqq.core.ui.base.open
import com.hqq.core.ui.list.BaseListActivity

class MainActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
//        open(CreateOpenGLActivity::class.java)
     open(TriangleActivity::class.java)
//        open(TriangleCppActivity::class.java)
//        open(Part1Activity::class.java)
//        open(Part2Activity::class.java)
    }

}