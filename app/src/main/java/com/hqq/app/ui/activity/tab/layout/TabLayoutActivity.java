package com.hqq.app.ui.activity.tab.layout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.hqq.core.adapter.BaseFragmentAdapter;
import com.hqq.core.ui.BaseActivity;
import com.hqq.app.R;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.tab_layout
 * @FileName :   TabLayoutActivity
 * @Date : 2019/6/6 0006  下午 6:00
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class TabLayoutActivity extends BaseActivity {
    @BindView(R.id.tb_tablayout1)
    TabLayout mTbTablayout1;
    @BindView(R.id.vp_page)
    ViewPager mVpPage;
    @BindView(R.id.tb_tablayout2)
    TabLayout mTbTablayout2;
    @BindView(R.id.tb_tablayout3)
    TabLayout mTbTablayout3;
    @BindView(R.id.tb_tablayout4)
    TabLayout mTbTablayout4;

    @Override
    public int setViewId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initView() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        mVpPage.setAdapter(adapter);
        mTbTablayout1.setupWithViewPager(mVpPage);
        mTbTablayout2.setupWithViewPager(mVpPage);
        mTbTablayout3.setupWithViewPager(mVpPage);
        mTbTablayout4.setupWithViewPager(mVpPage);
    }


    class ViewPageAdapter extends BaseFragmentAdapter {

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
            mStringSparseArray.append(0, "咖啡");
            mStringSparseArray.append(1, "奶茶");
            mStringSparseArray.append(2, "菊花茶");
            mStringSparseArray.append(3, "霸王杯");
            mStringSparseArray.append(4, "冬瓜茶");
        }

        @Override
        protected Fragment newFragment(int position) {
            return new TabFragment();
        }

    }
}
