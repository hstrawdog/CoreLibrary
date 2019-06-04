package com.hqq.core_app.ui.activity;

import android.support.v4.view.ViewPager;

import com.hqq.core_app.R;
import com.hqq.core_app.adapter.IFragmentAdapter;
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
//    @Override
//    public BaseToolBar initIToolBar(ViewGroup layout) {
//        return new IToolBarBuilder(ISearchToolBarImpl.class,this, layout, true, true)
//                .create();
//    }

}
