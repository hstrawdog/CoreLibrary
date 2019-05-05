package com.hqq.core.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hqq.core.CoreBuildConfig;
import com.hqq.core.R;
import com.hqq.core.annotation.LayoutModel;
import com.hqq.core.toolbar.IDefToolBarImpl;
import com.hqq.core.toolbar.IToolBar;
import com.hqq.core.toolbar.IToolBarBuild;
import com.hqq.core.utils.statusbar.StatusBarManager;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   RootViewBuild
 * @Date : 2018/12/4 0004  下午 7:03
 * @Descrive :
 * @Email :
 * 　主要功能
 * 　动态添加　布局
 * 　根据条件　判断添加　状态栏　标题栏
 */
public class RootViewBuild {
    /**
     * 布局
     * LinearLayout
     */
    public static final int LAYOUT_MODE_LINEAR_LAYOUT = 1;
    /**
     * 布局
     * frameLayout
     */
    public static final int LAYOUT_MODE_FRAME_LAYOUT = 2;
    /**
     * 当前activitry
     */
    Activity mActivity;
    /**
     * 是否显示状态栏
     */
    boolean mIsShowStatus;
    /**
     * 是否显示 标题栏
     */
    boolean mIsShowToolBar;
    /**
     * 根布局
     */
    View mRootView;
    /**
     * 标题栏
     */
    IToolBar mIToolBar;

    /**
     * 标题栏类型
     */
    Class<?> mClass = CoreBuildConfig.getInstance().getDefItoobar();
    /**
     * 布局类型
     */
    @LayoutModel
    protected int mLayoutMode = LAYOUT_MODE_LINEAR_LAYOUT;


    public RootViewBuild(Activity activity, boolean isShowStatus, boolean isShowToolBar) {
        mActivity = activity;
        mIsShowStatus = isShowStatus;
        mIsShowToolBar = isShowToolBar;
    }

    /**
     * 构建跟布局
     *
     * @param rootView
     * @param vid
     * @return
     */
    protected View createRootView(View rootView, int vid) {
        if (mLayoutMode == LAYOUT_MODE_LINEAR_LAYOUT) {
            return mRootView = createLayoutView(rootView, vid);
        } else if (mLayoutMode == LAYOUT_MODE_FRAME_LAYOUT) {
            return mRootView = createFrameLayoutView(rootView, vid);
        } else {
            return null;
        }
    }

    /**
     * 正常 情况下只有 帧布局 才需要 有渐变
     *
     * @param rootView
     * @param vid
     * @return
     */
    protected View createFrameLayoutView(View rootView, int vid) {
        FrameLayout frameLayout = new FrameLayout(mActivity);
        frameLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        View view;
        if (vid > 0) {
            view = mActivity.getLayoutInflater().inflate(vid, frameLayout, false);
        } else {
            view = rootView;
        }
        frameLayout.setBackgroundResource(R.color.bg_color);
        frameLayout.addView(view);
        addToolBar(frameLayout);
        return frameLayout;
    }

    /**
     * 线性 布局
     *
     * @param rootView
     * @param vid
     * @return
     */
    protected View createLayoutView(View rootView, int vid) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        addToolBar(layout);
        View view;
        if (vid > 0) {
            view = mActivity.getLayoutInflater().inflate(vid, layout, false);
        } else {
            view = rootView;
        }
        layout.setBackgroundResource(R.color.bg_color);
        layout.addView(view);
        return layout;
    }

    /**
     * 回收ToolBar
     */
    public void recoverToolbar() {
        if (getIToolBar() != null) {
            View view = getIToolBar().getRootView();
            if (view != null && view instanceof ViewGroup) {
                if ((view.getParent() != null)) {
                    ((ViewGroup) view).removeView(view);
                }
            }
        }
    }

    /**
     * 设置状态栏颜色   所以需要init
     * 但是可以不添加到布局中
     *
     * @param layout
     */
    protected void addToolBar(ViewGroup layout) {
        initIToolBar(layout);
        if (mIsShowToolBar != false || mIsShowStatus != false) {
            layout.addView(mIToolBar.getRootView());
        }
    }

    /**
     * 可以重写 这个方法 去自定义  头部
     *
     * @param layout
     * @return
     */
    public IToolBar initIToolBar(ViewGroup layout) {
        StatusBarManager.statusBarLightMode(mActivity, CoreBuildConfig.getInstance().isStatusMode());
        if (mIsShowToolBar != false || mIsShowStatus != false) {
            mIToolBar = new IToolBarBuild(mActivity)
                    .setViewGroup(layout)
                    .setShowStatusBar(mIsShowStatus)
                    .setShowToolBar(mIsShowToolBar)
                    .create(mClass);
        }
        return mIToolBar;
    }

    public IToolBar getIToolBar() {
        if (mIToolBar == null) {
            // 避免空指针
            new Exception("RootViewBuild no fount IDefToolBarImpl ");
            return null;
        }
        return mIToolBar;
    }

    public IDefToolBarImpl getDefToolBar() {
        if (getIToolBar() != null && getIToolBar() instanceof IDefToolBarImpl) {
            return (IDefToolBarImpl) getIToolBar();
        } else {
            // 开发中的异常 目前先抛出
            new Exception("RootViewBuild no fount IDefToolBarImpl ");
            return null;
        }
    }

    public void setIToolBarClass(Class<? extends IToolBar> clss) {
        mClass = clss;
    }

    public View getRootView() {
        return mRootView;
    }

    public void setShowStatus(boolean showStatus) {
        mIsShowStatus = showStatus;
    }

    public void setShowToolBar(boolean showToolBar) {
        mIsShowToolBar = showToolBar;
    }

    public void setLayoutMode(@LayoutModel int layoutMode) {
        mLayoutMode = layoutMode;
    }

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

}
