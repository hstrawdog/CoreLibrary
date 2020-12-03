package com.hqq.example.dialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.hqq.core.adapter.BaseFragmentAdapter;
import com.hqq.core.ui.dialog.BaseDialog;
import com.hqq.example.R;
import com.hqq.example.ui.tab.layout.TabFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.dialog
 * @FileName :   FullDialog
 * @Date : 2019/5/24 0024  上午 9:14
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FullDialog extends BaseDialog {

    public static void showDialog(FragmentManager fragmentManager) {
        FullDialog fragment = new FullDialog();
        fragment.show(fragmentManager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_full;
    }

    @Override
    public void initConfig() {

    }

    @Override
    public int getAnimation() {
        return R.anim.fade_in;
    }

    @Override
    public void initView() {
        ViewPager2 mVpPage = getRootView().findViewById(R.id.vp_page);
        TabLayout mTbTablayout1 = getRootView().findViewById(R.id.tb_layout);


        ViewPageAdapter adapter = new ViewPageAdapter(this);
        mVpPage.setAdapter(adapter);
        adapter.setupWithViewPager(mTbTablayout1, mVpPage);
    }

    static class ViewPageAdapter extends BaseFragmentAdapter {

        public ViewPageAdapter(@NotNull Fragment fragment) {
            super(fragment);

            getStringSparseArray().append(0, "咖啡");
            getStringSparseArray().append(1, "奶茶");
            getStringSparseArray().append(2, "菊花茶");
            getStringSparseArray().append(3, "霸王杯");
            getStringSparseArray().append(4, "冬瓜茶");
        }

        @NotNull
        @Override
        protected Fragment newFragment(int position) {
            return new TabFragment();
        }
    }
}
