package com.hqq.coreapp.ui;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.coreapp.adapter.MainAdapter;
import com.hqq.coreapp.bean.MainBean;
import com.hqq.coreapp.ui.activity.BannerActivity;
import com.hqq.coreapp.ui.activity.DefImgActivity;
import com.hqq.coreapp.ui.activity.IFragmentActivity;
import com.hqq.coreapp.ui.activity.LoadMoreActivity;
import com.hqq.coreapp.ui.activity.RcActivity;
import com.hqq.coreapp.ui.activity.SearchBarActivity;
import com.hqq.coreapp.ui.activity.ToolBarActivity;
import com.hqq.core.ui.BaseRcActivity;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :
 */
public class MainActivity extends BaseRcActivity<MainAdapter> {


    @Override
    protected MainAdapter getRcAdapter() {
        return new MainAdapter();
    }

    @Override
    protected void initData() {
        mAdapter.addData(new MainBean("加载数据", LoadMoreActivity.class));
        mAdapter.addData(new MainBean("默认标题栏", ToolBarActivity.class));
        mAdapter.addData(new MainBean("搜索/自定义标题栏", SearchBarActivity.class));
        mAdapter.addData(new MainBean("fragment 加载", IFragmentActivity.class));
        mAdapter.addData(new MainBean("RcActivity 加载", RcActivity.class));
        mAdapter.addData(new MainBean("默认图显示", DefImgActivity.class));
        mAdapter.addData(new MainBean("banner", BannerActivity.class));


        LogUtils.e("  ");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        goActivity(mAdapter.getItem(position).getClassName());
    }
}
