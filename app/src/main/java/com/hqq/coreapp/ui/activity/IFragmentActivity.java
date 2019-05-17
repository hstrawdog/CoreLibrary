package com.hqq.coreapp.ui.activity;

import android.support.v4.view.ViewPager;

import com.hqq.coreapp.R;
import com.hqq.coreapp.adapter.IFragmentAdapter;
import com.hqq.core.ui.BaseActivity;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   IFragmentActivity
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :
 */
public class IFragmentActivity extends BaseActivity {

    @BindView(R.id.vp_page)
    ViewPager mVpPage;

    @Override
    public int getViewId() {
        return R.layout.activity_ifragment;
    }

    @Override
    public void initView() {
        mVpPage.setAdapter(new IFragmentAdapter(getSupportFragmentManager()));
    }
//    @Override
//    public BaseToolBar initIToolBar(ViewGroup layout) {
//        return new IToolBarBuild(ISearchToolBarImpl.class,this, layout, true, true)
//                .create();
//    }

}
