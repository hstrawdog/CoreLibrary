package com.hqq.example.ui.tab.layout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hqq.core.adapter.BaseFragmentAdapter
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.tab_layout
 * @FileName :   TabLayoutActivity
 * @Date : 2019/6/6 0006  下午 6:00
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class TabLayoutActivity : BaseActivity() {
    var mTbTablayout1: TabLayout? = null
    var mVpPage: ViewPager? = null
    var mTbTablayout2: TabLayout? = null
    var mTbTablayout3: TabLayout? = null
    var mTbTablayout4: TabLayout? = null

    override val mLayoutViewId: Int
        get() = R.layout.activity_tab_layout
    override fun initDefConfig() {
        super.initDefConfig()
        mRootViewBuild?.setStatusColor(R.color.color_main)
    }

    override fun initView() {
        mTbTablayout4 = findViewById(R.id.tb_tablayout4)
        mTbTablayout3 = findViewById(R.id.tb_tablayout3)
        mTbTablayout2 = findViewById(R.id.tb_tablayout2)
        mVpPage = findViewById(R.id.vp_page)
        mTbTablayout1 = findViewById(R.id.tb_tablayout1)
        val adapter = ViewPageAdapter(supportFragmentManager)
        mVpPage!!.setAdapter(adapter)
        mTbTablayout1!!.setupWithViewPager(mVpPage)
        mTbTablayout2!!.setupWithViewPager(mVpPage)
        mTbTablayout3!!.setupWithViewPager(mVpPage)
        mTbTablayout4!!.setupWithViewPager(mVpPage)
    }

    internal inner class ViewPageAdapter(fm: FragmentManager?) : BaseFragmentAdapter(fm) {
        override fun newFragment(position: Int): Fragment {
            return TabFragment()
        }

        init {
            stringSparseArray.append(0, "咖啡")
            stringSparseArray.append(1, "奶茶")
            stringSparseArray.append(2, "菊花茶")
            stringSparseArray.append(3, "霸王杯")
            stringSparseArray.append(4, "冬瓜茶")
        }
    }
}