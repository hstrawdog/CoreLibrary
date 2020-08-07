package com.hqq.core.ui.base

import androidx.fragment.app.Fragment
import com.hqq.core.ui.base.BaseActivity

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFrameLayoutActivity
 * @Date : 2018/9/18 0018  下午 2:08
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
abstract class BaseFrameLayoutActivity : BaseActivity() {
    @JvmField
    var mCurrentFragment: Fragment? = null

    /**
     * 添加或者显示 fragment
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    fun addOrShowFragment(fragment: Fragment, id: Int) {
        if (mCurrentFragment === fragment) {
            return
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded) {
            if (mCurrentFragment == null) {
                supportFragmentManager.beginTransaction().add(id, fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(mCurrentFragment!!).add(id, fragment).commit()
            }
        } else {
            if (mCurrentFragment == null) {
                supportFragmentManager.beginTransaction().show(fragment).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(mCurrentFragment!!).show(fragment).commit()
            }
        }
        mCurrentFragment = fragment
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
            mCurrentFragment = fragment
        }
    }
}