package com.hqq.example.ui.list;

import android.os.Handler;
import android.view.View;

import com.hqq.core.recycler.HeaderItemDecoration;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;
import com.hqq.example.adapter.MainAdapter;
import com.hqq.example.bean.MainBean;
import com.hqq.core.ui.BaseListActivity;
import com.hqq.example.ui.view.page.IFragmentActivityBuilder;
import com.hqq.example.ui.bar.ToolBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   ListActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class ListActivity extends BaseListActivity<MainAdapter> {
    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {
        mLoadingView.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<MainBean> list = new ArrayList<>();
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                list.add(new MainBean("ItoolBar 控制 ", ToolBarActivity.class));
                list.add(new MainBean("fragment 加载", IFragmentActivityBuilder.class));
                mLoadingView.dismiss();
                mBaseListModel.fillingData(list);

            }
        }, 3 * 1000);


        mRcList.addItemDecoration(new HeaderItemDecoration(mRcList, new HeaderItemDecoration.StickyHeaderInterface() {
            @Override
            public int getHeaderPositionForItem(int itemPosition) {
                LogUtils.e("getHeaderPositionForItem----------------  " + itemPosition);
                int headerPosition = 0;
                do {
                    if (this.isHeader(itemPosition)) {
                        headerPosition = itemPosition;
                        break;
                    }
                    itemPosition -= 1;
                } while (itemPosition >= 0);
                return headerPosition;
            }

            @Override
            public int getHeaderLayout(int headerPosition) {
                return R.layout.item_main;
            }

            @Override
            public void bindHeaderData(View header, int headerPosition) {

            }

            @Override
            public boolean isHeader(int itemPosition) {
                return itemPosition == 6;
            }
        }));
    }

}
