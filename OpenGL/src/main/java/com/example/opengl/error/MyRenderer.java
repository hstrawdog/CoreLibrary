package com.example.opengl.error;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl.part
 * @Date : 17:47
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class MyRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // 初始化triangle
        mTriangle = new Triangle();
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public static int loadShader(int type, String shaderCode) {

        // glCreateShader函数创建一个顶点着色器或者片段着色器,并返回新创建着色器的ID引用
        int shader = GLES20.glCreateShader(type);
        // 把着色器和代码关联,然后编译着色器
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

}
