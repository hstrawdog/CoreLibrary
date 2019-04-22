package com.hqq.coreapp.ui.activity;

import com.hqq.core.recycler.RecyclerViewBanner;
import com.hqq.core.ui.BaseActivity;
import com.hqq.coreapp.R;
import com.hqq.coreapp.adapter.BannerAdapter;
import com.hqq.coreapp.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.rc_banner)
    RecyclerViewBanner mRcBanner;

    @Override
    public int getViewId() {
        return R.layout.activity_banner;
    }

    BannerAdapter mBannerAdapter;

    @Override
    protected void initView() {

        List<BannerBean> list = new ArrayList<>();
        list.add(new BannerBean(R.mipmap.ic_banner2));
        list.add(new BannerBean(R.mipmap.ic_banner2));
        list.add(new BannerBean(R.mipmap.ic_banner2));
        list.add(new BannerBean(R.mipmap.ic_banner2));
        list.add(new BannerBean(R.mipmap.ic_banner2));
        mRcBanner.setRvBannerData(list);
    }

}
