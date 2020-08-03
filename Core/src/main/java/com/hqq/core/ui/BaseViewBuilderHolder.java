package com.hqq.core.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.hqq.core.lifecycle.BaseLifecycleObserver;
import com.hqq.core.ui.builder.ICreateRootView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseViewBuilderHolder
 * @Date : 2019/10/31 0031  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseViewBuilderHolder extends ViewHolder implements ICreateRootView.IBaseViewBuilderHolder, BaseLifecycleObserver {

    private ViewGroup mParentView;
    Activity mActivity;

    /**
     * 构建activity
     *
     * @param appCompatActivity
     * @param parentView
     */
    public BaseViewBuilderHolder builder(AppCompatActivity appCompatActivity, ViewGroup parentView) {
        createRootView(parentView, appCompatActivity, appCompatActivity, appCompatActivity.getLifecycle());
        return this;
    }

    /**
     * 转移fragment
     *
     * @param fragment
     * @param parentView
     */
    public BaseViewBuilderHolder builder(Fragment fragment, ViewGroup parentView) {
        createRootView(parentView, fragment.getActivity(), fragment.getContext(), fragment.getLifecycle());
        return this;
    }

    @Override
    public void createRootView(ViewGroup parentView, Activity activity, Context context, Lifecycle lifecycle) {
        mParentView = parentView;
        mActivity = activity;
        if (getLayoutViewId() <= 0) {
            mConvertView = getLayoutView(mParentView);
        } else {
            mConvertView = LayoutInflater.from(context).inflate(getLayoutViewId(), mParentView, false);
        }
        lifecycle.addObserver(this);
        initView();
    }


    @Override
    public void initDefConfig() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getLayoutView(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onCrete() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onAny() {

    }

    @Override
    public void onDestroy() {
        removeFromParent();
    }

    public void addToParent() {
        if (mParentView != null && mConvertView != null) {
            mParentView.addView(mConvertView);
        }
    }

    public void removeFromParent() {
        ViewParent parent = mConvertView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mConvertView);
        }
    }

}
