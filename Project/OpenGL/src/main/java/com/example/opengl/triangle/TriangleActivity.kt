package com.example.opengl.triangle

import android.opengl.GLSurfaceView
import com.example.opengl.databinding.ActivityTriangleBinding
import com.easy.core.ui.base.BaseViewBindingActivity
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl
 * @Date : 10:19
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TriangleActivity : BaseViewBindingActivity<ActivityTriangleBinding>() {
    override fun initView() {
        binding.glSurface.setRenderer(MyGLRenderer())
    }

    class MyGLRenderer : GLSurfaceView.Renderer {


        private val mTriangleArray = floatArrayOf(
            0f, 0.5f, 0f,
            -1f, -1f, 0f,
            1f, -1f, 0f
        )

        //三角形各顶点颜色(三个顶点)
        private val mColor = floatArrayOf(1f, 1f, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 1f, 1f)
        private var mTriangleBuffer: FloatBuffer? = null
        private var mColorBuffer: FloatBuffer? = null


        init {

            //点相关
            //先初始化buffer，数组的长度*4，因为一个float占4个字节
            val bb: ByteBuffer = ByteBuffer.allocateDirect(mTriangleArray.size * 4)
            //以本机字节顺序来修改此缓冲区的字节顺序
            bb.order(ByteOrder.nativeOrder())
            mTriangleBuffer = bb.asFloatBuffer()
            //将给定float[]数据从当前位置开始，依次写入此缓冲区
            mTriangleBuffer?.put(mTriangleArray)
            //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
            mTriangleBuffer?.position(0)

            //颜色相关
            val bb2: ByteBuffer = ByteBuffer.allocateDirect(mColor.size * 4)
            bb2.order(ByteOrder.nativeOrder())
            mColorBuffer = bb2.asFloatBuffer()
            mColorBuffer?.put(mColor)
            mColorBuffer?.position(0)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            // 设置白色为清屏
            gl?.glClearColor(1f, 1f, 1f, 1f);
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

            val ratio = width.toFloat() / height
            // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
            gl!!.glViewport(0, 0, width, height)
            // 设置投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION)
            // 重置投影矩阵
            gl.glLoadIdentity()
            // 设置视口的大小
            gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)
            //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
            gl.glMatrixMode(GL10.GL_MODELVIEW)
            gl.glLoadIdentity()
        }

        override fun onDrawFrame(gl: GL10?) {

            // 清除屏幕和深度缓存
            gl!!.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
            // 重置当前的模型观察矩阵
            gl.glLoadIdentity()

            // 允许设置顶点
            //GL10.GL_VERTEX_ARRAY顶点数组
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
            // 允许设置颜色
            //GL10.GL_COLOR_ARRAY颜色数组
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY)

            //将三角形在z轴上移动
            gl.glTranslatef(0f, 0.5f, -2.5f)

            // 设置三角形
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer)
            // 设置三角形颜色
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer)
            // 绘制三角形
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3)
            // 取消颜色设置
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY)
            // 取消顶点设置
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)

            //绘制结束
            gl.glFinish()
        }

    }
}