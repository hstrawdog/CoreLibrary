package com.hqq.album.common

import android.app.Activity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.common
 * @FileName :   Album
 * @Date : 2020/6/30 0030  上午 10:13
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class Album private constructor(activity: Activity?, fragment: Fragment? = null) {
    private val mContext: WeakReference<Activity?>
    private val mFragment: WeakReference<Fragment?>?

    private constructor(fragment: Fragment) : this(fragment.activity, fragment) {}

    init {
        mContext = WeakReference(activity)
        mFragment = WeakReference(fragment)
    }

    fun choose(valueTypeImage: Int): FunctionOptions {
        return FunctionOptions.instance.setAlbum(this)
            .setAlbumType(valueTypeImage)
    }

    val activity: Activity?
        get() = mContext.get()
    val fragment: Fragment?
        get() = mFragment?.get()

    companion object {
        @JvmStatic
        fun from(activity: Activity?): Album {
            return Album(activity)
        }

        @JvmStatic
        fun from(fragment: Fragment): Album {
            return Album(fragment.activity, fragment)
        }
    }
}