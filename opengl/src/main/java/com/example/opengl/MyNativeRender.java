package com.example.opengl;

/**
 * @Author : huangqiqiang
 * @Package : com.example.opengl
 * @Date : 10:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class MyNativeRender {
    static {
        System.loadLibrary("native-render");
    }

    public native void native_OnInit();

    public native void native_OnUnInit();

    public native void native_SetImageData(int format, int width, int height, byte[] bytes);

    public native void native_OnSurfaceCreated();

    public native void native_OnSurfaceChanged(int width, int height);

    public native void native_OnDrawFrame();

}
