package com.hqq.example.ui.recycle;

import com.hqq.core.recycle.RecycleViewBanner;
import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   BannerActivity
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class BannerActivity extends BaseActivity {

    RecycleViewBanner mRcBanner;
    RecycleViewBanner mRcBanner2;

    @Override
    public int getMLayoutViewId() {
        return R.layout.activity_banner;
    }


    @Override
    public void initView() {
        mRcBanner=findViewById(R.id.rc_banner);
        mRcBanner2=findViewById(R.id.rc_banner2);


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
