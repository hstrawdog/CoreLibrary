package com.hqq.coreapp.ui.activity;

import com.hqq.core.ui.BaseActivity;
import com.hqq.coreapp.R;
import com.hqq.coreapp.dialog.FoldFullBottomSheet;
import com.hqq.coreapp.dialog.FullBottomSheet;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   BottomSheetActivity
 * @Date : 2019/4/29 0029
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class BottomSheetActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    public void initView() {
    }

    @OnClick(R.id.button9)
    public void onViewClicked() {
        FullBottomSheet mFullBottomSheetFragment = new FullBottomSheet();
        mFullBottomSheetFragment.show(getSupportFragmentManager(), "123");
    }

    @OnClick(R.id.button10)
    public void onViewClicked1() {
        FoldFullBottomSheet mFullBottomSheetFragment = new FoldFullBottomSheet();
        mFullBottomSheetFragment.show(getSupportFragmentManager(), "123");
    }

}