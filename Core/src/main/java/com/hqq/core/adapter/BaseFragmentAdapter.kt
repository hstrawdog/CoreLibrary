package com.hqq.core.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.adapter
 * @FileName :   BaseFragmentAdapter
 * @Date : 2019/1/24 0024  下午 2:26
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    protected var mFragmentSparseArray = SparseArray<Fragment>()
    var stringSparseArray = SparseArray<String>()
        protected set

    override fun getCount(): Int {
        return stringSparseArray.size()
    }

    override fun getPageTitle(position: Int): String? {
        return stringSparseArray[position]
    }

    override fun getItem(position: Int): Fragment {
        var fragment = mFragmentSparseArray[position]
        if (fragment == null) {
            fragment = newFragment(position)
            mFragmentSparseArray.put(position, fragment)
        }
        return fragment
    }

    /**
     * 只会执行一次
     *
     * @param position position
     * @return
     */
    protected abstract fun newFragment(position: Int): Fragment
}