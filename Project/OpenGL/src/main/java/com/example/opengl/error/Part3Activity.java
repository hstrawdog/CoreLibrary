package com.example.opengl.error;

import android.opengl.GLSurfaceView;

import androidx.annotation.NonNull;

import com.example.opengl.part.BasePartActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl.part
 * @Date : 16:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class Part3Activity extends BasePartActivity {
    @NonNull
    @Override
    public GLSurfaceView.Renderer getRenderer() {
        return new MyRenderer();
    }



}
