package com.hqq.example.ui.model;

import android.app.Activity;
import androidx.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hqq.example.CoreBuildConfig;
import com.hqq.example.R;
import com.hqq.example.annotation.LayoutModel;
import com.hqq.example.annotation.ToolBarMode;
import com.hqq.example.toolbar.BaseToolBar;
import com.hqq.example.toolbar.IToolBar;
import com.hqq.example.toolbar.IToolBarBuilder;
import com.hqq.example.utils.statusbar.StatusBarManager;

import java.lang.ref.WeakReference;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.model
 * @FileName :   CreateRootViewModel
 * @Date : 2019/7/17 0017  下午 5:53
 * @Email : qiqiang213@gmail.com
 * @Descrive : 根布局的创建
 */
public class CreateRootViewModel {
    /**
     * 标题栏类型
     */
    private Class<? extends IToolBar> mClass = CoreBuildConfig.getInstance().getDefItoobar();
    @LayoutModel
    private int mLayoutMode = LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT;
    /**
     * 当前上下文
     */
    WeakReference<Activity> mActivity;
    /**
     * 状态栏颜色
     */
    @ColorRes
    int mStatusColor = R.color.white;

    /**
     * dialogFragment 不带背景颜色
     */
    @ColorRes
    int mBgColor = R.color.bg_color;
    /**
     * 标题栏
     */
    private IToolBar mIToolBar;
    /**
     * 是否执行 状态栏 透明化
     */
    private boolean immersiveStatusBar;
    /**
     * 状态栏模式
     */
    @ToolBarMode
    private int mStatusBarMode = CoreBuildConfig.getInstance().isStatusMode();
    /**
     * 是否显示状态栏
     */
    private boolean mIsShowStatus;
    /**
     * 是否显示 标题栏
     */
    private boolean mIsShowToolBar;

    public CreateRootViewModel(boolean isShowStatus, boolean isShowToolBar) {
        mIsShowStatus = isShowStatus;
        mIsShowToolBar = isShowToolBar;
    }

    /**
     * 构建布局
     *
     * @param layoutId xml id
     * @param rootView 布局View
     * @return
     */
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
     * 构建跟布局
     *
     * @param rootView 布局View
     * @param vid      布局id
     * @return 构建后的View
     */
    public View createRootView(View rootView, int vid) {
        if (mLayoutMode == LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT) {
            return createLayoutView(rootView, vid);
        } else if (mLayoutMode == LayoutModel.LAYOUT_MODE_FRAME_LAYOUT) {
            return createFrameLayoutView(rootView, vid);
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
        FrameLayout frameLayout = new FrameLayout(mActivity.get());
        frameLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        View view;
        if (vid > 0) {
            view = mActivity.get().getLayoutInflater().inflate(vid, frameLayout, false);
        } else {
            view = rootView;
        }
        frameLayout.setBackgroundResource(mBgColor);
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
        LinearLayout layout = new LinearLayout(mActivity.get());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        createToolBar(layout);
        View view;
        if (vid > 0) {
            view = mActivity.get().getLayoutInflater().inflate(vid, layout, false);
        } else {
            view = rootView;
        }

        layout.setBackgroundResource(mBgColor);
        layout.addView(view);
        return layout;
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
                StatusBarManager.setStatusBarModel(mActivity.get().getWindow(), true);
            } else {
                StatusBarManager.setStatusBarModel(mActivity.get().getWindow(), false);
            }
        }
        if (mIsShowToolBar || mIsShowStatus) {
            mIToolBar = initIToolBar();
            layout.addView(mIToolBar.getRootView());
        }
    }


    /**
     * 可以重写 这个方法 去自定义  头部
     *
     * @return
     */
    public IToolBar initIToolBar() {
        IToolBarBuilder iToolBarBuilder = new IToolBarBuilder();
        iToolBarBuilder.setActivity(mActivity.get());
        iToolBarBuilder.setShowStatusBar(mIsShowStatus);
        iToolBarBuilder.setShowToolBar(mIsShowToolBar);
        iToolBarBuilder.setStatusBarColor(mStatusColor);
        return iToolBarBuilder.create(mClass);
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
     * toolBar 的类名
     *
     * @param clss
     */
    public void setIToolBarClass(Class<? extends BaseToolBar> clss) {
        mClass = clss;

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
     * 状态栏颜色
     *
     * @param statusColor
     */
    public void setStatusColor(@ColorRes int statusColor) {
        mStatusColor = statusColor;
    }

    /**
     * 设置状态栏模式
     *
     * @param statusBarMode
     * @return
     */
    public void setStatusBarMode(@ToolBarMode int statusBarMode) {
        mStatusBarMode = statusBarMode;
    }

    /**
     * 获取状态栏模式
     *
     * @return
     */
    public int getStatusBarMode() {
        return mStatusBarMode;
    }

    /**
     * 当前Activity 对象
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    /**
     * 是否设置状态适配  activity 是默认设置的
     *
     * @param immersiveStatusBar
     */
    public void setImmersiveStatusBar(boolean immersiveStatusBar) {
        this.immersiveStatusBar = immersiveStatusBar;

    }

    /**
     * 背景颜色 默认是color_bg
     * 当前是dialog 的时候默认设置为透明颜色
     *
     * @param bgColor
     */
    public void setBgColor(@ColorRes int bgColor) {
        mBgColor = bgColor;

    }


}
