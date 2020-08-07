package com.hqq.example.demo.joke;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.ui.vm.BaseVmListActivity;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityJokeBinding;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.joke
 * @FileName :   JokeActivity
 * @Date : 2020/8/5 0005  下午 2:18
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class JokeActivity extends BaseVmListActivity<ActivityJokeBinding, JokeViewModel, JokeAdapter> {

    public static void open(Activity context) {
        Intent starter = new Intent(context, JokeActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getBindingViewModelId() {
        return R.layout.activity_joke;
    }

    @Override
    public JokeAdapter initAdapter() {
        return new JokeAdapter();
    }

    @Override
    public void initData() {
        mAdapter.setOnLoadMoreListener(this, mRcList);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}