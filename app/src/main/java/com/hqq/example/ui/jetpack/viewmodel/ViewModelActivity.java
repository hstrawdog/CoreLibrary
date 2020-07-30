package com.hqq.example.ui.jetpack.viewmodel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;
import com.hqq.example.ui.jetpack.livedata.User;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.viewmodel
 * @FileName :   ViewModelActivity
 * @Date : 2020/7/21 0021  上午 9:52
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * 正常 ViewModel 搭配LiveData 使用
 */
public class ViewModelActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, ViewModelActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_view_model;
    }

    @Override
    public void initView() {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                LogUtils.e(user.toString());

                ((TextView) findViewById(R.id.textView15)).setText(user.getName() + user.getLevel());
            }
        });

        userViewModel.setUser(User.newInstance());

        findViewById(R.id.button37).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userViewModel.getUser().getValue();
                user.setLevel((int) (Math.random() * 77));
                userViewModel.setUser(user);

            }
        });

    }
}