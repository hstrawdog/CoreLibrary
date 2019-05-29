package com.hqq.core.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hqq.core.R;
import com.hqq.core.listenner.DialogClickListener;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.ui.builder.RootViewBuilder;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.core.utils.statusbar.StatusBarManager;
import com.hqq.core.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseDialog
 * @Date : 2018/6/19 0019  上午 11:33
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public abstract class BaseDialog extends DialogFragment implements ICreateRootView.IDialogFragment {

    Unbinder mUnkinder;
    protected LoadingView mLoadingView;
    boolean mLoaded = false;
    protected DialogClickListener mDialogClickListener;
    protected View mRootView = null;
    /**
     * 布局创建 容器
     */
    protected RootViewBuilder mRootViewBuild;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //代码设置 无标题 无边框
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DefDialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(setAnimation());
        if (mRootView == null) {
            mLoadingView = new LoadingView(getActivity());
            mRootViewBuild = new RootViewBuilder(this);
            initDefConfig();

            mRootView = mRootViewBuild.initContentView(setViewId(), setRootView());
            mUnkinder = ButterKnife.bind(this, mRootView);
        }
        LogUtils.d("onCreateView " + getClass().getSimpleName() + this.toString());
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mLoaded && mRootView != null) {
            StatusBarManager.transparencyBar(getDialog().getWindow());
            mLoaded = true;
            initView();
        }
    }

    @Override
    public View setRootView() {
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getBackground()));
        getDialog().getWindow().setLayout(setWeight(), setHeight());
        getDialog().getWindow().setGravity(setGravity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnkinder.unbind();
        mLoadingView = null;
        mRootView = null;
    }


    public void show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag(getClass().getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.add(this, getClass().getSimpleName());
        ft.commitAllowingStateLoss();
        // super.show(manager, getClass().getSimpleName());
    }


    public BaseDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        mDialogClickListener = dialogClickListener;
        return this;
    }

    /**
     * @return 背景颜色
     */
    public int getBackground() {
        return 0x00000000;
    }

    public int setGravity() {
        return Gravity.CENTER;
    }

    protected int setWeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int setHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int setAnimation() {
        return R.style.DialogAnimation_bottom2top;
    }


}
