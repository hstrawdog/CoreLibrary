package com.hqq.core.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.hqq.core.utils.ToastUtils;
import com.hqq.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsFragment
 * @Date : 2018/11/22 0022  上午 10:41
 * @Descrive :
 * @Email :
 */
public class PermissionsFragment extends Fragment {
    public static int CODE = 0x5186;
    IPermissionsResult mIPermissionsResult;

    public void setIPermissionsResult(IPermissionsResult IPermissionsResult) {
        mIPermissionsResult = IPermissionsResult;
    }

    public PermissionsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * 发现 系统选择 与 厂商权限不一致   取消判断是否拥有权限 直接去申请
     * @param strings
     */
    public void requestPermissions(String[] strings) {
        mPermissions = strings;

        if (hasPermission(getContext(), strings)) {
        } else {
            requestPermissions(strings, CODE);
        }
        requestPermissions(strings, CODE);
    }


    public   static boolean hasPermission(Context context, String... permissions) {
        boolean has = permissions != null && permissions.length > 0;
        if (has) {
            for (String permission : permissions) {
                has = has && ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            }
        }

        return has;
    }

    /**
     *  申请的权限组
     */
    String[] mPermissions;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        if (grantResults == null) {
            for (String perm : permissions) {
                if (hasPermission(getContext(), perm)) {
                    granted.add(perm);
                } else {
                    boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), perm);

                    denied.add(perm);
                }
            }
        } else {
            for (int index = 0, length = permissions.length; index < length; index++) {
                String perm = permissions[index];
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    granted.add(perm);
                } else {
                    denied.add(perm);
                }
            }
        }

        if (denied.isEmpty() && !granted.isEmpty()) {
            if (mIPermissionsResult != null) {
                ToastUtils.showToast(getContext(), "同意了所有权限");
                mIPermissionsResult.PermissionsResult(permissions, grantResults);
            }
        } else {
            ToastUtils.showToast(getContext(), "拒绝权限");
            new AppSettingsDialog.Builder(this).build().show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (hasPermission(getContext(), mPermissions)) {
                ToastUtils.showToast(getContext(), R.string.open_all_permission);
            } else {
                ToastUtils.showToast(getContext(), R.string.un_open_all_permission);
            }
        }
    }
}
