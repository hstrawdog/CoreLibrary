package com.hqq.example.ui.tab.layout;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.hqq.core.adapter.BaseFragmentAdapter;
import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.tab_layout
 * @FileName :   TabLayoutActivity
 * @Date : 2019/6/6 0006  下午 6:00
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class TabLayoutActivity extends BaseActivity {
    TabLayout mTbTablayout1;
    ViewPager mVpPage;
    TabLayout mTbTablayout2;
    TabLayout mTbTablayout3;
    TabLayout mTbTablayout4;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initDefConfig() {
        super.initDefConfig();
        mRootViewBuild.setStatusColor(R.color.color_main);
    }

    @Override
    public void initView() {
        mTbTablayout4=findViewById(R.id.tb_tablayout4);
        mTbTablayout3=findViewById(R.id.tb_tablayout3);
        mTbTablayout2=findViewById(R.id.tb_tablayout2);
        mVpPage=findViewById(R.id.vp_page);
        mTbTablayout1=findViewById(R.id.tb_tablayout1);

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
