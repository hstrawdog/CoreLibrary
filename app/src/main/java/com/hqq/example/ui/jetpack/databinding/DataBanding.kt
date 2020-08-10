package com.hqq.example.ui.jetpack.databinding

import android.view.View
import com.hqq.core.utils.ToastUtils.showToast
import com.hqq.core.utils.log.LogUtils.e

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.viewbinding
 * @FileName :   ViewBanding
 * @Date : 2020/7/21 0021  下午 3:03
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class DataBanding {
    fun onClickButton(v: View?) {
        showToast("onClickButton")
        e("-----------onClickButton")
    }

    fun onClickButton4Text(name: String) {
        showToast("onClickButton   $name")
        e("-----------onClickButton4Text")
    }
}