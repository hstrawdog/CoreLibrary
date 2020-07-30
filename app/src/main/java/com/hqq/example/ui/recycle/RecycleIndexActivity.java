package com.hqq.example.ui.recycle;

import com.hqq.example.adapter.MainAdapter;
import com.hqq.example.bean.MainBean;
import com.hqq.core.ui.base.BaseListActivity;
import com.hqq.example.ui.list.ListActivity;
import com.hqq.example.ui.list.LoadMoreActivity;
import com.hqq.example.ui.view.page.IFragmentActivityBuilder;

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
        mAdapter.addData(new MainBean("空数据测试", EmptyListActivity.class));
        mAdapter.addData(new MainBean("加载数据", LoadMoreActivity.class));
        mAdapter.addData(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
        mAdapter.addData(new MainBean("ListActivity 加载", ListActivity.class));
    }
}
