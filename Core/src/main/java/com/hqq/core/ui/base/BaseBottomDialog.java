package com.hqq.core.ui.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hqq.core.R;
import com.hqq.core.ui.builder.ICreateRootView;
import com.hqq.core.ui.base.IRootViewImpl;
import com.hqq.core.utils.log.LogUtils;


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
    boolean mLoaded = false;
    protected BottomSheetBehavior<FrameLayout> behavior;
    /**
     * 布局创建 容器
     */
    protected IRootViewImpl mRootViewBuild;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getTransparentBottomSheetStyle());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootViewBuild = new IRootViewImpl(this);
            initDefConfig();
            mRootView = mRootViewBuild.buildContentView(this);
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
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
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
        mRootView = null;
    }

    @Override
    public int getHeight() {
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
    public View getLayoutView(ViewGroup parent) {
        return null;
    }


}
