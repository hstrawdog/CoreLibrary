package com.hqq.example.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.hqq.example.utils.ToastUtils;
import com.hqq.example.utils.VersionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   PermissionsFragment
 * @Date : 2018/11/22 0022  上午 10:41
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class PermissionsFragment extends Fragment {
    public static int CODE = 0x5186;
    PermissionsResult mPermissionsResult;

    public void setPermissionsResult(PermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;
    }

    public static PermissionsFragment newInstance() {
        Bundle args = new Bundle();
        PermissionsFragment fragment = new PermissionsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * 发现 系统选择 与 厂商权限不一致   取消判断是否拥有权限 直接去申请
     *
     * @param strings
     */
    public void requestPermissions(String[] strings) {
        mPermissions = strings;
        if (hasPermission(getContext(), strings)) {
            mPermissionsResult.onPermissionsResult(true);
        } else {
            requestPermissions(strings, CODE);
        }
    }


    /**
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Context context, String... permissions) {
        boolean has = permissions != null && permissions.length > 0;
        if (has) {
            for (String permission : permissions) {
                has = has && ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            }
        }
        return has;
    }

    /**
     * 申请的权限组
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
            //   ToastUtils.showToast(getContext(), "同意了所有权限");
            if (mPermissionsResult != null) {
                mPermissionsResult.onPermissionsResult(true);
            }
        } else {
            ToastUtils.showToast(getContext(), "拒绝权限,会导致功能无法执行");
            // 打开设置界面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", VersionUtils.getPackageName(getContext()), null);
            intent.setData(uri);
            startActivityForResult(intent, 0x55);


            //勾选了对话框中”Don’t ask again”的选项, 返回false
            for (String deniedPermission : permissions) {
                boolean flag = shouldShowRequestPermissionRationale(deniedPermission);
                if (!flag) {
                    //拒绝授权
                    return;
                }
            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x55) {
            // 从设置界面过来 重新再去检测权限
            requestPermissions(mPermissions);

        }

    }
}
