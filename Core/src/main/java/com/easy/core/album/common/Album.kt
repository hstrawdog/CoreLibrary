package com.easy.core.album.common

import android.app.Activity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.album.common
 * @FileName :   Album
 * @Date : 2020/6/30 0030  上午 10:13
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class Album private constructor() {

    companion object {
        val functionOptions = FunctionOptions()

        @JvmStatic
        fun from(valueTypeImage: Int): FunctionOptions {
            return functionOptions.choose(valueTypeImage)
        }
    }

}