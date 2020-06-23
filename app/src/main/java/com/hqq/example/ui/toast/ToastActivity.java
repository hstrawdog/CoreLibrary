package com.hqq.example.ui.toast;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.toast
 * @FileName :   ToastActivity
 * @Date : 2020/6/23 0023  下午 1:57
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class ToastActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, ToastActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getViewId() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView() {

        findViewById(R.id.button35).setOnClickListener(this::onClickButton);

    }

    public void onClickButton(View v) {
        Toast toast = new Toast(mActivity);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_toast, null);
        toast.setView(view);
        toast.show();

    }

}