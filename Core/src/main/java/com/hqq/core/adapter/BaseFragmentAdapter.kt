package com.hqq.core.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
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
 * @Describe :  ViewPage2 的 Adapter 简单封装
 *  注意 设置缓存大小 否则会内存泄漏
 *  setupWithViewPager 是与 TabLayout 一起使用
 * 采用了从Manager中获取Fragment  与设置的缓存大小有关系
 * 这样也避免了用集合缓存Fragment对象 当Fragment被回收后 Fragment内存泄漏
 *
 */
abstract class BaseFragmentAdapter : FragmentStateAdapter {
    public var stringSparseArray = SparseArray<String>()
    var fragmentManager: FragmentManager? = null

    constructor(fragment: Fragment) : super(fragment) {
        fragmentManager = fragment.childFragmentManager
    }

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity) {
        fragmentManager = fragmentActivity.supportFragmentManager
    }

    constructor(fm: FragmentManager, lifecycle: Lifecycle) : super(fm, lifecycle) {
        fragmentManager = fm
    }

    /**
     *  item 的数量
     */
    override fun getItemCount(): Int {
        return stringSparseArray.size()
    }

    /**
     *  创建Fragment
     */
    override fun createFragment(position: Int): Fragment {
        var fragment = fragmentManager?.findFragmentByTag("f" + position)
        if (fragment == null) {
            fragment = newFragment(position)
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
     *  获取标题
     */
    open fun getItemTitle(position: Int): String {
        return stringSparseArray.get(position)
    }

    /**
     * 只会执行一次
     *
     * @param position position
     * @return
     */
    protected abstract fun newFragment(position: Int): Fragment


}


