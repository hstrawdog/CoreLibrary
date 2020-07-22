package com.hqq.example.ui.jetpack.databinding;

import android.view.View;

import com.hqq.core.utils.ToastUtils;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.viewbinding
 * @FileName :   ViewBanding
 * @Date : 2020/7/21 0021  下午 3:03
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class DataBanding {
    public void onClickButton(View v) {
        ToastUtils.showToast("onClickButton");
        LogUtils.e("-----------onClickButton");

    }

    public void onClickButton4Text(String name) {
        ToastUtils.showToast("onClickButton   " +name);
        LogUtils.e("-----------onClickButton4Text");
    }
}
