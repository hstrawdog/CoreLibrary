package com.easy.core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.easy.core.common.TAG
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseFragment
import com.easy.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Date : 下午 2:11
 * @Email : qiqiang213@gmail.com
 * @Describe : Fragment 管理工具类（支持 add / show / detach / attach / pop）
 */

//
//| 功能               | 方法名                       | 说明                                 |
//| ---------------- | ------------------------- | ---------------------------------- |
//| 智能显示或添加          | `showOrAddFragment()`     | 自动判断是否需要 `add` / `show` / `attach` |
//| 替换当前 Fragment    | `replaceOrShowFragment()` | 销毁旧的 Fragment，添加新的                 |
//| 覆盖叠加（可返回）        | `coverFragment()`         | 使用系统 BackStack                     |
//| 暂时移除但可重用         | `detachFragment()`        | 从 UI 移除但保留实例                       |
//| 重新显示已 detach     | `attachFragment()`        | 恢复 UI 显示                           |
//| 完全移除 Fragment    | `removeFragment()`        | 销毁 Fragment                        |
//| 返回上一个 Fragment   | `popFragment()`           | 使用自定义栈回退                           |
//| 更新当前 Fragment 引用 | `updateCurrentFragment()` | 自动同步当前可见 Fragment                  |
//

class FragmentUtils(any: Any) {

    private var supportFragmentManager: FragmentManager? = null
    private val fragmentBackStack = ArrayDeque<Fragment>() // 自定义 Fragment 栈
    var currentFragment: Fragment? = null
        private set

    init {
        when (any) {
            is FragmentManager -> supportFragmentManager = any
            is BaseActivity -> supportFragmentManager = any.supportFragmentManager
            is BaseFragment -> supportFragmentManager = any.childFragmentManager
        }

        fragmentBackStack.clear()
        supportFragmentManager?.addOnBackStackChangedListener {
            updateCurrentFragment()
            LogUtils.dMark(TAG.LIVE_TAG, "Fragment 栈变化 -> 当前: $currentFragment")
        }
    }

    // ---------------------------------------------------------------------------------------------
    //  智能添加 / 显示 / 复用
    // ---------------------------------------------------------------------------------------------

    /**
     * 智能添加或显示 Fragment（支持 attach / add / show）
     */
    fun showOrAddFragment(fragment: Fragment, containerId: Int) {
        val fm = supportFragmentManager ?: return
        val transaction = fm.beginTransaction().setReorderingAllowed(true)

        val inManager = fm.fragments.contains(fragment)

        when {
            fragment.isAdded && fragment.isHidden -> transaction.show(fragment)
            fragment.isDetached -> transaction.attach(fragment)
            !inManager -> transaction.add(containerId, fragment)
            else -> transaction.show(fragment)
        }

        if (currentFragment != null && currentFragment != fragment) {
            transaction.hide(currentFragment!!)
        }

        transaction.commitAllowingStateLoss()

        if (!fragmentBackStack.contains(fragment)) {
            fragmentBackStack.add(fragment)
        }
        currentFragment = fragment

        LogUtils.dMark(TAG.LIVE_TAG, "showOrAddFragment -> ${fragment.javaClass.simpleName}")
    }

    /**
     * 替换当前 Fragment（旧的将销毁）
     */
    fun replaceOrShowFragment(fragment: Fragment, containerId: Int) {
        val fm = supportFragmentManager ?: return
        if (currentFragment === fragment) return

        val transaction = fm.beginTransaction().setReorderingAllowed(true)
        transaction.replace(containerId, fragment).commitAllowingStateLoss()

        removeFromStack(currentFragment)
        addToStack(fragment)
        currentFragment = fragment
    }

