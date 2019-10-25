package com.hqq.core.example.ui.recycle;

import com.hqq.core.example.adapter.MainAdapter;
import com.hqq.core.example.bean.MainBean;
import com.hqq.core.ui.BaseListActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.recycle
 * @FileName :   RecycleIndexActivity
 * @Date : 2019/6/18 0018  下午 2:16
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class RecycleIndexActivity extends BaseListActivity<MainAdapter> {


    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {
        mAdapter.addData(new MainBean("轮播图", BannerActivity.class));
        mAdapter.addData(new MainBean("多Item 分页滑动", FullPagerSnapActivity.class));
        mAdapter.addData(new MainBean("画廊 分页滑动", GallerySnapActivity.class));

    }
}
