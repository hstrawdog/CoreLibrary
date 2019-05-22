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
import com.hqq.core.annotation.StatusBarMode;
import com.hqq.core.toolbar.BaseToolBar;
import com.hqq.core.toolbar.BaseDefToolBarImpl;
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
    BaseToolBar mIToolBar;

    /**
     * 标题栏类型
     */
    Class<?> mClass = CoreBuildConfig.getInstance().getDefItoobar();
    /**
     * 布局类型
     */
    @LayoutModel
    protected int mLayoutMode = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT;

    @StatusBarMode
    protected int mStatusBarMode = CoreBuildConfig.getInstance().isStatusMode();

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
        if (mLayoutMode == LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT) {
            return mRootView = createLayoutView(rootView, vid);
        } else if (mLayoutMode == LayoutModel.LAYOUT_MODE_FRAME_LAYOUT) {
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
        createToolBar(frameLayout);
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
        createToolBar(layout);
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
    protected void createToolBar(ViewGroup layout) {
        if (mStatusBarMode == StatusBarMode.LIGHT_MODE) {
            StatusBarManager.statusBarLightMode(mActivity, true);
        } else {
            StatusBarManager.statusBarLightMode(mActivity, false);

        }
        if (mIsShowToolBar != false || mIsShowStatus != false) {
            initIToolBar(layout);
            layout.addView(mIToolBar.getRootView());
        }
    }

    /**
     * 可以重写 这个方法 去自定义  头部
     *
     * @param layout
     * @return
     */
    public BaseToolBar initIToolBar(ViewGroup layout) {
        if (mIsShowToolBar != false || mIsShowStatus != false) {
            mIToolBar = new IToolBarBuild(mActivity)
                    .setViewGroup(layout)
                    .setShowStatusBar(mIsShowStatus)
                    .setShowToolBar(mIsShowToolBar)
                    .create(mClass);
        }
        return mIToolBar;
    }

    /**
     * 获取 父类态栏
     *
     * @return
     */
    public <T extends BaseToolBar> T getIToolBar() {
        if (mIToolBar == null) {
            //  自定义异常
            try {
                throw   new Exception("RootViewBuild no fount BaseDefToolBarImpl ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return (T) mIToolBar;
    }

    /**
     * 获取默认状态栏
     *
     * @return
     */
    public BaseDefToolBarImpl getDefToolBar() {
        if (getIToolBar() != null && getIToolBar() instanceof BaseDefToolBarImpl) {
            return (BaseDefToolBarImpl) getIToolBar();
        } else {
            // 自定义的异常 目前先抛出 类型不正确
            try {
                throw    new Exception("RootViewBuild no fount BaseDefToolBarImpl ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 获取跟布局
     *
     * @return
     */
    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置根部的样式  目前只支持两种  布局
     *
     * @param layoutMode
     */
    public void setLayoutMode(@LayoutModel int layoutMode) {
        mLayoutMode = layoutMode;
    }

    /**
     * toolBar 的类名
     *
     * @param clss
     */
    public void setIToolBarClass(Class<? extends BaseToolBar> clss) {
        mClass = clss;
    }

    /**
     * 是否显示  状态栏  与标题栏
     *
     * @param showStatus  状态栏
     * @param showToolBar 标题栏
     */
    public void setToolbatVisibility(boolean showStatus, boolean showToolBar) {
        mIsShowStatus = showStatus;
        mIsShowToolBar = showToolBar;
    }

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    public void setShowStatus(boolean showStatus) {
        mIsShowStatus = showStatus;
    }

    /**
     * 是否显示标题栏
     *
     * @param showToolBar
     */
    public void setShowToolBar(boolean showToolBar) {
        mIsShowToolBar = showToolBar;
    }

    /**
     * 设置当前 act 目测没有用 暂时保留
     *
     * @param activity
     */


    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setStatusBarMode(int statusBarMode) {
        mStatusBarMode = statusBarMode;
    }

    public int getStatusBarMode() {
        return mStatusBarMode;
    }
}


