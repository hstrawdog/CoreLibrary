package com.hqq.example.ui.dialog;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.hqq.example.R;
import com.hqq.example.dialog.BottomDialog;
import com.hqq.example.dialog.FoldFullBottomSheet;
import com.hqq.example.dialog.FullBottomSheet;
import com.hqq.example.dialog.FullDialog;
import com.hqq.example.dialog.LeftDialog;
import com.hqq.example.dialog.RightDialog;
import com.hqq.example.dialog.SelectADialog;
import com.hqq.example.dialog.SelectDialog;
import com.hqq.core.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   TestDialogActivity
 * @Date : 2019/5/24 0024
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class TestDialogActivity extends BaseActivity {


    @BindView(R.id.button14)
    Button mButton14;
    @BindView(R.id.button15)
    Button mButton15;

    public static void open(Activity context) {
        Intent starter = new Intent(context, TestDialogActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_test_dialog;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.button9, R.id.button14, R.id.button15, R.id.button16, R.id.button10, R.id.button17, R.id.button18, R.id.button28
    })
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button9:
                FullBottomSheet mFullBottomSheetFragment = new FullBottomSheet();
                mFullBottomSheetFragment.show(getSupportFragmentManager());
                break;
            case R.id.button10:
                FoldFullBottomSheet mFullBottomSheetFragments = new FoldFullBottomSheet();
                mFullBottomSheetFragments.show(getSupportFragmentManager());
                break;
            case R.id.button18:
                SelectDialog.showDialog(getSupportFragmentManager());
                break;
            case R.id.button14:
                BottomDialog.showDialog(getSupportFragmentManager());
                break;
            case R.id.button15:
                FullDialog.showDialog(getSupportFragmentManager());
                break;
            case R.id.button16:
//                NewRightDialog.showDialog(getSupportFragmentManager());
                RightDialog.showDialog(getSupportFragmentManager());
                break;
            case R.id.button17:
                LeftDialog.showDialog(getSupportFragmentManager());
                break;
            case R.id.button28:
                SelectADialog.showDialog(getSupportFragmentManager());
                break;
            default:
        }
    }


}
