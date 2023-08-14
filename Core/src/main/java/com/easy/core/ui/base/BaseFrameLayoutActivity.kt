package com.easy.core.ui.base

import androidx.fragment.app.Fragment

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFrameLayoutActivity
 * @Date : 2018/9/18 0018  下午 2:08
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseFrameLayoutActivity : BaseActivity() {
    var currentFragment: Fragment? = null
    /**
     * 添加或者显示 fragment
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    fun addOrShowFragment(fragment: Fragment, id: Int) {
        if (currentFragment === fragment) {
            return
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded) {
            if (currentFragment == null) {
                supportFragmentManager.beginTransaction().add(id, fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment!!).add(id, fragment).commit()
            }
        } else {
            if (currentFragment == null) {
                supportFragmentManager.beginTransaction().show(fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(currentFragment!!).show(fragment).commit()
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
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().add(id, fragment).commit()
            currentFragment = fragment
        }
    }
}