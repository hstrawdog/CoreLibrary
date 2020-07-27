package com.hqq.example.ui.adaptation;

import android.app.Activity;
import android.content.Intent;

import com.hqq.core.permission.PermissionsResult;
import com.hqq.core.permission.PermissionsUtils;
import com.hqq.core.ui.BaseCoreActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.example.R;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   PermissionActivity
 * @Date : 2019/4/22 0022
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
public class PermissionActivity extends BaseCoreActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, PermissionActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_permission;
    }

    @Override
    public void initView() {

    }

    public void onMButton7Clicked() {
        PermissionsUtils.requestCameraPermission(new PermissionsResult() {
            @Override
            public void onPermissionsResult(boolean status) {
                ToastUtils.showToast("拥有摄像头权限");
            }
        });

    }

    public void onMButton8Clicked() {
        PermissionsUtils.requestStoragePermission(new PermissionsResult() {
            @Override
            public void onPermissionsResult(boolean status) {
                ToastUtils.showToast("拥有文件读写权限");
            }
        });
    }
}
