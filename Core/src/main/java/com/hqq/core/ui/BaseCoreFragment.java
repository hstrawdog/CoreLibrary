package com.hqq.core.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hqq.core.ui.builder.ICreateRootViewBuilder;
import com.hqq.core.ui.builder.RootViewBuilder;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.core.widget.LoadingView;


/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFragment
 * @Date : 2018/5/28 0028  下午 2:14
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public abstract class BaseCoreFragment extends Fragment implements ICreateRootViewBuilder.IFragmentBuilder, View.OnClickListener {
    /**
     * 缓存根布局对象
     */
    protected View mRootView = null;
    /**
     * 当前Activity
     */
    protected Activity mActivity;
    /**
     * LoadingDialog
     */
    public LoadingView mLoadingView;
    /**
     * fragment 是否创建
     */
    public boolean mIsCreate = false;
    /**
     * 延迟加载是否结束
     */
    boolean mLazyInitEnd = false;
    /**
     * 布局创建 容器
     */
    protected RootViewBuilder mRootViewBuild;

    /**
     * 在viewPage 中不断的切换 fragment  都会不断的去执行 onCreateView 的方法
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mActivity = getActivity();
            mLoadingView = new LoadingView(mActivity);
            mRootViewBuild = new RootViewBuilder(this);
            initDefConfig();

            mRootView = mRootViewBuild.buildContentView(this);
        }
        LogUtils.d(this.getClass().getName(), "onCreateView " + getClass().getSimpleName() + this.toString());
        return mRootView;
    }

    /**
     * 延迟加载
     * 当页面在前台显示时 才开始记载数据
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoad() && mIsCreate && !mLazyInitEnd && isVisibleToUser) {
            initView();
            mLazyInitEnd = true;
            LogUtils.d(this.getClass().getName(), "setUserVisibleHint  initBasic " + getClass().getSimpleName() + this.toString());
        } else if (isLazyLoad() && mIsCreate && mLazyInitEnd && !isVisibleToUser) {
            onFragmentHit();
        }
    }

    /**
     * 保证一个fragment 只会执行一次
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mIsCreate && mRootView != null) {
            mIsCreate = true;
            if (!isLazyLoad()) {
                initView();

                LogUtils.d(this.getClass().getName(), "onViewCreated initBasic   false  " + getClass().getSimpleName() + this.toString());

            } else if (isLazyLoad() && getUserVisibleHint()) {
                mLazyInitEnd = true;
                LogUtils.d(this.getClass().getName(), "onViewCreated initBasic   True " + getClass().getSimpleName() + this.toString());
                initView();
            }
        }
    }

    /**
     * 在没有使用 status 的情况下
     * 只有fragment 完全脱离activity 的回执onDestroy  也就是activity 销毁时
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(this.getClass().getName(), "onDestroy " + getClass().getSimpleName() + this.toString());

        if (mRootView != null) {
            if (mRootView instanceof ViewGroup) {
                ((ViewGroup) mRootView).removeAllViews();
            }
            if (null != mRootView.getParent()) {
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
                mRootView = null;
            }
        }
        if (mRootViewBuild != null) {
            mRootViewBuild = null;
        }
        if (mLoadingView != null) {
            mLoadingView = null;
        }
        mActivity = null;
    }

    @Override
    public void initDefConfig() {

    }

    /**
     * 默认不开启延迟 加载
     *
     * @return
     */
    @Override
    public boolean isLazyLoad() {
        return false;
    }

    /**
     * 关联主界面 <b>只有在使用自定义View时使用</b>
     */
    @Override
    public View getLayoutView(ViewGroup parent) {
        return null;
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * fragment 隐藏
     */
    protected void onFragmentHit() {

    }

    /******************************** 繁生方法  ****************************************/

    public View findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        if (id < 0) {
            return null;
        }
        return mRootView.findViewById(id);
    }


}
