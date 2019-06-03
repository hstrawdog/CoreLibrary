package com.hqq.core.toolbar;

import android.app.Activity;
import android.graphics.Color;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   IToolBarBuilder
 * @Date : 2018/11/23 0023  下午 1:34
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class IToolBarBuilder {
    private Activity mActivity;
    /**
     * 是否显示 状态栏 背景
     */
    private boolean mIsShowStatusBar = true;
    /**
     * 是否显示 ToolBar
     */
    private boolean mIsShowToolBar = true;
    /**
     * 默认白色
     */
    private int mStatusBarColor = Color.WHITE;

    public IToolBarBuilder setStatusBarColor(int statusBarColor) {
        mStatusBarColor = statusBarColor;
        return this;
    }

    public IToolBarBuilder setActivity(Activity activity) {
        mActivity = activity;
        return this;
    }

    public IToolBarBuilder setShowStatusBar(boolean isShowStatusBar) {
        mIsShowStatusBar = isShowStatusBar;
        return this;
    }

    public IToolBarBuilder setShowToolBar(boolean showToolBar) {
        mIsShowToolBar = showToolBar;
        return this;
    }

    /**
     * @param clss 类型
     * @return
     */
    public <T extends IToolBar> T create(Class<T> clss) {
        try {
            T toolBar = clss.newInstance();
            toolBar.setShowStatusBar(mIsShowStatusBar)
                    .setShowBar(mIsShowToolBar)
                    .setDefStatusColor(mStatusBarColor)
                    .createToolBar(mActivity);
            return toolBar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
