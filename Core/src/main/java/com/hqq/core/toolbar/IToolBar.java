package com.hqq.core.toolbar;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.toolbar
 * @FileName :   IToolBar
 * @Date : 2019/5/8 0008  下午 3:43
 * @Email : qiqiang213@gmail.com
 * @Descrive :
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

}
