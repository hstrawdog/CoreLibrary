package com.hqq.example.ui.jetpack.package1;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hqq.core.ui.vm.BaseActivity;
import com.hqq.core.ui.vm.BaseViewModel;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityVmTestBinding;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   VmTestActivity
 * @Date : 2020/7/27  下午11:03
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */

public class VmTestActivity extends BaseActivity<ActivityVmTestBinding, BaseViewModel> {
    public static void open(Context context) {
        Intent starter = new Intent(context, VmTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void addViewModel() {
        mBinding.setLifecycleOwner(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vm_test;
    }

    @Override
    public void initViews() {
        mBinding.button49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setShowLoading(true);
            }
        });
    }
}