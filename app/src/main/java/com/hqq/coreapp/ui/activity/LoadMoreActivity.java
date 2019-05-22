package com.hqq.coreapp.ui.activity;

import com.hqq.core.ui.BaseListActivity;
import com.hqq.coreapp.adapter.MainAdapter;
import com.hqq.coreapp.bean.MainBean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   LoadMoreActivity
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :
 */
public class LoadMoreActivity extends BaseListActivity<MainAdapter> {


    @Override
    public MainAdapter getAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {
        mBaseListModel.fillingData(getData());
    }

    @Override
    public boolean isShowLoadMore() {
        return true;
    }

    @Override
    protected void onLoadMore() {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseListModel.fillingData(getData());
                mAdapter.loadMoreComplete();
            }
        }, 2000);
    }

    public List<MainBean> getData() {
        List<MainBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MainBean("标题 " + ((int) (1 + Math.random() * 10)), null));
        }
        return list;
    }

}
