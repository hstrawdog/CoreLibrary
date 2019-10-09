package com.hqq.core.app.ui.recycle;

import com.hqq.core.recycler.RecycleViewBanner;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.app.R;

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
    RecycleViewBanner mRcBanner;
    @BindView(R.id.rc_banner2)
    RecycleViewBanner mRcBanner2;

    @Override
    public int setViewId() {
        return R.layout.activity_banner;
    }


    @Override
    public void initView() {
        List<String> list = new ArrayList<>();
        list.add(("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        list.add(("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg"));
        mRcBanner.setRvBannerData(list);
        List<Integer> list2 = new ArrayList<>();
        list2.add(R.mipmap.ic_banner2);
        list2.add(R.mipmap.ic_banner2);
        mRcBanner2.setRvBannerData(list2);
    }

}
