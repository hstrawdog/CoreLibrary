package com.hqq.core.utils;

import android.app.Activity;

import com.hqq.core.listenner.OnCompressListener;

import java.io.File;
import java.util.List;

import top.zibin.luban.Luban;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   LuBanUttils
 * @Date : 2019/2/21 0021  下午 2:30
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class LuBanUttils {
    /**
     * @param activity
     * @param path
     * @param size
     * @param onCompressListener
     */
    public static void compression(Activity activity, String path, int size, final OnCompressListener onCompressListener) {

        Luban.with(activity).load(path).ignoreBy(size).setCompressListener(new top.zibin.luban.OnCompressListener() {
            @Override
            public void onStart() {
                onCompressListener.onStart();
            }

            @Override
            public void onSuccess(File file) {
                onCompressListener.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
                onCompressListener.onError(e);
            }
        }).launch();
    }

    /**
     * @param activity
     * @param path
     * @param size
     * @param onCompressListener
     */
    public static void compression(Activity activity, List<String> path, int size, final OnCompressListener onCompressListener) {

        Luban.with(activity).load(path).ignoreBy(size).setCompressListener(new top.zibin.luban.OnCompressListener() {
            @Override
            public void onStart() {
                onCompressListener.onStart();
            }

            @Override
            public void onSuccess(File file) {
                onCompressListener.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
                onCompressListener.onError(e);
            }
        }).launch();
    }

}
