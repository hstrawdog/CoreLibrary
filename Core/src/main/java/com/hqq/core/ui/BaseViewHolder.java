package com.hqq.core.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hqq.core.lifecycle.BaseLifecycleObserver;
import com.hqq.core.ui.builder.ICreateRootView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseViewHolder
 * @Date : 2019/10/31 0031  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseViewHolder extends ViewHolder implements ICreateRootView.IBaseViewHolder, BaseLifecycleObserver {

    private ViewGroup mParentView;
    Activity mActivity;


    /**
     * 构建activity
     *
     * @param appCompatActivity
     * @param parentView
     */
    public BaseViewHolder(AppCompatActivity appCompatActivity, ViewGroup parentView) {
        super();
        mParentView = parentView;
        mActivity = appCompatActivity;
        mConvertView = LayoutInflater.from(appCompatActivity).inflate(setViewId(), mParentView, false);
        appCompatActivity.getLifecycle().addObserver(this);
        initView();
    }

    /**
     * 直接使用 activity 中的view
     *
     * @param appCompatActivity
     * @param view
     */
    public BaseViewHolder(AppCompatActivity appCompatActivity, View view) {
        super();
        mActivity = appCompatActivity;
        mConvertView = view;
        appCompatActivity.getLifecycle().addObserver(this);
        initView();
    }

    /**
     * 转移fragment
     *
     * @param fragment
     * @param parentView
     */
    public BaseViewHolder(Fragment fragment, ViewGroup parentView) {
        super();
        mParentView = parentView;
        mActivity = fragment.getActivity();
        mConvertView = LayoutInflater.from(fragment.getContext()).inflate(setViewId(), mParentView, false);
        fragment.getLifecycle().addObserver(this);
        initView();
    }

    /**
     * 直接使用 fragment中的View
     *
     * @param fragment
     * @param view
     */
    public BaseViewHolder(Fragment fragment, View view) {
        super();
        mActivity = fragment.getActivity();
        mConvertView = view;
        fragment.getLifecycle().addObserver(this);
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
