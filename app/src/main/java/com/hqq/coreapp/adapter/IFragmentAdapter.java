package com.hqq.coreapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.hqq.coreapp.ui.activity.fragment.MainFragment;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity.fragment
 * @FileName :   IFragmentAdapter
 * @Date : 2018/11/23 0023  上午 9:40
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class IFragmentAdapter extends FragmentPagerAdapter {
    public IFragmentAdapter(FragmentManager fm) {
        super(fm);
    }



    SparseArray<MainFragment> mSparseArray =new SparseArray();

    @Override
    public Fragment getItem(int position) {

        Fragment fragment  =mSparseArray .get(position);
        if (fragment == null){
            fragment= MainFragment.getIFragment(position);
        }

        return   fragment;
    }

    @Override
    public int getCount() {
        return  5;
    }
}
