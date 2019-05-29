package com.hqq.core.ui.builder;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hqq.core.CoreBuildConfig;
import com.hqq.core.R;
import com.hqq.core.annotation.LayoutModel;
import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.toolbar.BaseToolBar;
import com.hqq.core.toolbar.BaseDefToolBarImpl;
import com.hqq.core.toolbar.IToolBar;
import com.hqq.core.toolbar.IToolBarBuilder;
import com.hqq.core.utils.statusbar.StatusBarManager;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   RootViewBuilder
 * @Date : 2018/12/4 0004  下午 7:03
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * 　主要功能
 * 　动态添加　布局
 * 　根据条件　判断添加　状态栏　标题栏
 */
public class RootViewBuilder implements IRootViewBuilder {

    /**
     * 当前activity
     */
    private Activity mActivity;
    /**
     * 是否显示状态栏
     */
    private boolean mIsShowStatus;
    /**
     * 是否显示 标题栏
     */
    private boolean mIsShowToolBar;
    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 标题栏
     */
    private IToolBar mIToolBar;

    /**
     * 标题栏类型
     */
    private Class<? extends IToolBar> mClass = CoreBuildConfig.getInstance().getDefItoobar();
    /**
     * 布局类型
     */
    @LayoutModel
    private int mLayoutMode = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT;

    /**
     *
     */
    @ToolBarMode
    private int mStatusBarMode = CoreBuildConfig.getInstance().isStatusMode();

    /**
     * 是否强制竖屏
     */
    private boolean alwaysPortrait = true;
    /**
     * 是否全屏显示
     */
    private boolean fullScreen = false;

    /**
     * 是否执行 状态栏 透明化
     */
    private boolean immersiveStatusBar = false;


    public <T> RootViewBuilder(T activity, boolean isShowStatus, boolean isShowToolBar) {
        if (activity instanceof Activity) {
            mActivity = (Activity) activity;
            immersiveStatusBar = true;
        } else if (activity instanceof Fragment) {
            mActivity = ((Fragment) activity).getActivity();
        } else if (activity instanceof DialogFragment) {
            mActivity = ((DialogFragment) activity).getActivity();
        } else {
            try {
                throw new Exception("不支持的布局");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        mIsShowStatus = isShowStatus;
        mIsShowToolBar = isShowToolBar;
    }

    public <T> RootViewBuilder(T activity) {
        this(activity, false, false);
    }

    /**
     * 构建跟布局
     *
     * @param rootView 布局View
     * @param vid      布局id
     * @return 构建后的View
     */
    @Override
    public View createRootView(View rootView, int vid) {

        if (mLayoutMode == LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT) {
            return mRootView = createLayoutView(rootView, vid);
        } else if (mLayoutMode == LayoutModel.LAYOUT_MODE_FRAME_LAYOUT) {
            return mRootView = createFrameLayoutView(rootView, vid);
        } else {
            return null;
        }
    }

    @Override
    public void initActivity() {
        if (fullScreen) {
            //隐藏状态 上的字体还颜色
            mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        //竖屏
        if (alwaysPortrait) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    @Override
    public View initContentView(int layoutId, View rootView) {
        //  构建  ContentView 默认 LineLayout 构建   支持  xml /view
        // 优先构建xml
        if (layoutId != 0) {
            return createRootView(null, layoutId);
        } else {
            if (rootView != null) {
                return createRootView(rootView, 0);
            } else {
                try {
                    throw new Exception("no fount layoutId and rootView  , must init RootView");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
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
    private void createToolBar(ViewGroup layout) {
        // 默认只有Activity 会去执行设置状态栏的颜色
        if (immersiveStatusBar) {
            if (mStatusBarMode == ToolBarMode.LIGHT_MODE) {
                StatusBarManager.statusBarLightMode(mActivity, true);
            } else {
                StatusBarManager.statusBarLightMode(mActivity, false);
            }
        }

        if (mIsShowToolBar || mIsShowStatus) {
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
    public void initIToolBar(ViewGroup layout) {
        IToolBarBuilder iToolBarBuilder = new IToolBarBuilder();
        iToolBarBuilder.setActivity(mActivity);
        iToolBarBuilder.setViewGroup(layout);
        iToolBarBuilder.setShowStatusBar(mIsShowStatus);
        iToolBarBuilder.setShowToolBar(mIsShowToolBar);
        mIToolBar = iToolBarBuilder.create(mClass);
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
                throw new Exception("RootViewBuilder no fount BaseDefToolBarImpl ");
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
                throw new Exception("RootViewBuilder no fount BaseDefToolBarImpl ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /*********************************Build 方法*********************************************/

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
    public RootViewBuilder setLayoutMode(@LayoutModel int layoutMode) {
        mLayoutMode = layoutMode;
        return this;
    }

    /**
     * toolBar 的类名
     *
     * @param clss
     */
    public RootViewBuilder setIToolBarClass(Class<? extends BaseToolBar> clss) {
        mClass = clss;
        return this;

    }

    /**
     * 是否显示  状态栏  与标题栏
     *
     * @param showStatus  状态栏
     * @param showToolBar 标题栏
     */
    public RootViewBuilder setToolbatVisibility(boolean showStatus, boolean showToolBar) {
        mIsShowStatus = showStatus;
        mIsShowToolBar = showToolBar;
        return this;

    }

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    public RootViewBuilder setShowStatus(boolean showStatus) {
        mIsShowStatus = showStatus;
        return this;

    }

    /**
     * 是否显示标题栏
     *
     * @param showToolBar
     */
    public RootViewBuilder setShowToolBar(boolean showToolBar) {
        mIsShowToolBar = showToolBar;
        return this;

    }


    public RootViewBuilder setStatusBarMode(@ToolBarMode int statusBarMode) {
        mStatusBarMode = statusBarMode;
        return this;

    }

    public int getStatusBarMode() {
        return mStatusBarMode;
    }

    public boolean isAlwaysPortrait() {
        return alwaysPortrait;
    }

    public RootViewBuilder setAlwaysPortrait(boolean alwaysPortrait) {
        this.alwaysPortrait = alwaysPortrait;
        return this;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public RootViewBuilder setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        return this;

    }
}


