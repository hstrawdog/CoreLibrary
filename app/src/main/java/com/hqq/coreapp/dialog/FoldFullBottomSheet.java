package com.hqq.coreapp.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hqq.core.ui.BaseBottomDialog;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.coreapp.R;
import com.hqq.coreapp.adapter.StringAdapter;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.dialog
 * @FileName :   FullBottomSheet
 * @Date : 2019/4/29 0029  下午 2:06
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FoldFullBottomSheet extends BaseBottomDialog {
    StringAdapter mStringAdapter = new StringAdapter();

    @Override
    public int setView() {
        return R.layout.dialog_bottom_sheet;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = mRootView.findViewById(R.id.rc_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < 20; i++) {
            mStringAdapter.addData("    " + i);
        }
        recyclerView.setAdapter(mStringAdapter);
    }

    @Override
    protected int getHeight() {
        return (int) ResourcesUtils.getDimen(getContext(), R.dimen.x750);
    }

    @Override
    protected int getTransparentBottomSheetStyle() {
        return R.style.TransparentBottomSheetStyle_behavior;
    }
}