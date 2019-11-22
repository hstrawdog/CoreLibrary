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
import com.hqq.core.ui.builder.ICreateRootViewBuilder;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseViewBuilderHolder
 * @Date : 2019/10/31 0031  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseViewBuilderHolder extends ViewHolder implements ICreateRootViewBuilder.IBaseViewBuilderHolder, BaseLifecycleObserver {

    private ViewGroup mParentView;
    Activity mActivity;

    /**
     * 构建activity
     *
     * @param appCompatActivity
     * @param parentView
     */
    public BaseViewBuilderHolder(AppCompatActivity appCompatActivity, ViewGroup parentView) {
        createRootView(parentView, appCompatActivity, appCompatActivity, appCompatActivity.getLifecycle());
    }

    /**
     * 转移fragment
     *
     * @param fragment
     * @param parentView
     */
    public BaseViewBuilderHolder(Fragment fragment, ViewGroup parentView) {
        createRootView(parentView, fragment.getActivity(), fragment.getContext(), fragment.getLifecycle());

    }

    @Override
    public void createRootView(ViewGroup parentView, Activity activity, Context context, Lifecycle lifecycle) {
        mParentView = parentView;
        mActivity = activity;
        if (setViewId() <= 0) {
            mConvertView = parentView;
        } else {
            mConvertView = LayoutInflater.from(context).inflate(setViewId(), mParentView, false);
        }
        lifecycle.addObserver(this);
        initView();
    }


    @Override
    public void initDefConfig() {

    }

    @Override
    public View setRootView() {
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
