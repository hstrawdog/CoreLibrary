package com.hqq.coreapp.ui.activity.dialog;

import android.app.Activity;
import android.content.Intent;

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

    public static void open(Activity context) {
        Intent starter = new Intent(context, BottomSheetActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int setViewId() {
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
