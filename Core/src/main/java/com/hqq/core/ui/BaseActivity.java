package com.hqq.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hqq.core.R;
import com.hqq.core.ui.builder.ICreateRootViewBuilder;
import com.hqq.core.ui.builder.RootViewBuilder;
import com.hqq.core.widget.LoadingView;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseActivity
 * @Date : 2018/2/9  10:01
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 1.
 */
public abstract class BaseActivity extends AppCompatActivity implements ICreateRootViewBuilder.IActivityBuilder, View.OnClickListener {
    /**
     * 当前对象
     */
    protected Activity mActivity;
    /**
     * LoadingDialog
     */
    public LoadingView mLoadingView;
    /**
     * 根布局
     */
    protected RootViewBuilder mRootViewBuild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initAnimEnter();
        super.onCreate(savedInstanceState);
        mActivity = this;
        mLoadingView = new LoadingView(this);

        mRootViewBuild = new RootViewBuilder(this, true, true);
        initDefConfig();
        setContentView(mRootViewBuild.buildContentView(this));
        initView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            onResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void finish() {
        super.finish();
        initAnimExit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 手动回收
        mRootViewBuild = null;
    }

    @Override
    public void initAnimEnter() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    @Override
    public void initAnimExit() {
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
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
        mRootViewBuild.initActivity(false);
    }

    /**
     * 初始化  rootView
     * 默认 不采用
     *
     * @return
     */
    @Override
    public View getLayoutView(ViewGroup parent) {
        return null;
    }


}
