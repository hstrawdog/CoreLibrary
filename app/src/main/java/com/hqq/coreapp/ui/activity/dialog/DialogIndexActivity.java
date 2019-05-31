package com.hqq.coreapp.ui.activity.dialog;

import com.hqq.core.ui.BaseListActivity;
import com.hqq.coreapp.adapter.MainAdapter;
import com.hqq.coreapp.bean.MainBean;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity.dialog
 * @FileName :   DialogIndexActivity
 * @Date : 2019/5/25 0025  下午 2:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class DialogIndexActivity extends BaseListActivity<MainAdapter> {
    @Override
    public MainAdapter initAdapter() {
        return  new MainAdapter();
    }

    @Override
    public void initData() {
        mAdapter.addData(new MainBean("仿知乎评论列表", BottomSheetActivity.class));
        mAdapter.addData(new MainBean("dialog样式测试", TestDialogActivity.class));
    }


}
