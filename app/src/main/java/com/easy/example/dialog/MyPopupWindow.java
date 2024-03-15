package com.easy.example.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.easy.core.adapter.BaseFragmentAdapter;
import com.easy.example.R;
import com.easy.example.ui.tab.layout.TabFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.dialog
 * @Date : 下午 3:52
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class MyPopupWindow extends PopupWindow {
    private View contentView;

    public MyPopupWindow(Context context) {
        super(context);
        //获得 LayoutInflater 的实例
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.dialog_popup, null);

        ViewPager2 mVpPage = contentView.findViewById(R.id.vp_page);
        TabLayout mTbTablayout1 = contentView.findViewById(R.id.tb_layout);


        ViewPageAdapter adapter = new ViewPageAdapter((FragmentActivity) context);
        mVpPage.setAdapter(adapter);
        adapter.setupWithViewPager(mTbTablayout1, mVpPage);


        setContentView(contentView);

    }

    static class ViewPageAdapter extends BaseFragmentAdapter {

        public ViewPageAdapter(@NotNull FragmentActivity fragment) {
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
    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
}
