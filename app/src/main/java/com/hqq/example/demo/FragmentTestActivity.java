package com.hqq.example.demo;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.vm.BaseVmActivity;
import com.hqq.core.ui.vm.BaseViewModel;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityFragmentTestBinding;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   FragmentTestActivity
 * @Date : 2020/7/28 0028  下午 5:51
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class FragmentTestActivity extends BaseVmActivity<ActivityFragmentTestBinding, BaseViewModel> {
    public static void open(Activity context) {
        Intent starter = new Intent(context, FragmentTestActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_test;
    }

    @Override
    public void initViews() {
        Fragment fragment = TestFragment.newInstance();
        addFragment(fragment, R.id.fl_layout);
    }


    /**
     * 添加 fragment 到 FrameLayout
     *
     * @param fragment fragment
     * @param id       FrameLayout
     */
    @Deprecated
    public void addFragment(Fragment fragment, int id) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
        }

    }

    @Override
    public int getBindingViewModelId() {
        return 0;
    }
}