package com.hqq.example.dialog;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hqq.core.ui.BaseSelectDialog;
import com.hqq.core.ui.BaseViewBuilderHolder;
import com.hqq.example.R;

import javax.inject.Inject;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.dialog
 * @FileName :   SelectADialog
 * @Date : 2019/10/31 0031  上午 11:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SelectADialog extends BaseSelectDialog<BaseViewBuilderHolder> {

    public static void showDialog(FragmentManager fragmentManager) {
        SelectADialog dialog = new SelectADialog();
        dialog.show(fragmentManager);
    }


    @Override
    protected BaseViewBuilderHolder getViewHolder() {
        return new ViewBuilderHolderA().builder(this, mRootView.findViewById(R.id.ll_content));
    }

    @Override
    protected void initData() {

    }


    class ViewBuilderHolderA extends BaseViewBuilderHolder {
        public ViewBuilderHolderA() {

        }

        @Override
        public int getViewId() {
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
