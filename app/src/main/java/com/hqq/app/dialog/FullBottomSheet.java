package com.hqq.app.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hqq.core.ui.BaseBottomDialog;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.app.R;
import com.hqq.app.adapter.StringAdapter;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.dialog
 * @FileName :   FullBottomSheet
 * @Date : 2019/4/29 0029  下午 2:06
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FullBottomSheet extends BaseBottomDialog {
    StringAdapter mStringAdapter = new StringAdapter();

    @Override
    public int setViewId() {
        return R.layout.dialog_bottom_sheet;

    }

    @Override
    public void initView() {
        RecyclerView recyclerView = mRootView.findViewById(R.id.rc_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < 20; i++) {
            mStringAdapter.addData("    " + i);
        }
        recyclerView.setAdapter(mStringAdapter);
    }

    @Override
    public int setHeight() {
        return (int) ResourcesUtils.getDimen(getContext(), R.dimen.x750);
    }

}