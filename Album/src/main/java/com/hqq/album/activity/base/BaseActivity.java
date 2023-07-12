

/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Create on 2016-11-22 下午4:39
 * FileName: BaseActivity.java
 * Author: huang qiqiang
 * Contact: http://www.huangqiqiang.cn
 * Email 593979591@QQ.com
 *
 */

package com.hqq.album.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hqq.album.AppManager;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.zoeru.mylocalalbum.BaseActivity.java
 * @author: 黄其强
 * @date: 2016-11-18 09:33
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 应用是否销毁标志
     */
    protected boolean isDestroy;
    /**
     * 防止重复点击设置的标志，涉及到点击打开其他Activity时，将该标志设置为false，在onResume事件中设置为true
     */
    private boolean clickable = true;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置无标题
        //垂直显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        isDestroy = false;

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次返回界面时，将点击标志设置为可点击
        clickable = true;
    }

    /**
     * 当前是否可以点击
     *
     * @return
     */
    protected boolean isClickable() {
        return clickable;
    }

    /**
     * 锁定点击
     */
    protected void lockClick() {
        clickable = false;
    }


    public void goActivity(Class<? extends Activity> activity) {
        goActivity(activity, null);
    }

    public void goActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 针对6.0动态请求权限问题
     * 判断是否允许此权限
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 动态请求权限
     *
     * @param code
     * @param permissions
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    protected void fullWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


}
