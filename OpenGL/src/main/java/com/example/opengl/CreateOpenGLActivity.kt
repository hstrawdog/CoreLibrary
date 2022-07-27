package com.example.opengl

import android.opengl.GLSurfaceView
import android.opengl.GLU
import com.example.opengl.databinding.ActivityCreateOpenglBinding
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.log.LogUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl
 * @Date : 14:00
 * @Email : qiqiang213@gmail.com
 * @Describe : 设置背景
 */
class CreateOpenGLActivity : BaseViewBindingActivity<ActivityCreateOpenglBinding>() {

    override fun initView() {
        binding.GLSurfaceView.setRenderer(MyGLRenderer())
    }


    class MyGLRenderer : GLSurfaceView.Renderer {
        /**
         * 每次方向变化时调用 /   创建时        执行
         * @param gl GL10
         * @param config EGLConfig
         */
        override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
            LogUtils.dInfo("onSurfaceCreated")
            // 启用阴影平滑
            gl.glShadeModel(GL10.GL_SMOOTH);
            // 设置背景颜色
            gl.glClearColor(0.2f, 0.4f, 0.52f, 1.0f);

            // 设置深度缓存
            gl.glClearDepthf(1.0f);
            // 启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
            // 所作深度测试的类型
            gl.glDepthFunc(GL10.GL_LEQUAL);
            // 告诉系统对透视进行修正
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


        }

        /**
         *  改变了大小
         * @param gl GL10
         * @param width Int
         * @param height Int
         */
        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            LogUtils.dInfo("onSurfaceChanged")

            // 重置当前的视图区域
            gl.glViewport(0, 0, width, Math.max(1, height));
            // 选择投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            // 重置投影矩阵
            gl.glLoadIdentity();
            // 设置视图区域的大小
            GLU.gluPerspective(gl, 45.0f, width.toFloat() / Math.max(1, height), 0.1f, 100.0f);
            // 选择模型观察矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            // 重置模型观察矩阵
            gl.glLoadIdentity();

        }

        override fun onDrawFrame(gl: GL10?) {
            LogUtils.dInfo("onDrawFrame")
            // 清除屏幕和深度缓存
            gl!!.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
            // 重置当前的模型观察矩阵
            gl.glLoadIdentity()

        }

    }
}