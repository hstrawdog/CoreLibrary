package com.hqq.core.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hqq.core.R;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.ui.builder.RootViewBuilder;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.core.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseBottomDialog
 * @Date : 2019/4/29 0029  下午 2:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 宽度是全屏
 * 高度自定义
 */
public abstract class BaseBottomDialog extends BottomSheetDialogFragment implements ICreateRootView.IBottomDialogFragment {

    protected View mRootView = null;
    Unbinder mUnkinder;
    boolean mLoaded = false;
    protected BottomSheetBehavior<FrameLayout> behavior;
    /**
     * 布局创建 容器
     */
    protected RootViewBuilder mRootViewBuild;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getTransparentBottomSheetStyle());

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
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
            mLoaded = true;
            initView();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置软键盘不自动弹出
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = setHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnkinder.unbind();
        mRootView = null;
    }

    @Override
    public int setHeight() {
        return CoordinatorLayout.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void initDefConfig() {

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

    protected int getTransparentBottomSheetStyle() {
        return R.style.TransparentBottomSheetStyle;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }


    @Override
    public View setRootView() {
        return null;
    }


}
