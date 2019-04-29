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
import com.hqq.core.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   BaseDialog
 * @Date : 2018/6/19 0019  上午 11:33
 * @Descrive : TODO
 * @Email :
 */
public abstract class BaseDialog extends DialogFragment {

    Unbinder unbinder;
    protected LoadingView mLoadingView;
    boolean mLoaded = false;
    protected DialogClickListener mDialogClickListener;

    public BaseDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        mDialogClickListener = dialogClickListener;
        return this;
    }


    protected View mRootView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 可以全屏 但是 背景消失
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //代码设置 无标题 无边框
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(setAnimation());
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        if (mRootView == null) {
            FrameLayout layout = new FrameLayout(getActivity());
            layout.addView(inflater.inflate(setView(), layout, false));
            mRootView = layout;
        }
        mLoadingView = new LoadingView(getActivity());
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mLoaded && mRootView != null) {
            mLoaded = true;
            initView();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getBackground()));
        getDialog().getWindow().setLayout(setWeight(), setHeight());
        getDialog().getWindow().setGravity(setGravity());
//        //透明状态栏
//        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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

    /**
     * 关联主界面
     *
     * @return int
     */
    public abstract int setView();

    /**
     * 初始化
     */
    protected abstract void initView();

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
