package com.hqq.core.ui.model;

import android.app.Activity;

import androidx.annotation.ColorRes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hqq.core.CoreBuildConfig;
import com.hqq.core.R;
import com.hqq.core.annotation.LayoutModel;
import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.toolbar.BaseToolBar;
import com.hqq.core.toolbar.IToolBar;
import com.hqq.core.toolbar.IToolBarBuilder;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.core.utils.statusbar.StatusBarManager;

import java.lang.ref.WeakReference;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.model
 * @FileName :   CreateRootViewModel
 * @Date : 2019/7/17 0017  下午 5:53
 * @Email : qiqiang213@gmail.com
 * @Descrive : 根布局的创建
 * 目的
 * 1.创建根布局
 * 2.创建标题栏
 * 3.状态栏适配控制
 * <p>
 * 需求
 * 1.虚拟导航栏适配
 * 2.标题栏的多种创建方式
 * 3.微博详情的布局
 * <p>
 * <p>
 * 真正核心的内容 应该拆成两个部分
 * 一个view  一个头部
 */
public class CreateRootViewModel {
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

    /**
     * 创建rootView
     *
     * @param isShowStatus  状态栏
     * @param isShowToolBar 标题栏
     */
    public CreateRootViewModel(boolean isShowStatus, boolean isShowToolBar) {
        mIsShowStatus = isShowStatus;
        mIsShowToolBar = isShowToolBar;
    }

    /**
     * 构建跟布局
     *
     * @param iActivityBuilder
     * @return构建后的View
     */
    public View createRootView(ICreateRootView iActivityBuilder) {
        if (mLayoutMode == LayoutModel.LAYOUT_MODE_LINEAR_LAYOUT) {
            return createLayoutView(iActivityBuilder);
        } else if (mLayoutMode == LayoutModel.LAYOUT_MODE_FRAME_LAYOUT) {
            return createFrameLayoutView(iActivityBuilder);
        } else {
            return null;
        }
    }

    /**
     * 正常 情况下只有 帧布局 才需要 有渐变
     *
     * @param iActivityBuilder
     * @return
     */
    protected View createFrameLayoutView(ICreateRootView iActivityBuilder) {
        FrameLayout frameLayout = new FrameLayout(mActivity.get());
        frameLayout.setOverScrollMode(View.OVER_SCROLL_NEVER);

        View view = getLayoutView(iActivityBuilder, frameLayout);

        frameLayout.setBackgroundResource(mBgColor);
        frameLayout.addView(view);
        createToolBar(frameLayout);
        return frameLayout;
    }

    /**
     * 线性 布局
     *
     * @param iActivityBuilder
     * @return
     */
    protected View createLayoutView(ICreateRootView iActivityBuilder) {
        LinearLayout layout = new LinearLayout(mActivity.get());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setOverScrollMode(View.OVER_SCROLL_NEVER);
        createToolBar(layout);
        View view = getLayoutView(iActivityBuilder, layout);

        layout.setBackgroundResource(mBgColor);
        layout.addView(view);
        return layout;
    }

    private View getLayoutView(ICreateRootView iActivityBuilder, ViewGroup layout) {
        View view;
        if (iActivityBuilder.getLayoutViewId() > 0) {
            view = mActivity.get().getLayoutInflater().inflate(iActivityBuilder.getLayoutViewId(), layout, false);
        } else {
            view = iActivityBuilder.getLayoutView(layout);
            if (view == null) {
                LogUtils.e(new Exception("no fount layoutId and rootView  , must init RootView"));
                view = new View(mActivity.get());
            }
        }
        return view;
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
        IToolBarBuilder iToolBarBuilder = new IToolBarBuilder()
                .setActivity(mActivity.get())
                .setShowStatusBar(mIsShowStatus)
                .setShowToolBar(mIsShowToolBar)
                .setStatusBarColor(mStatusColor);
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
            LogUtils.e(new Exception("RootViewBuilder no fount BaseDefToolBarImpl "));
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
    public void setToolbarVisibility(boolean showStatus, boolean showToolBar) {
        mIsShowStatus = showStatus;
        mIsShowToolBar = showToolBar;

    }

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    public void setShowStatusBar(boolean showStatus) {
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
