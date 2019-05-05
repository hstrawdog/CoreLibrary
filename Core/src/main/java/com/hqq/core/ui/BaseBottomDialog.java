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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hqq.core.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseBottomDialog
 * @Date : 2019/4/29 0029  下午 2:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseBottomDialog extends BottomSheetDialogFragment {

    protected View mRootView = null;
    Unbinder unbinder;
    boolean mLoaded = false;
    protected BottomSheetBehavior<FrameLayout> behavior;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE , getTransparentBottomSheetStyle());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            layoutParams.height = getHeight();
            behavior = BottomSheetBehavior.from(bottomSheet);
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mRootView = null;
    }

    protected int getHeight() {
        return CoordinatorLayout.LayoutParams.MATCH_PARENT;
    }

    private int getTransparentBottomSheetStyle() {
        return R.style.TransparentBottomSheetStyle;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
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
}
