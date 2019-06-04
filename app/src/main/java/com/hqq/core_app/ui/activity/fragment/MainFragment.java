package com.hqq.core_app.ui.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.hqq.core.ui.BaseListFragment;
import com.hqq.core_app.R;
import com.hqq.core_app.adapter.MainAdapter;
import com.hqq.core_app.bean.MainBean;
import com.hqq.core_app.ui.activity.IFragmentActivity;
import com.hqq.core_app.ui.activity.bar.ToolBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity.fragment
 * @FileName :   MainFragment
 * @Date : 2018/11/23 0023  上午 9:41
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class MainFragment extends BaseListFragment<MainAdapter> {

    @Override
    public int setViewId() {
        return R.layout.fragment_i;

    }

    public static Fragment getIFragment(int position) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("postition", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 只有界面显示的时候才会加载
     *
     * @return
     */
    @Override
    public boolean isLazyLoad() {
        return true;
    }

    @Override
    public boolean isShowLoadMore() {
        return true;
    }

    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();

    }

    @Override
    public void initData() {
        initlistData();
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        getData();
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initlistData();
            }
        }, 3 * 1000);

    }

    private void initlistData() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
        list.add(new MainBean("fragment 加载", IFragmentActivity.class));
        mBaseListModel.fillingData(list);
    }
}
