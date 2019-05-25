package com.hqq.coreapp.ui.activity.customize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.adapter.BaseRcViewHolder;
import com.hqq.core.ui.BaseListActivity;
import com.hqq.coreapp.adapter.MainAdapter;
import com.hqq.coreapp.bean.MainBean;

public class CustomizeIndexActivity extends BaseListActivity<MainAdapter> {


    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {

        mAdapter.addData(new MainBean("贝塞尔曲线鉴定绘制", BezierActivity.class));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(new Intent(this, mAdapter.getItem(position).getClassName()));
    }
}