    /**
     * 以覆盖方式添加 Fragment（add + addToBackStack）
     */
    fun coverFragment(fragment: Fragment, containerId: Int) {
        val fm = supportFragmentManager ?: return
        val transaction = fm.beginTransaction().setReorderingAllowed(true)
        transaction.add(containerId, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commitAllowingStateLoss()

        addToStack(fragment)
        currentFragment = fragment
    }

    fun coverFragmentNew(fragment: Fragment, containerId: Int) {
        val fm = supportFragmentManager ?: return
        val transaction = fm.beginTransaction().setReorderingAllowed(true)

        val tag = fragment.javaClass.name

        // 1️⃣ 隐藏当前可见 Fragment
        currentFragment?.let { transaction.hide(it) }

        // 2️⃣ 查找是否已经 add
        val existingFragment = fm.findFragmentByTag(tag)

        if (existingFragment == null) {
            // 新 Fragment，添加到 container
            transaction.add(containerId, fragment, tag)
            addToStack(fragment)
            currentFragment = fragment
        } else {
            // 已存在，确保在 container 内并显示
            if (existingFragment.isDetached) transaction.attach(existingFragment)
            transaction.show(existingFragment)
            currentFragment = existingFragment
        }

        // 3️⃣ 回退栈
        transaction.addToBackStack(tag)
            .commitAllowingStateLoss()

        LogUtils.dMark(TAG.LIVE_TAG, "coverFragmentNew -> ${currentFragment?.javaClass?.simpleName}")
    }

    /**
     * 回退上一个覆盖的 Fragment（与 coverFragment 搭配使用）
     * @return 是否成功回退
     */
    fun popCoverFragment(): Boolean {
        val fm = supportFragmentManager ?: return false

        if (fragmentBackStack.size <= 1) {
            LogUtils.dMark(TAG.LIVE_TAG, "popCoverFragment -> 栈中仅剩一个 Fragment，无法回退")
            return false
        }

        val current = fragmentBackStack.removeLast()
        val previous = fragmentBackStack.lastOrNull()

        val transaction = fm.beginTransaction().setReorderingAllowed(true)

        // 👇 如果需要自定义动画，可以在这里加上
        // transaction.setCustomAnimations(
        //     R.anim.slide_in_left,  // 上一个进入动画
        //     R.anim.slide_out_right // 当前退出动画
        // )

        // 隐藏当前的、显示上一个
        previous?.let { transaction.show(it) }
        transaction.remove(current)
        transaction.commitAllowingStateLoss()

        currentFragment = previous

        // 同步系统的 back stack
        try {
            fm.popBackStack()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        LogUtils.dMark(TAG.LIVE_TAG, "popCoverFragment -> 回到 ${previous?.javaClass?.simpleName}")

        return true
    }


    // ---------------------------------------------------------------------------------------------
    //  detach / attach / remove 操作
    // ---------------------------------------------------------------------------------------------

    /**
     * 临时移除 Fragment（可下次重新 attach）
     */
    fun detachFragment(fragment: Fragment) {
        val fm = supportFragmentManager ?: return
        if (!fragment.isAdded || fragment.isDetached) return
        fm.beginTransaction().setReorderingAllowed(true)
            .detach(fragment)
            .commitAllowingStateLoss()
        LogUtils.dMark(TAG.LIVE_TAG, "detachFragment -> ${fragment.javaClass.simpleName}")

        if (fragment == currentFragment) {
            currentFragment = fragmentBackStack.lastOrNull { it != fragment }
        }
    }

    /**
     * 重新显示上次 detach 的 Fragment
     */
    fun attachFragment(fragment: Fragment, containerId: Int) {
        val fm = supportFragmentManager ?: return
        val transaction = fm.beginTransaction().setReorderingAllowed(true)

        if (fragment.isDetached) {
            transaction.attach(fragment)
        } else if (!fm.fragments.contains(fragment)) {
            transaction.add(containerId, fragment)
        } else {
            transaction.show(fragment)
        }

        transaction.commitAllowingStateLoss()
        addToStack(fragment)
        currentFragment = fragment
        LogUtils.dMark(TAG.LIVE_TAG, "attachFragment -> ${fragment.javaClass.simpleName}")
    }

    /**
     * 完全移除 Fragment（不可重用）
     */
    fun removeFragment(fragment: Fragment) {
        val fm = supportFragmentManager ?: return
        fm.beginTransaction()
            .setReorderingAllowed(true)
            .remove(fragment)
            .commitAllowingStateLoss()
        fm.executePendingTransactions()

        removeFromStack(fragment)
        if (fragment == currentFragment) {
            currentFragment = fragmentBackStack.lastOrNull()
        }

        LogUtils.dMark(TAG.LIVE_TAG, "removeFragment -> ${fragment.javaClass.simpleName}")
    }

    // ---------------------------------------------------------------------------------------------
    //  栈与回退
    // ---------------------------------------------------------------------------------------------

    /**
     * 手动回退上一个 Fragment
     */
    fun popFragment(): Boolean {
        if (fragmentBackStack.size > 1) {
            val current = fragmentBackStack.removeLast()
            val previous = fragmentBackStack.last()

            supportFragmentManager?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.hide(current)
                ?.show(previous)
                ?.commitAllowingStateLoss()

            currentFragment = previous
            LogUtils.dMark(TAG.LIVE_TAG, "popFragment -> 切换至 ${previous.javaClass.simpleName}")
            return true
        }
        return false
    }

    /**
     * 更新当前 Fragment 引用（系统回退栈变化时调用）
     */
    fun updateCurrentFragment() {
        val list = supportFragmentManager?.fragments ?: return
        currentFragment = list.lastOrNull { it.isVisible }
    }

    // ---------------------------------------------------------------------------------------------
    //  辅助方法
    // ---------------------------------------------------------------------------------------------

    private fun addToStack(fragment: Fragment?) {
        if (fragment != null && !fragmentBackStack.contains(fragment)) {
            fragmentBackStack.add(fragment)
        }
    }

    private fun removeFromStack(fragment: Fragment?) {
        fragment?.let { fragmentBackStack.remove(it) }
    }
}
