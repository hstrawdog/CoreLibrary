package com.hqq.example.ui.animate;

import android.content.Context;
import android.content.Intent;

import com.hqq.core.ui.base.BaseListActivity;
import com.hqq.example.adapter.MainAdapter;
import com.hqq.example.bean.MainBean;

public class AnimateIndexActivity extends BaseListActivity<MainAdapter> {
    public static void open(Context context) {
        Intent starter = new Intent(context, AnimateIndexActivity.class);
        context.startActivity(starter);
    }

    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {
        mAdapter.addData(new MainBean("隐藏动画", ViewAnimateActivity.class));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
