package com.hqq.example.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hqq.core.ui.BaseSelectDialog;
import com.hqq.core.ui.BaseViewHolder;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.dialog
 * @FileName :   SelectADialog
 * @Date : 2019/10/31 0031  上午 11:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SelectADialog extends BaseSelectDialog<BaseViewHolder> {

    public static void showDialog(FragmentManager fragmentManager) {
        SelectADialog dialog = new SelectADialog();
        dialog.show(fragmentManager);
    }


    @Override
    protected BaseViewHolder getViewHolder() {
        return new ViewHolderA(this, mRootView.findViewById(R.id.ll_content));
    }

    @Override
    protected void initData() {

    }

    class ViewHolderA extends BaseViewHolder {

        public ViewHolderA(Fragment fragment, ViewGroup parentView) {
            super(fragment, parentView);
        }

        @Override
        public int setViewId() {
            return R.layout.view_holder_a;
        }

        @Override
        public void initView() {

        }

        @Override
        public void onClick(View v) {

        }
    }

}
