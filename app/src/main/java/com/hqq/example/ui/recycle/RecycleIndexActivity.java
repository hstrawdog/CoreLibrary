package com.hqq.example.ui.recycle;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
        getAdapter().addData(new MainBean("轮播图", BannerActivity.class));
        getAdapter().addData(new MainBean("多Item 分页滑动", FullPagerSnapActivity.class));
        getAdapter().addData(new MainBean("画廊 分页滑动", GallerySnapActivity.class));
        getAdapter().addData(new MainBean("空数据测试", EmptyListActivity.class));
        getAdapter().addData(new MainBean("加载数据", LoadMoreActivity.class));
        getAdapter().addData(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
        getAdapter().addData(new MainBean("ListActivity 加载", ListActivity.class));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
