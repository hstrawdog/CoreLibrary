package com.hqq.core.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.adapter
 * @FileName :   BaseFragmentAdapter
 * @Date : 2019/1/24 0024  下午 2:26
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseFragmentAdapter extends FragmentPagerAdapter {
    protected SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();
    protected SparseArray<String> mStringSparseArray = new SparseArray<>();

    public SparseArray<String> getStringSparseArray() {
        return mStringSparseArray;
    }

    public BaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mStringSparseArray.size();
    }

    @Override
    public String getPageTitle(int position) {
        return mStringSparseArray.get(position);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentSparseArray.get(position);
        if (fragment == null) {
            fragment = newFragment(position);
            mFragmentSparseArray.put(position, fragment);
        }
        return fragment;
    }


    /**
     * 只会执行一次
     *
     * @param position position
     * @return
     */
    protected abstract Fragment newFragment(int position);

}
