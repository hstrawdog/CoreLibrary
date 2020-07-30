package com.hqq.example.ui.jetpack.databinding;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.BR;
import com.hqq.example.R;
import com.hqq.example.ui.jetpack.livedata.User;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.dagger
 * @FileName :   DaggerAActivity
 * @Date : 2020/7/15 0015  下午 4:18
 * @Email :  qiqiang213@gmail.com
 * @Descrive : dataBinding  双向绑定 需要实现BaseObservable 进行观察 以及通知回调
 * 注意 setVariable
 */
public class DataBindingActivity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, DataBindingActivity.class);
        context.startActivityForResult(starter, -1);

    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_dagger_a;
    }


    @Override
    public void initView() {

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_data_binding, null, false);
        ((LinearLayout) findViewById(R.id.ll_rootView)).addView(viewDataBinding.getRoot());
        User user = new User();
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setVariable(BR.user, user);
        viewDataBinding.setVariable(BR.dataBinding, new DataBanding());
        user.setName("1111");

//        findViewById(R.id.button38).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LogUtils.e("-----------" + user.getName());
//            }
//        });


    }


}