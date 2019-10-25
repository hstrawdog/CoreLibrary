package com.hqq.example.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.hqq.example.ui.builder.ICreateRootView;
import com.hqq.example.ui.builder.RootViewBuilder;
import com.hqq.example.widget.LoadingView;

import butterknife.ButterKnife;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseActivity
 * @Date : 2018/2/9  10:01
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 1.
 */
public abstract class BaseActivity extends AppCompatActivity implements ICreateRootView.IActivity, View.OnClickListener {
    protected Activity mActivity;
    public LoadingView mLoadingView;
    protected RootViewBuilder mRootViewBuild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mRootViewBuild = new RootViewBuilder(this, true, true);
        initDefConfig();
        setContentView(mRootViewBuild.initContentView(setViewId(), setRootView()));
        mLoadingView = new LoadingView(this);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        initView();
        //  ARouter.getInstance().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 手动回收
        mRootViewBuild = null;
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
    protected void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 默认配置
     */
    @Override
    public void initDefConfig() {
        mRootViewBuild.initActivity();
    }

    /**
     * 初始化  rootView
     * 默认 不采用
     *
     * @return
     */
    @Override
    public View setRootView() {
        return null;
    }


}
