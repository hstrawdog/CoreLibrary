package com.hqq.coreapp.ui.activity;

import com.hqq.coreapp.R;
import com.hqq.coreapp.adapter.BannerAdapter;
import com.hqq.core.recycler.BannerLayout;
import com.hqq.core.ui.BaseActivity;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   BannerActivity
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class BannerActivity extends BaseActivity {

    @BindView(R.id.bl_recycler)
    BannerLayout mBlRecycler;

    @Override
    public int getViewId() {
        return R.layout.activity_banner;
    }

    BannerAdapter mBannerAdapter;

    @Override
    protected void initView() {
        mBannerAdapter = new BannerAdapter();
        mBannerAdapter.addData("");
        mBannerAdapter.addData("");
        mBannerAdapter.addData("");
        mBannerAdapter.addData("");
        mBannerAdapter.addData("");
        mBannerAdapter.addData("");
        mBlRecycler.setAdapter(mBannerAdapter);
    }
}
