package com.hqq.coreapp.ui.activity;

import android.os.Bundle;

import com.hqq.core.recycler.BannerLayout;
import com.hqq.core.ui.BaseActivity;
import com.hqq.coreapp.R;
import com.hqq.coreapp.adapter.BannerAdapter;
import com.hqq.coreapp.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    BannerLayout mRcBanner;
    @BindView(R.id.rc_banner2)
    BannerLayout mRcBanner2;

    @Override
    public int getViewId() {
        return R.layout.activity_banner;
    }

    BannerAdapter mBannerAdapter;

    @Override
    public void initView() {
        List<BannerBean> list = new ArrayList<>();
        list.add(new BannerBean("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(new BannerBean("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(new BannerBean("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(new BannerBean("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(new BannerBean("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        mRcBanner.setRvBannerData(list);
        List<Integer> list2 = new ArrayList<>();
        list2.add(R.mipmap.ic_banner2);
        list2.add(R.mipmap.ic_banner2);
        mRcBanner2.setRvBannerData(list2);
    }


}
