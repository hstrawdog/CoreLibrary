package com.example.opengl.part

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 *  最简单的 的使用
 *  https://blog.csdn.net/donkor_/article/details/71807627
 * @property glSurfaceView GLSurfaceView?
 */
class Part1Activity : BasePartActivity() {


    override fun getRenderer(): GLSurfaceView.Renderer {
        return MyRenderer()
    }

    class MyRenderer : GLSurfaceView.Renderer {
        // Surface创建的时候调用
        override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
            // 设置清屏颜色为红色
            gl.glClearColor(0f, 1f, 0f, 0f)
        }

        // Surface改变的的时候调用
        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            // 设置窗口大小
            gl.glViewport(0, 0, width, height)
        }

        // 在Surface上绘制的时候调用
        override fun onDrawFrame(gl: GL10) {
            // 清除屏幕
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
        }
    }


}