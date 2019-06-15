package com.hqq.core.toolbar;


import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hqq.core.R;
import com.hqq.core.utils.ScreenUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   BaseToolBar
 * @Date : 2018/11/15 0015  下午 7:26
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * -  第一层 背景    toolBar
 * -  第二层 状态栏  statusBar
 * -  第三层 标题栏  TitleBar
 */
public abstract class BaseToolBar implements IToolBar {
    /**
     * mLinearLayout  包含
     * 1. 顶部状态栏背景颜色
     * 2. 中间 toolbar 与 toolbar  背景颜色  与 底部的分割线
     */

    /**
     * 根布局
     */
    public View mRootView = null;
    /**
     * 状态栏背景颜色
     */
    protected int mDefStatusColor = R.color.toolbar_status_color;

    /**
     * 状态栏背景
     */
    protected View mStatusBar;
    /**
     * 当前activity
     */
    protected Activity mActivity;
    /**
     * 底部分割线
     */
    protected View mViewLine;
    /**
     * 是否显示 分割线
     */
    protected boolean mIsShowLine = true;

    protected boolean mIsShowBar = true;

    protected boolean mIsShowStatusBar = true;

    @Override
    public BaseToolBar setShowStatusBar(boolean showStatusBar) {
        mIsShowStatusBar = showStatusBar;
        return this;
    }

    /**
     * 创建 toolbar
     */
    @Override
    public BaseToolBar createToolBar(final Activity activity) {
        mActivity = activity;
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        if (mIsShowStatusBar) {
            initStatusBar(mActivity);
            int mStatusBarHeight = ScreenUtils.getStatusBarHeight(activity);
            linearLayout.addView(mStatusBar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mStatusBarHeight));
        }

        // 目前不知道是否需要都执行 createToolBar
        View toolBar = iniToolBar(activity, linearLayout);
        if (mIsShowBar && toolBar != null) {
            linearLayout.addView(toolBar);
        }

        if (mIsShowLine) {
            mViewLine = new View(mActivity);
            mViewLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(mActivity, 1)));
            mViewLine.setBackgroundResource(R.color.toolbar_line_bg);
            linearLayout.addView(mViewLine);
        }
        mRootView = linearLayout;
        return this;
    }


    /***************** statusBar  *************************/
    /**
     * 获取状态 底部的View
     *
     * @return
     */
    public View getStatusBar() {
        return mStatusBar;
    }

    /**
     * 设置 状态栏底部的颜色
     *
     * @param colorInt
     */
    @Override
    public BaseToolBar setDefStatusColor(@ColorInt int colorInt) {
        if (getStatusBar() != null) {
            getStatusBar().setBackgroundColor(colorInt);
        }else {
            mDefStatusColor=colorInt;
        }
        return this;
    }

    @Override
    public BaseToolBar setShowBar(boolean showBar) {
        mIsShowBar = showBar;
        return this;
    }

    /**
     * 滑动渐变
     *
     * @param alpha 0 -1
     */
    public void initScroll(float alpha) {
        if (mStatusBar != null) {
            mStatusBar.setAlpha(alpha);
        }
        if (mViewLine != null) {
            mViewLine.setAlpha(alpha);
        }
    }

    /**
     * 初始化状态栏  底部的view
     *
     * @param activity
     */
    public void initStatusBar(Activity activity) {
        mStatusBar = new View(activity);
        mStatusBar.setBackgroundResource(mDefStatusColor);
    }
    /***************** Toolbar  *************************/

    /**
     * 获取 根布局
     *
     * @return mRootView
     */
    @Override
    public View getRootView() {
        return mRootView;
    }

    /***************** Line  *************************/
    /**
     * 需要在 createToolBar 之前调用才会生效
     * 这个方法会导致 mViewLine 是 null
     * 但是会优化布局
     * {@link #showLine}
     *
     * @param showLine
     */
    @Deprecated
    public void setShowLine(boolean showLine) {
        mIsShowLine = showLine;
    }

    /**
     * 获取分割线
     *
     * @return mViewLine
     */
    public View getViewLine() {
        return mViewLine;
    }

    /**
     * 是否显示分割线
     *
     * @param isShow isShow
     */
    public void showLine(boolean isShow) {
        if (isShow) {
            getViewLine().setVisibility(View.VISIBLE);
        } else {
            getViewLine().setVisibility(View.GONE);
        }
    }

    /**
     * 设置分割线颜色
     *
     * @param color color id
     */
    public void setLineColor(int color) {
        getViewLine().setBackgroundResource(color);
    }

}
