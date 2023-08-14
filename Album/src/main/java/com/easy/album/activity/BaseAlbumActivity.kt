package com.easy.album.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.easy.album.AppManager
import com.easy.core.ui.base.BaseViewBindingActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.album.activity
 * @Date : 14:55
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseAlbumActivity<T : ViewBinding> : BaseViewBindingActivity<T>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 添加Activity到堆栈
        AppManager.appManager?.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 结束Activity&从堆栈中移除
        AppManager.appManager?.finishActivity(this)
    }
}

