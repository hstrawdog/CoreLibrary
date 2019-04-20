package com.hqq.coreapp.ui.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.hqq.coreapp.R;
import com.hqq.coreapp.adapter.MainAdapter;
import com.hqq.coreapp.bean.MainBean;
import com.hqq.coreapp.ui.activity.IFragmentActivity;
import com.hqq.coreapp.ui.activity.ToolBarActivity;
import com.hqq.core.ui.BaseRcFragment;
import com.hqq.core.widget.CusPtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity.fragment
 * @FileName :   IFragment
 * @Date : 2018/11/23 0023  上午 9:41
 * @Descrive :
 * @Email :
 */
public class IFragment extends BaseRcFragment<MainAdapter> {

    @Override
    public int getViewId() {
        return R.layout.fragment_i;

    }

    public static Fragment getIFragment(int position) {
        IFragment fragment = new IFragment();
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
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected boolean isShowLoadMore() {
        return true;
    }

    @Override
    protected MainAdapter getRcAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        mPtrPullDown = (CusPtrClassicFrameLayout) findViewById(R.id.ptr_pull_down);
        mRcList = (RecyclerView) findViewById(R.id.rc_list);
        super.initBasic(savedInstanceState);
        initPull();

    }

    @Override
    protected void initData() {
        getData();
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
                fillingData(list);
            }
        }, 3 * 1000);

    }
}
