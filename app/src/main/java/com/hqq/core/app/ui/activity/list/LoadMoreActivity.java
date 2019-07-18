package com.hqq.core.app.ui.activity.list;

import com.hqq.core.ui.BaseListActivity;
import com.hqq.core.app.adapter.MainAdapter;
import com.hqq.core.app.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   LoadMoreActivity
 * @Date : 2018/11/23 0023
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class LoadMoreActivity extends BaseListActivity<MainAdapter> {


    @Override
    public MainAdapter initAdapter() {
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
                if (mBaseListModel != null) {
                    mBaseListModel.fillingData(getData());
                    mAdapter.loadMoreComplete();
                }
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
