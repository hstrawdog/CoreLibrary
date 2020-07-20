package com.hqq.core.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hqq.core.CoreBuildConfig;
import com.hqq.core.R;
import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.listenner.DialogClickListener;
import com.hqq.core.ui.builder.ICreateRootViewBuilder;
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
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * - 默认 背景是透明的 状态栏亮色模式
 * - 在高度全屏的情况下 进行状态栏模式设置
 * - 宽度需要在代码上写死
 * - DialogFragment自带内存泄漏
 */
public abstract class BaseDialog extends DialogFragment implements ICreateRootViewBuilder.IDialogFragmentBuilder {

    Unbinder mUnkinder;
    protected LoadingView mLoadingView;
    boolean mLoaded = false;
    protected DialogClickListener mDialogClickListener;
    protected View mRootView = null;
    /**
     * 布局创建 容器
     */
    protected RootViewBuilder mRootViewBuild;

    /**
     * 状态栏模式
     */
    @ToolBarMode
    protected int mStatusBarMode = CoreBuildConfig.getInstance().isStatusMode();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //代码设置 无标题 无边框
//        if (setWeight() == WindowManager.LayoutParams.WRAP_CONTENT) {
//            setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
//        } else {
//        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DefDialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(getAnimation());
        if (mRootView == null) {
            mLoadingView = new LoadingView(getActivity());
            mRootViewBuild = new RootViewBuilder(this);
            initDefConfig();
            mRootView = mRootViewBuild.buildContentView(R.layout.dialog_new, null);
            initContentView();
            mUnkinder = ButterKnife.bind(this, mRootView);
            initView();

            LogUtils.d("onCreateView " + getClass().getSimpleName() + this.toString());
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mLoaded && mRootView != null) {
            if (mStatusBarMode == ToolBarMode.LIGHT_MODE) {
                StatusBarManager.setStatusBarModel(getDialog().getWindow(), true);
            } else if (mStatusBarMode == ToolBarMode.DARK_MODE) {
                StatusBarManager.setStatusBarModel(getDialog().getWindow(), false);
            } else {
                // 默认进行全屏显示
                StatusBarManager.transparencyBar(getDialog().getWindow());
            }
            mLoaded = true;

            LogUtils.e(mRootView.getWidth());

        }
    }

    private void initContentView() {
        LinearLayout linearLayout = mRootView.findViewById(R.id.ll_rootView);
        View view = LayoutInflater.from(getContext()).inflate(getViewId(), linearLayout, false);
        linearLayout.setGravity(getGravity());
        linearLayout.addView(view);
        view.setOnClickListener(view1 -> {
        });
        if (isDismissBackground()) {
            linearLayout.setOnClickListener(view2 -> dismiss());
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e(mRootView.getMeasuredWidth());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getBackground()));
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setGravity(getGravity());
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnkinder.unbind();
        mLoadingView = null;
        mRootView = null;
    }

    @Override
    public View getRootView() {
        return null;
    }


    @Override
    public void show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag(getClass().getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.add(this, getClass().getSimpleName());
        ft.commitAllowingStateLoss();
    }

    /**
     * 默认配置
     */
    @Override
    public void initDefConfig() {
    }

    public boolean isDismissBackground() {
        return true;
    }


    /**
     * @return 背景颜色
     */
    @Override
    public int getBackground() {
        return 0x00000000;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getWeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getAnimation() {
        return R.style.DialogAnimation_bottom2top;
    }


    public BaseDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        mDialogClickListener = dialogClickListener;
        return this;
    }

}
