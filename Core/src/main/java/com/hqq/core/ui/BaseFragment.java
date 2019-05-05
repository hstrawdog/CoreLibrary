package com.hqq.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqq.core.utils.log.LogUtils;
import com.hqq.core.widget.LoadingView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseFragment
 * @Date : 2018/5/28 0028  下午 2:14
 * @Descrive :
 * @Email :
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View mRootView = null;
    protected Activity mActivity;
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
   protected RootViewBuild mRootViewBuild;
    Unbinder mUnkinder;

    public View findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        if (id < 0) {
            return null;
        }
        return mRootView.findViewById(id);
    }

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
//        if (mRootView != null) {
//            ViewGroup parent = (ViewGroup) mRootView.getParent();
//            if (null != parent) {
//                parent.removeView(mRootView);
//            }
//        }
        if (mRootView == null) {
            mActivity = getActivity();
            mLoadingView = new LoadingView(mActivity);
            mRootViewBuild = new RootViewBuild(getActivity(),false,false);
            initDefBuild();
            int vid = getViewId();
            if (vid != 0) {
                mRootView = mRootViewBuild.createRootView(null, vid);
            } else {
                View view = getRootView();
                if (view != null) {
                    mRootView = mRootViewBuild.createRootView(view, 0);
                } else {
                    new Exception("no fount layout and rootView  , must init View");
                }
            }
            mUnkinder = ButterKnife.bind(this, mRootView);
        }
        LogUtils.d("onCreateView " + getClass().getSimpleName() + this.toString());
        return mRootView;
    }

    /**
     * 在没有使用 status 的情况下
     * 只有fragment 完全脱离activity 的回执onDestroy  也就是activity 销毁时
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy " + getClass().getSimpleName() + this.toString());
        //    mLoadingView = null;
        if (mUnkinder != null) {
            mUnkinder.unbind();
            mUnkinder = null;
        }
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
            mRootViewBuild.recoverToolbar();
            mRootViewBuild = null;
        }
        if (mLoadingView != null) {
            mLoadingView = null;
        }
        mActivity = null;
    }


    protected void initDefBuild() {
        mRootViewBuild.setShowStatus(false);
        mRootViewBuild.setShowToolBar(false);
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
            initBasic(null);
            mLazyInitEnd = true;
            LogUtils.d("setUserVisibleHint  initBasic " + getClass().getSimpleName() + this.toString());
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
                initBasic(savedInstanceState);
                LogUtils.d("onViewCreated initBasic   false  " + getClass().getSimpleName() + this.toString());

            } else if (isLazyLoad() && getUserVisibleHint()) {
                mLazyInitEnd = true;
                LogUtils.d("onViewCreated initBasic   True " + getClass().getSimpleName() + this.toString());
                initBasic(savedInstanceState);
            }
        }
    }

    /**
     * 默认不开启延迟 加载
     *
     * @return
     */
    protected boolean isLazyLoad() {
        return false;
    }

    /**
     * fragment 隐藏
     */
    protected void onFragmentHit() {

    }


    /**
     * 关联主界面 <b>只有在使用自定义View时使用</b>
     */
    protected View getRootView() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 关联主界面
     *
     * @return
     */
    public abstract int getViewId();

    /**
     * 基础 初始化
     *
     * @param savedInstanceState 对象  在延迟加载的时候可能会出现null
     */
    public abstract void initBasic(Bundle savedInstanceState);


    /******************************** 繁生方法  ****************************************/


    /******************************** Activity跳转  ****************************************/

    /**
     * @param activity
     */
    public void goActivity(Class<? extends Activity> activity) {
        goActivity(activity, null);
    }

    public void goActivity(Class<? extends Activity> activity, Bundle bundle) {
        goActivity(activity, bundle, -1);
    }

    public void goActivity(Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        goActivity(getActivity(), activity, bundle, requestCode);
    }

    public void goActivity(Activity activity, Class<? extends Activity> activityClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public void goActivityFade(Class<? extends Activity> activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /******************************** getArguments  空判断  ****************************************/


    protected int getArguments2Int(String key, int defaultValue) {
        if (getArguments() != null) {
            return getArguments().getInt(key, defaultValue);
        }
        return defaultValue;
    }

    protected <T extends Parcelable> T getArguments2Parcelable(String key) {
        if (getArguments() != null) {
            return getArguments().getParcelable(key);
        }
        return null;
    }

    protected <T extends Parcelable> ArrayList<T> getArguments2ParcelableArrayList(String key) {
        if (getArguments() != null) {
            return getArguments().getParcelableArrayList(key);
        }
        return null;
    }

    protected String getArguments2getString(String key, String defaultValue) {
        if (getArguments() != null) {
            return getArguments().getString(key, defaultValue);
        }
        return defaultValue;
    }

    protected Object getArgumentsValue(String key, Object defaultValue) {
        if (getArguments() != null) {

            if (defaultValue instanceof Integer) {
                return getArguments().getInt(key, (Integer) defaultValue);
            } else if (defaultValue instanceof String) {
                return getArguments().getString(key, (String) defaultValue);
            }
        }
        return defaultValue;
    }

}
