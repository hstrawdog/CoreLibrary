package com.hqq.example.ui

import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import com.hqq.example.bean.ParcelableBean

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @Date : 上午 9:20
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ParcelableActivity : BaseActivity() {
    override val layoutViewId: Int
        get() = R.layout.activity_parcelable

    override fun initView() {
        val bean = intent.getParcelableExtra<ParcelableBean>("A")
        LogUtils.e("${bean?.a}")

    }
}