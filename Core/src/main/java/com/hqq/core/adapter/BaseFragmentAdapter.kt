package com.hqq.core.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.adapter
 * @FileName :   BaseFragmentAdapter
 * @Date : 2019/1/24 0024  下午 2:26
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseFragmentAdapter : FragmentStateAdapter {
    var mFragmentSparseArray = SparseArray<Fragment>()
    var stringSparseArray = SparseArray<String>()


    constructor(fm: FragmentManager, lifecycle: Lifecycle) : super(fm, lifecycle)

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    constructor(fragment: Fragment) : super(fragment)

    override fun getItemCount(): Int {
        return stringSparseArray.size()

    }

    /**
     *  创建Fragment
     */
    override fun createFragment(position: Int): Fragment {
        var fragment = mFragmentSparseArray[position]
        if (fragment == null) {
            fragment = newFragment(position)
            mFragmentSparseArray.put(position, fragment)
        }
        return fragment
    }

    /**
     *  绑定TabLayout方法
     */
    fun setupWithViewPager(tabLayout: TabLayout, viePage: ViewPager2) {
        TabLayoutMediator(tabLayout, viePage, object : TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = getItemTitle(position)
            }
        }).attach()
    }

    /**
     * 只会执行一次
     *
     * @param position position
     * @return
     */
    protected abstract fun newFragment(position: Int): Fragment

    fun getItemTitle(position: Int): String {
        return stringSparseArray.get(position)
    }


}