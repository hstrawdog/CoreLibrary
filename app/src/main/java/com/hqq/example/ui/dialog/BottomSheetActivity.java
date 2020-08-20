package com.hqq.example.ui.dialog;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;
import com.hqq.example.dialog.FoldFullBottomSheet;
import com.hqq.example.dialog.FullBottomSheet;


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
    public int getLayoutViewId() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    public void initView() {
    }

    public void onViewClicked() {
        FullBottomSheet mFullBottomSheetFragment = new FullBottomSheet();
        mFullBottomSheetFragment.show(getSupportFragmentManager());
    }

    public void onViewClicked1() {
        FoldFullBottomSheet mFullBottomSheetFragment = new FoldFullBottomSheet();
        mFullBottomSheetFragment.show(getSupportFragmentManager());
    }

}
