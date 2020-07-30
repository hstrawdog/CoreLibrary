package com.hqq.example.ui.jetpack.livedata;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.hqq.example.R;
import com.hqq.core.ui.base.BaseActivity;
import com.hqq.core.utils.log.LogUtils;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.databing
 * @FileName :   DataBindingActivity
 * @Date : 2019/10/22 0022  下午 1:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * LiveData  change 需要在ui前台才会触发 并不能再后台执行
 */
public class LiveDateActivity extends BaseActivity {


    public static void open(Activity context) {
        Intent starter = new Intent(context, LiveDateActivity.class);
        context.startActivityForResult(starter, -1);
    }


    TextView mTextView2;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_data_binding;
    }

    @Override
    public void initView() {
        mTextView2=findViewById(R.id.textView2);
        mTextView2.setOnClickListener(this::onViewClicked);
        LiveUser.getInstance(this).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                LogUtils.e("onChanged        " + user.toString());
                mTextView2.setText(user.getName() + user.getLevel());
            }
        });

    }


    public void onViewClicked(View view) {
        User user = LiveUser.getInstance(this).getValue();
        if (null == user) {
            user = User.newInstance();
        }
        user.setLevel(user.getLevel() + 2);
        LiveUser.getInstance(this).setValue(user);

    }
}
