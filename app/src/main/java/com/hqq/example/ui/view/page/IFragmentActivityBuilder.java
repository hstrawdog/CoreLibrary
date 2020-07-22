package com.hqq.example.ui.view.page;

import androidx.viewpager.widget.ViewPager;

import com.hqq.example.R;
import com.hqq.example.adapter.IFragmentAdapter;
import com.hqq.core.ui.BaseActivity;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   IFragmentActivityBuilder
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class IFragmentActivityBuilder extends BaseActivity {

    @BindView(R.id.vp_page)
    ViewPager mVpPage;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_ifragment;
    }

    @Override
    public void initView() {
        mVpPage.setAdapter(new IFragmentAdapter(getSupportFragmentManager()));
    }


}
