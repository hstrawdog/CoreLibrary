package com.hqq.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.hqq.core.utils.KeyboardUtils;
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
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Activity mActivity;
    public LoadingView mLoadingView;
    protected RootViewBuild mRootViewBuild = new RootViewBuild();

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
                new Exception("no fount layout and rootView  , must init View");
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
    protected void initDefConfig() {
        /**
         * RootViewBuild　
         * 默认布局类型
         */
        mRootViewBuild.setActivity(this);
        mRootViewBuild.setShowStatus(visibilityStatusBar());
        mRootViewBuild.setShowToolBar(visibilityToolBar());

    }

    /**
     * 初始化  rootView
     * 默认 不采用
     *
     * @return
     */
    protected View getRootView() {

        return null;
    }

    /**
     * 是否强制竖屏
     *
     * @return true 则强制竖屏
     */
    protected boolean alwaysPortrait() {
        return true;
    }

    /**
     * 是否全屏
     *
     * @return true则全屏
     */
    protected boolean fullScreen() {
        return false;
    }

    /**
     * 获取id
     *
     * @return
     */
    public abstract int getViewId();

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 是否显示状态栏
     *
     * @return
     */
    protected boolean visibilityStatusBar() {
        return true;
    }

    /**
     * 11.22  修改方法 是否显示标题栏
     *
     * @return
     */
    protected Boolean visibilityToolBar() {
        return true;
    }

    /**
     * 隐藏键盘方法
     * @param event
     * @param view
     */
    protected void hideKeyboard(MotionEvent event, View view) {
        if (view != null && view instanceof EditText) {
            int[] location = {0, 0};
            view.getLocationInWindow(location);
            int left = location[0], top = location[1], right = left + view.getWidth(), bootom = top + view.getHeight();
            // 判断焦点位置坐标是否在控件内，如果位置在控件外则隐藏键盘
            if (event.getRawX() < left || event.getRawX() > right || event.getY() < top || event.getRawY() > bootom) {
                // 隐藏键盘
                KeyboardUtils.hideSoftInput(view);
            }
        }
    }


    /*********************************** Activity 跳转*******************************************************************************/
    protected void goActivity(Class<?> cls) {
        goActivity(cls, null);
    }

    protected void goActivity(Class<?> cls, Bundle bundle) {
        goActivity(cls, bundle, -1);
    }

    protected void goActivity(Class<?> cls, int requestCode) {
        goActivity(cls, null, requestCode);
    }

    protected void goActivity(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);

    }

}
