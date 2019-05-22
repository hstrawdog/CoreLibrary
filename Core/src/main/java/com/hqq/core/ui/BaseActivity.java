package com.hqq.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hqq.core.widget.LoadingView;

import butterknife.ButterKnife;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.blibrary
 * @FileName :   BaseActivity
 * @Date : 2018/2/9  10:01
 * @Descrive : TODO
 * @Email :
 */
public abstract class BaseActivity extends AppCompatActivity implements ICreateRootView.IActivity, View.OnClickListener {
    protected Activity mActivity;
    public LoadingView mLoadingView;
    protected RootViewBuild mRootViewBuild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //竖屏
        if (alwaysPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (fullScreen()) {
            //隐藏状态 上的字体还颜色
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        mActivity = this;
        mRootViewBuild = new RootViewBuild(this, true, true);
        initDefConfig();

        //  构建  ContentView 默认 LineLayout 构建   支持  xml /view
        // 优先构建xml
        int vid = getViewId();
        if (vid != 0) {
            setContentView(mRootViewBuild.createRootView(null, vid));
        } else {
            View view = getRootView();
            if (view != null) {
                setContentView(mRootViewBuild.createRootView(view, 0));
            } else {
                try {
                    throw new Exception("no fount layout and rootView  , must createToolBar View");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        mLoadingView = new LoadingView(this);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //  ARouter.getInstance().inject(this);
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            onResult(requestCode, resultCode, data);
        }
    }

    /**
     * 判断后的 onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 默认配置
     */
    @Override
    public void initDefConfig() {
        /**
         * RootViewBuild　
         * 默认布局类型
         */
        mRootViewBuild.setShowStatus(true);
        mRootViewBuild.setShowToolBar(true);
    }

    /**
     * 初始化  rootView
     * 默认 不采用
     *
     * @return
     */
    @Override
    public View getRootView() {
        return null;
    }

    /**
     * 是否强制竖屏
     *
     * @return true 则强制竖屏
     */
    @Override
    public boolean alwaysPortrait() {
        return true;
    }

    /**
     * 是否全屏
     *
     * @return true则全屏
     */
    @Override
    public boolean fullScreen() {
        return false;
    }


}
