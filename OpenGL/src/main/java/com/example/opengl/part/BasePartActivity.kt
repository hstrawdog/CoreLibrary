package com.example.opengl.part

import android.opengl.GLSurfaceView
import android.view.View
import com.hqq.core.ui.base.BaseActivity
import android.view.ViewGroup

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl.part
 * @Date : 15:11
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
open abstract class BasePartActivity : BaseActivity() {
    override val layoutViewId: Int
        get() = 0

    val glSurfaceView: GLSurfaceView by lazy {
        GLSurfaceView(activity)
    }

    override fun initView() {
        // 设置渲染器
        glSurfaceView.setRenderer(getRenderer())
    }

    abstract fun getRenderer(): GLSurfaceView.Renderer

    override fun getLayoutView(parent: ViewGroup): View? {
        return glSurfaceView
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }


    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

}