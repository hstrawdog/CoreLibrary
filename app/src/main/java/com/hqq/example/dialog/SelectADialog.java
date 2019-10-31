package com.hqq.example.dialog;

import android.view.ViewGroup;

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
public class SelectADialog extends BaseSelectDialog<SelectADialog.ViewHolderA> {

    public static void showDialog(FragmentManager fragmentManager) {
        SelectADialog dialog = new SelectADialog();
        dialog.show(fragmentManager);
    }


    @Override
    public void initView() {
        mViewHolder = new ViewHolderA(this, mRootView.findViewById(R.id.ll_content));
        mViewHolder.addToParent();
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
    }

}
