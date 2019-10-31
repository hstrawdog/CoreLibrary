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
public abstract class BaseViewHolder implements ICreateRootView.IBaseViewHolder, BaseLifecycleObserver {

    private View mContentView;
    private ViewGroup mParentView;
    Activity mActivity;


    public BaseViewHolder(AppCompatActivity appCompatActivity, ViewGroup parentView) {
        mParentView = parentView;
        mActivity = appCompatActivity;
        mContentView = LayoutInflater.from(appCompatActivity).inflate(setViewId(), mParentView, false);
        appCompatActivity.getLifecycle().addObserver(this);
        initView();
    }
    public BaseViewHolder(Fragment fragment, ViewGroup parentView) {
        mParentView = parentView;
        mActivity = fragment.getActivity();
        mContentView = LayoutInflater.from(fragment.getContext()).inflate(setViewId(), mParentView, false);
        fragment.getLifecycle().addObserver(this);
        initView();
    }


    protected View findViewById(int res) {
        return mContentView.findViewById(res);
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
        if (mParentView != null && mContentView != null) {
            mParentView.addView(mContentView);
        }
    }

    public void removeFromParent() {
        ViewParent parent = mContentView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mContentView);
        }
    }



}
