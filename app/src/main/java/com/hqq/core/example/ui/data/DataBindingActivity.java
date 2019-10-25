package com.hqq.core.example.ui.data;

import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.hqq.core.example.R;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.log.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.databing
 * @FileName :   DataBindingActivity
 * @Date : 2019/10/22 0022  下午 1:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive : DataBinding 与现在的设计冲突
 */
public class DataBindingActivity extends BaseActivity {

    @BindView(R.id.textView2)
    TextView mTextView2;

    @Override
    public int setViewId() {
        return R.layout.activity_data_binding;
    }

    @Override
    public void initView() {

        LiveUser.getInstance(this).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                LogUtils.e("onChanged        " + user.toString());
            }
        });

    }


    @OnClick(R.id.button19)
    public void onViewClicked() {
        User user = LiveUser.getInstance(this).getValue();
        if (null == user) {
            user = User.newInstance();
        }
        user.setLevel(user.getLevel() + 2);
        LiveUser.getInstance(this).setValue(user);

    }
}
