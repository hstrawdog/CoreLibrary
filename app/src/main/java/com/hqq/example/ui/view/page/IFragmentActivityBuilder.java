package com.hqq.example.ui.view.page;

import androidx.viewpager.widget.ViewPager;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;
import com.hqq.example.adapter.IFragmentAdapter;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   IFragmentActivityBuilder
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class IFragmentActivityBuilder extends BaseActivity {

    ViewPager mVpPage;

    @Override
    public int getMLayoutViewId() {
        return R.layout.activity_ifragment;
    }

    @Override
    public void initView() {
        mVpPage = findViewById(R.id.vp_page);
        mVpPage.setAdapter(new IFragmentAdapter(getSupportFragmentManager()));
    }







}
