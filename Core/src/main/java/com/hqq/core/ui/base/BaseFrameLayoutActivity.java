package com.hqq.core.ui.base;

import androidx.fragment.app.Fragment;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFrameLayoutActivity
 * @Date : 2018/9/18 0018  下午 2:08
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public abstract class BaseFrameLayoutActivity extends BaseActivity {
    public Fragment mCurrentFragment;

    /**
     * 添加或者显示 fragment
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    public void addOrShowFragment(Fragment fragment, int id) {
        if (mCurrentFragment == fragment) {
            return;
        }
        // 如果当前fragment未被添加，则添加到Fragment管理器中
        if (!fragment.isAdded()) {
            if (mCurrentFragment == null) {
                getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).add(id, fragment).commit();
            }
        } else {
            if (mCurrentFragment == null) {
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).show(fragment).commit();
            }
        }
        mCurrentFragment = fragment;
    }


    /**
     * 添加 fragment 到 FrameLayout
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    @Deprecated
    public void addFragment(Fragment fragment, int id) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
            mCurrentFragment = fragment;
        }

    }
}
