package com.hqq.example.ui.recycle;

import com.hqq.core.ui.BaseListActivity;
import com.hqq.example.adapter.MainAdapter;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.recycle
 * @FileName :   EmptyListActivity
 * @Date : 2019/12/30 0030  下午 8:40
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class EmptyListActivity extends BaseListActivity<MainAdapter> {

    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {
        mBaseListModel.fillingData(new ArrayList());
    }
}
