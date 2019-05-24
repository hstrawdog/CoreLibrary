package com.hqq.core.toolbar;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   IToolBarBuild
 * @Date : 2018/11/23 0023  下午 1:34
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class IToolBarBuild<T extends BaseToolBar> {
    T mIToolBar;
    Activity mActivity;
    ViewGroup mViewGroup;
    /**
     * 是否显示 状态栏 背景
     */
    boolean mIsShowStatusBar = true;
    /**
     * 是否显示 ToolBar
     */
    boolean mIsShowToolBar = true;
    int mStatusBarColor = Color.WHITE;

    public IToolBarBuild setStatusBarColor(int statusBarColor) {
        mStatusBarColor = statusBarColor;
        return this;
    }

    public IToolBarBuild(Activity activity) {
        mActivity = activity;
    }

    public IToolBarBuild setViewGroup(ViewGroup viewGroup) {
        mViewGroup = viewGroup;
        return this;
    }

    public IToolBarBuild setShowStatusBar(boolean isShowStatusBar) {
        mIsShowStatusBar = isShowStatusBar;
        return this;
    }

    public IToolBarBuild setShowToolBar(boolean showToolBar) {
        mIsShowToolBar = showToolBar;
        return this;
    }

    public T create(Class<T> clas) {
        try {
            mIToolBar = clas.newInstance();
            mIToolBar.setShowStatusBar(mIsShowStatusBar)
                    .setShowBar(mIsShowToolBar)
                    .setDefStatusColor(mStatusBarColor)
                    .createToolBar(mActivity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIToolBar;
    }


}
