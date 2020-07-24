package com.hqq.example.ui.jetpack.viewbinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityViewBindingBinding;

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
public class ViewBindingActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, ViewBindingActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return 0;
    }

    ActivityViewBindingBinding binding;

    @Override
    public View getLayoutView(ViewGroup parent) {
        // ActivityViewBindingBinding 对应生成的 布局  驼峰命名
        binding = ActivityViewBindingBinding.inflate(getLayoutInflater(), parent, false);
        return binding.getRoot();
    }

    @Override
    public void initView() {
        binding.textView17.setText("12312312312");
        binding.button48.setText("3333");
        binding.imageView10.setBackgroundResource(R.drawable.dfh);

    }
}