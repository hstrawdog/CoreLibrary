package com.hqq.core.app.ui.activity.recycle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hqq.core.adapter.BaseRcViewHolder;
import com.hqq.core.app.R;
import com.hqq.core.app.adapter.MainAdapter;
import com.hqq.core.app.bean.MainBean;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.ui.BaseListActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity.recycle
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

    }
}
