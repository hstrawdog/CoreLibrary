package com.easy.core.utils.file

import android.app.Activity
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.utils
 * @FileName :   LuBanUttils
 * @Date : 2019/2/21 0021  下午 2:30
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object LuBanUtils {
    /**
     * 压缩单张图片
     *
     * @param activity
     * @param path
     * @param size
     * @param onCompressListener
     */
    @JvmStatic
    fun compression(activity: Activity?, path: String?, size: Int, onCompressListener: com.easy.core.listenner.OnCompressListener) {
        Luban.with(activity).load(path).ignoreBy(size)
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                    onCompressListener.onStart()
                }

                override fun onSuccess(file: File) {
                    onCompressListener.onSuccess(file)
                }

                override fun onError(e: Throwable) {
                    onCompressListener.onError(e)
                }
            }).launch()
    }

    /**
     * 压缩图片集合
     *
     * @param activity
     * @param path
     * @param size
     * @param onCompressListener
     */
    @JvmStatic
    fun compression(activity: Activity?, path: List<String>?, size: Int, onCompressListener: com.easy.core.listenner.OnCompressListener) {
        Luban.with(activity).load(path).ignoreBy(size)
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                    onCompressListener.onStart()
                }

                override fun onSuccess(file: File) {
                    onCompressListener.onSuccess(file)
                }

                override fun onError(e: Throwable) {
                    onCompressListener.onError(e)
                }
            }).launch()
    }
}