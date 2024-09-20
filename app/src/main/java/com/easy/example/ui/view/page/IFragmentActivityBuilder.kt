package com.easy.example.ui.view.page

import androidx.viewpager2.widget.ViewPager2
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R
import com.easy.example.adapter.IFragmentAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity
 * @FileName :   IFragmentActivityBuilder
 * @Date : 2018/11/23 0023
 * @Describe : TODO
 * @Email :  qiqiang213@gmail.com
 */
class IFragmentActivityBuilder : BaseActivity() {
    lateinit var mVpPage: ViewPager2
    override fun getLayoutViewId(): Int {
        return R.layout.activity_ifragment

    }

    override fun initView() {
        mVpPage = findViewById(R.id.vp_page)
        mVpPage.setAdapter(IFragmentAdapter(this))
    }
}