package com.example.opengl.error

import android.opengl.GLSurfaceView
import com.example.opengl.MyNativeRender
import com.example.opengl.databinding.ActivityTriangleBinding
import com.easy.core.ui.base.BaseViewBindingActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl
 * @Date : 10:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TriangleCppActivity : BaseViewBindingActivity<ActivityTriangleBinding>() {

    lateinit var mMyNativeRender: MyNativeRender

    override fun initView() {
        mMyNativeRender = MyNativeRender()
        binding.glSurface.setRenderer(Render(mMyNativeRender))
    }


    class Render(var mMyNativeRender: MyNativeRender) : GLSurfaceView.Renderer {
        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            mMyNativeRender.native_OnSurfaceCreated()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            mMyNativeRender.native_OnSurfaceChanged(width, height)
        }

        override fun onDrawFrame(gl: GL10?) {
            mMyNativeRender.native_OnDrawFrame()
        }

    }

}