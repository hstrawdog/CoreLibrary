package com.hqq.example.ui.view.page

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R
import com.hqq.example.adapter.IFragmentAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   IFragmentActivityBuilder
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class IFragmentActivityBuilder : BaseActivity() {
    lateinit var mVpPage: ViewPager2
    override val layoutViewId: Int
        get() = R.layout.activity_ifragment

    override fun initView() {
        mVpPage = findViewById(R.id.vp_page)
        mVpPage.setAdapter(IFragmentAdapter(this))
        mVpPage.offscreenPageLimit = mVpPage.adapter?.itemCount!!
    }
}