package com.easy.example.ui.jetpack.databinding

import android.view.View
import com.easy.core.utils.ToastUtils.showToast
import com.easy.core.utils.log.LogUtils.e

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.viewbinding
 * @FileName :   ViewBanding
 * @Date : 2020/7/21 0021  下午 3:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DataBanding {
    fun onClickButton(v: View?) {
        showToast("onClickButton")
    }

    fun onClickButton4Text(name: String) {
        showToast("onClickButton   $name")
    }
}