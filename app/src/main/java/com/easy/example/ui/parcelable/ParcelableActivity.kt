package com.easy.example.ui.parcelable

import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import com.easy.example.bean.ParcelableBean

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 上午 9:20
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ParcelableActivity : BaseActivity() {

    override fun getLayoutViewId(): Int {
        return  R.layout.activity_parcelable
    }

    override fun initView() {
        val bean = intent.getParcelableExtra<ParcelableBean>("A")
        LogUtils.e("${bean?.a}")

    }
}