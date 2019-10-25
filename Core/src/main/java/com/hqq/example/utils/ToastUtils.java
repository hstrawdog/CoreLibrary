/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hqq.example.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.hqq.example.CoreBuildConfig;


/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   ToastUtils
 * @Date : 2018/12/14 0014
 * @Descrive : 输出工具类
 * @Email :  qiqiang213@gmail.com
 */
public class ToastUtils {
    /**
     * 全局单利
     * 吐司部分，只显示一个
     */
    private static Toast mToast;


    @SuppressLint("ShowToast")
    private static void showToast(final Context context, final String text, final int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (mToast != null ) {
            mToast.cancel();
        }
            mToast = Toast.makeText(context.getApplicationContext(), text, duration);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setText(text);

        mToast.show();
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * @param context  content
     * @param strId    String id
     * @param duration 显示时间
     */
    public static void showToast(Context context, @StringRes int strId, int duration) {
        showToast(context, context.getString(strId), duration);
    }

    /**
     * 同上
     *
     * @param context content
     * @param strId   内容
     */
    public static void showToast(Context context, @StringRes int strId) {
        showToast(context, context.getString(strId), Toast.LENGTH_LONG);
    }


    /**
     * 默认显示   content 是Application
     *
     * @param text 内容
     */
    public static void showToast(String text) {
        showToast(CoreBuildConfig.getInstance().getApplication().getBaseContext(), text);

    }
}
