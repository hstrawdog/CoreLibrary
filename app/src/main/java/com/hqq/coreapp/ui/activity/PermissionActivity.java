package com.hqq.coreapp.ui.activity;

import com.hqq.core.permission.PermissionsResult;
import com.hqq.core.permission.PermissionsUtils;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.coreapp.R;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   PermissionActivity
 * @Date : 2019/4/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class PermissionActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initView() {

    }


    @OnClick(R.id.button7)
    public void onMButton7Clicked() {
        PermissionsUtils.showCameraFragment(this, new PermissionsResult() {
            @Override
            public void PermissionsResult(boolean status) {

                ToastUtils.showToast("拥有摄像头权限");
            }
        });

    }

    @OnClick(R.id.button8)
    public void onMButton8Clicked() {
        PermissionsUtils.showStorageFragment(this, new PermissionsResult() {
            @Override
            public void PermissionsResult(boolean status) {

                ToastUtils.showToast("拥有文件读写权限");
            }
        });
    }
}
