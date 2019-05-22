package com.hqq.core.toolbar;

import android.app.Activity;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.toolbar
 * @FileName :   IToolBar
 * @Date : 2019/5/8 0008  下午 3:43
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * <p>
 * StatusBar  状态栏
 * TitleBar  标题栏
 * ToolBar  标题栏+状态栏
 * <p>
 * 由于这边是View 存在两种情况
 * - 在View 还未初始化完成 未添加到Activity中
 * - 在View 初始化完成 已经添加到 Activity后
 */
public interface IToolBar {
    /**
     * 构建  标题栏
     *
     * @param activity
     * @return
     */
    IToolBar createToolBar(Activity activity);

    /**
     * 构建toolBar
     *
     * @param activity  activity
     * @param viewGroup viewGroup
     */
    View iniToolBar(final Activity activity, ViewGroup viewGroup);


    void setToolBarColor(@ColorRes int colorId);

}
