package com.hqq.core.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseFragment

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @Date : 下午 2:11
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class FragmentUtils {
    var supportFragmentManager: FragmentManager? = null

    var currentFragment: Fragment? = null

    constructor(any: Any) {
        when (any) {
            is FragmentManager -> {
                supportFragmentManager = any
            }
            is BaseActivity -> {
                supportFragmentManager = any.supportFragmentManager
            }
            is BaseFragment -> {
                supportFragmentManager = any.childFragmentManager
            }
        }

    }

    /**
     * 添加或者显示 fragment
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    fun addOrShowFragment(fragment: Fragment, id: Int) {
        if (currentFragment === fragment || supportFragmentManager == null) {
            return
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded) {
            if (currentFragment == null) {
                supportFragmentManager!!.beginTransaction().add(id, fragment).commit()
            } else {
                supportFragmentManager!!.beginTransaction().hide(currentFragment!!).add(id, fragment)
                    .commit()
            }
        } else {
            if (currentFragment == null) {
                supportFragmentManager!!.beginTransaction().show(fragment).commit()
            } else {
                supportFragmentManager!!.beginTransaction().hide(currentFragment!!).show(fragment).commit()
            }
        }
        currentFragment = fragment
    }

    /**
     * 添加 fragment 到 FrameLayout
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    @Deprecated("")
    fun addFragment(fragment: Fragment, id: Int) {
        if (!fragment.isAdded && supportFragmentManager != null) {
            supportFragmentManager!!.beginTransaction().add(id, fragment).commit()
            currentFragment = fragment
        }
    }

    /**
     *  移除 fragment
     * @param fragment Fragment
     */
    fun removeFragment(fragment: Fragment) {
        supportFragmentManager?.beginTransaction()?.remove(fragment)?.commit()
        if (fragment == currentFragment) {
            currentFragment = null;
        }
    }
}