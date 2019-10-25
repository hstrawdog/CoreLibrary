package com.hqq.example.app.dialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hqq.example.ui.BaseBottomDialog;
import com.hqq.example.utils.ResourcesUtils;
import com.hqq.example.app.R;
import com.hqq.example.app.adapter.StringAdapter;

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

    @Override
    protected int getTransparentBottomSheetStyle() {
        return R.style.TransparentBottomSheetStyle_behavior;
    }
}