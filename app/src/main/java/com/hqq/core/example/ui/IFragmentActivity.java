package com.hqq.core.example.ui;

import androidx.viewpager.widget.ViewPager;

import com.hqq.core.example.R;
import com.hqq.core.example.adapter.IFragmentAdapter;
import com.hqq.core.ui.BaseActivity;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   IFragmentActivity
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class IFragmentActivity extends BaseActivity {

    @BindView(R.id.vp_page)
    ViewPager mVpPage;

    @Override
    public int setViewId() {
        return R.layout.activity_ifragment;
    }

    @Override
    public void initView() {
        mVpPage.setAdapter(new IFragmentAdapter(getSupportFragmentManager()));
    }


}
