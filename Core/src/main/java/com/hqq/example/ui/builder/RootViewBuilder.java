package com.hqq.example.ui.builder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import androidx.annotation.ColorRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hqq.example.R;
import com.hqq.example.annotation.LayoutModel;
import com.hqq.example.annotation.ToolBarMode;
import com.hqq.example.toolbar.BaseDefToolBarImpl;
import com.hqq.example.toolbar.BaseToolBar;
import com.hqq.example.ui.model.CreateRootViewModel;

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
public class RootViewBuilder implements IRootViewBuilder {
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
     * 是否全屏显示
     */
    private boolean fullScreen = false;

    /**
     * 布局构建器
     */
    CreateRootViewModel mCreateRootViewModel;

    public <T> RootViewBuilder(T activity, boolean isShowStatus, boolean isShowToolBar) {
        mCreateRootViewModel = new CreateRootViewModel(isShowStatus, isShowToolBar);
        if (activity instanceof Activity) {
            mActivity = (Activity) activity;
            mCreateRootViewModel.setImmersiveStatusBar(true);
        } else if (activity instanceof DialogFragment) {
            mActivity = ((DialogFragment) activity).getActivity();
            mCreateRootViewModel.setBgColor(R.color.transparent);
        } else if (activity instanceof Fragment) {
            mActivity = ((Fragment) activity).getActivity();
        } else {
            try {
                throw new Exception("不支持的类" + activity.getClass().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCreateRootViewModel.setActivity(mActivity);
    }

    public <T> RootViewBuilder(T activity) {
        this(activity, false, false);
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
        mRootView = mCreateRootViewModel.initContentView(layoutId, rootView);
        return mRootView;
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
            try {
                // 自定义的异常 目前先抛出 类型不正确
                throw new Exception("RootViewBuilder no fount BaseDefToolBarImpl ");
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
    public RootViewBuilder setLayoutMode(@LayoutModel int layoutMode) {
        mCreateRootViewModel.setLayoutMode(layoutMode);
        return this;
    }

    /**
     * toolBar 的类名
     *
     * @param clss
     */
    public RootViewBuilder setIToolBarClass(Class<? extends BaseToolBar> clss) {
        mCreateRootViewModel.setIToolBarClass(clss);
        return this;

    }

    /**
     * 是否显示  状态栏  与标题栏
     *
     * @param showStatus  状态栏
     * @param showToolBar 标题栏
     */
    public RootViewBuilder setToolbatVisibility(boolean showStatus, boolean showToolBar) {
        mCreateRootViewModel.setToolbatVisibility(showStatus, showToolBar);
        return this;

    }

    /**
     * 是否显示标题栏
     *
     * @param showStatus
     */
    public RootViewBuilder setShowStatus(boolean showStatus) {
        mCreateRootViewModel.setShowStatus(showStatus);
        return this;

    }

    /**
     * 是否显示标题栏
     *
     * @param showToolBar
     */
    public RootViewBuilder setShowToolBar(boolean showToolBar) {
        mCreateRootViewModel.setShowToolBar(showToolBar);
        return this;

    }

    /**
     * 设置状态栏模式
     *
     * @param statusBarMode
     * @return
     */
    public RootViewBuilder setStatusBarMode(@ToolBarMode int statusBarMode) {

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
    public RootViewBuilder setAlwaysPortrait(boolean alwaysPortrait) {
        this.alwaysPortrait = alwaysPortrait;
        return this;
    }

    /**
     * 是否全屏
     *
     * @return
     */
    public boolean isFullScreen() {
        return fullScreen;
    }

    /**
     * 设置是否全屏
     *
     * @param fullScreen
     * @return
     */
    public RootViewBuilder setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        return this;

    }
}


