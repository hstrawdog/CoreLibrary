package com.hqq.example.demo.news;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.ui.vm.BaseVmListActivity;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityNewsBinding;
import com.hqq.example.ui.web.WebActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.news
 * @FileName :   NewsActivity
 * @Date : 2020/8/5 0005  上午 10:34
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class NewsActivity extends BaseVmListActivity<ActivityNewsBinding, NewsViewModel, NewsAdapter> {

    public static void open(Activity context) {
        Intent starter = new Intent(context, NewsActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_news;
    }


    @Override
    public int getBindingViewModelId() {
        return 0;
    }

    @Override
    public NewsAdapter initAdapter() {
        return new NewsAdapter();
    }


    @Override
    public void initData() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebActivity.open(mActivity, mAdapter.getItem(position).getUrl(), mAdapter.getItem(position).getTitle());

            }
        });
    }


}