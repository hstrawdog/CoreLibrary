package com.hqq.core.ui.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.hqq.core.R;
import com.hqq.core.annotation.LayoutModel;
import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.toolbar.BaseDefToolBarImpl;
import com.hqq.core.toolbar.BaseToolBar;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.ui.builder.IRootView;
import com.hqq.core.ui.model.CreateRootViewModel;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.ui
 * @FileName :   RootViewBuilder
 * @Date : 2018/12/4 0004  下午 7:03
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 * 　动态添加　布局
 * 　根据条件　判断添加状态栏标题栏以及设置状态栏模式
 */
public class IRootViewImpl implements IRootView {
    /**
     * 当前activity
     */
    private Activity mActivity;
    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 是否强制竖屏
     */
    private boolean alwaysPortrait = true;

    /**
     * 布局构建器
     */
    CreateRootViewModel mCreateRootViewModel;

    public <T> IRootViewImpl(T activity) {
        this(activity, false, false);
    }

    public <T> IRootViewImpl(T context, boolean isShowStatus, boolean isShowToolBar) {
        mCreateRootViewModel = new CreateRootViewModel(isShowStatus, isShowToolBar);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
            // 只有在Activity的情况下才会去设置状态栏的颜色  其他的情况默认采用 activity的颜色
            mCreateRootViewModel.setImmersiveStatusBar(true);
        } else if (context instanceof DialogFragment) {
            mActivity = ((DialogFragment) context).getActivity();
            mCreateRootViewModel.setBgColor(R.color.transparent);
        } else if (context instanceof Fragment) {
            mActivity = ((Fragment) context).getActivity();
        } else {
            LogUtils.e(new Exception("不支持的类" + context.getClass().getName()));
        }
        mCreateRootViewModel.setActivity(mActivity);
    }

    @Override
    public View buildContentView(ICreateRootView iActivityBuilder) {
        mRootView = mCreateRootViewModel.createRootView(iActivityBuilder);
        return mRootView;
    }

    @Override
    public void initActivity(boolean fullScreen) {
        // 全屏的需求只有在activity上才需要的
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

    /*********************************Builder 方法*********************************************/
    /**
     * 获取默认状态栏
     *
     * @return
     */
    public <T> BaseDefToolBarImpl getDefToolBar() {
        if (mCreateRootViewModel.getIToolBar() != null && mCreateRootViewModel.getIToolBar() instanceof BaseDefToolBarImpl) {
            return (BaseDefToolBarImpl) mCreateRootViewModel.getIToolBar();
        } else {
            // 自定义的异常 目前先抛出 类型不正确
            LogUtils.e(new Exception("RootViewBuilder no fount BaseDefToolBarImpl "));
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
    public IRootViewImpl setLayoutMode(@LayoutModel int layoutMode) {
        mCreateRootViewModel.setLayoutMode(layoutMode);
        return this;
    }

    /**
     * toolBar 的类名
     *
     * @param clss
     */
    public IRootViewImpl setIToolBarClass(Class<? extends BaseToolBar> clss) {
        mCreateRootViewModel.setIToolBarClass(clss);
        return this;

    }

    /**
     * 是否显示  状态栏  与标题栏
     *
     * @param showStatus  状态栏
     * @param showToolBar 标题栏
     */
    public IRootViewImpl setToolbarVisibility(boolean showStatus, boolean showToolBar) {
        mCreateRootViewModel.setToolbarVisibility(showStatus, showToolBar);
        return this;

    }

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    public IRootViewImpl setShowStatusBar(boolean showStatus) {
        mCreateRootViewModel.setShowStatusBar(showStatus);
        return this;

    }

    /**
     * 是否显示标题栏
     *
     * @param showToolBar
     */
    public IRootViewImpl setShowToolBar(boolean showToolBar) {
        mCreateRootViewModel.setShowToolBar(showToolBar);
        return this;

    }

    /**
     * 设置状态栏模式
     *
     * @param statusBarMode
     * @return
     */
    public IRootViewImpl setStatusBarMode(@ToolBarMode int statusBarMode) {

        mCreateRootViewModel.setStatusBarMode(statusBarMode);
        return this;

    }

    /**
     * 状态栏颜色
     *
     * @param statusColor
     */
    public void setStatusColor(@ColorRes int statusColor) {
        mCreateRootViewModel.setStatusColor(statusColor);
    }

    /**
     * 获取状态栏模式
     *
     * @return
     */
    public int getStatusBarMode() {
        return mCreateRootViewModel.getStatusBarMode();
    }

    /**
     * 是否强制竖屏
     *
     * @return
     */
    public boolean isAlwaysPortrait() {
        return alwaysPortrait;
    }

    /**
     * 设置是否竖屏
     *
     * @param alwaysPortrait
     * @return
     */
    public IRootViewImpl setAlwaysPortrait(boolean alwaysPortrait) {
        this.alwaysPortrait = alwaysPortrait;
        return this;
    }


}


