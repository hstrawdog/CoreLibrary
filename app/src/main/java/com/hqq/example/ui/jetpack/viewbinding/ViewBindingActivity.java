package com.hqq.example.ui.jetpack.viewbinding;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.hqq.core.ui.BaseCoreActivity;
import com.hqq.example.BR;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityViewBindingBinding;
import com.hqq.example.ui.jetpack.package1.UserViewModel;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.viewbinding
 * @FileName :   ViewBindingActivity
 * @Date : 2020/7/21 0021  下午 1:57
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 视图绑定在 Android Studio 3.6 Canary 11 及更高版本中可用。
 * 新版官方文档 https://developer.android.com/topic/libraries/view-binding
 * 旧版官方文档 https://www.jb51.net/article/182708.htm
 * 错误解决 https://www.it610.com/article/1281824114532696064.htm
 */
public class ViewBindingActivity extends BaseCoreActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, ViewBindingActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return 0;
    }

    ActivityViewBindingBinding mBinding;

    @Override
    public View getLayoutView(ViewGroup parent) {
        // ActivityViewBindingBinding 对应生成的 布局  驼峰命名
        mBinding = ActivityViewBindingBinding.inflate(getLayoutInflater(), parent, false);


        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBinding.textView17.setText("12312312312");
        mBinding.button48.setText("3333");
        mBinding.imageView10.setBackgroundResource(R.drawable.dfh);
        UserViewModel viewModel= new ViewModelProvider(this).get(UserViewModel.class);
        mBinding.setVariable(BR.vm,viewModel );
        mBinding.setLifecycleOwner(this);

    }
}