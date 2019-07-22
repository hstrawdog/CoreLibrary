package com.hqq.core.app.ui.customize;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.ui.BaseListActivity;
import com.hqq.core.app.adapter.MainAdapter;
import com.hqq.core.app.bean.MainBean;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.customize
 * @FileName :   CustomizeIndexActivity
 * @Date : 2019/6/25 0025  下午 8:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */

public class CustomizeIndexActivity extends BaseListActivity<MainAdapter> {


    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {

        mAdapter.addData(new MainBean("贝塞尔曲线鉴定绘制", BezierActivity.class));
        mAdapter.addData(new MainBean("进度条/星星", ProgressBarViewActivity.class));
        mAdapter.addData(new MainBean("高逼格的鱼", FishActivity.class));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(new Intent(this, mAdapter.getItem(position).getClassName()));
    }
}
