package com.example.opengl.part

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opengl.ToolUtils
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl.part
 * @FileName :   Part2Activity.kt
 * @Date  : 22-05-06 0006  15:21
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 * https://blog.csdn.net/donkor_/article/details/77837863
 *    绘制一个点
 */
class Part2Activity : BasePartActivity() {
    override fun getRenderer(): GLSurfaceView.Renderer {
        return MyRenderer()
    }

    class MyRenderer internal constructor() : GLSurfaceView.Renderer {
        //顶点数组
        private val mArrayVertex = floatArrayOf(0f, 0f, 0f)

        // 缓冲区
        private val mBuffer: FloatBuffer = ToolUtils.getFloatBuffer(mArrayVertex)

        // Surface创建的时候调用
        override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
            // 设置清屏颜色为黑色（rgba）
            gl.glClearColor(0f, 0f, 0f, 0f)
        }

        // Surface改变的的时候调用
        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            // 设置OpenGL场景的大小
            gl.glViewport(width / 4, width / 2, width / 2, height / 2)
        }

        // 在Surface上绘制的时候调用
        override fun onDrawFrame(gl: GL10) {
            // 清除屏幕
            //GL_COLOR_BUFFER_BIT —— 表明颜色缓冲区
            //GL_DEPTH_BUFFER_BIT —— 表明深度缓冲
            //GL_STENCIL_BUFFER_BIT —— 表明模型缓冲区
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

            // 允许设置顶点 // GL10.GL_VERTEX_ARRAY顶点数组

//            GL_COLOR_ARRAY —— 如果启用，颜色矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glColorPointer。
//            GL_NORMAL_ARRAY —— 如果启用，法线矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glNormalPointer。
//            GL_TEXTURE_COORD_ARRAY —— 如果启用，纹理坐标矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glTexCoordPointer。
//            GL_VERTEX_ARRAY —— 如果启用，顶点矩阵可以用来写入以及调用glDrawArrays方法或者glDrawElements方法时进行渲染。详见glVertexPointer。
//            GL_POINT_SIZE_ARRAY_OES(OES_point_size_arrayextension)——如果启用，点大小矩阵控制大小以渲染点和点sprites。这时由glPointSize定义的点大小将被忽略，由点大小矩阵 提供的大小将被用来渲染点和点sprites。详见glPointSize。

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)

            // 设置顶点
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBuffer)

            //设置点的颜色为红色
            gl.glColor4f(1f, 0f, 0f, 0f)

            //设置点的大小
            gl.glPointSize(100f)

            // 绘制点
//mode
//            GL_TRIANGLES：每三个顶之间绘制三角形，之间不连接
//            GL_TRIANGLE_FAN：以V0 V1 V2,V0 V2 V3,V0 V3 V4，……的形式绘制三角形
//            GL_TRIANGLE_STRIP：顺序在每三个顶点之间均绘制三角形。这个方法可以保证从相同的方向上所有三角形均被绘制。以V0 V1 V2 ,V1 V2 V3,V2 V3 V4,……的形式绘制三角形
            gl.glDrawArrays(GL10.GL_POINTS, 0, 1)

            // 禁止顶点设置
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        }


    }

}
