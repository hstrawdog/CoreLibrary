package com.hqq.core.app.weight;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hqq.core.app.R;
import com.hqq.core.toolbar.BaseToolBar;

/**
 * @Author : huangqiqiang
 * @Package : com..sellercenter.toolsbar
 * @FileName :   BaseToolBarSearch
 * @Date : 2018/11/27 0027  下午 1:14
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class BaseToolBarSearch extends BaseToolBar {

    @Override
    public View iniToolBar(final Activity activity, ViewGroup viewGroup) {
        Toolbar mToolbar = (Toolbar)
                LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.layout_toolbar_order_search, viewGroup, false);
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(mToolbar);
        }
        mToolbar.findViewById(R.id.iv_bar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        return mToolbar;
    }

    @Override
    public void setToolBarColor(int colorId) {
        
    }

    public EditText getSearchView() {
        return mRootView.findViewById(R.id.edt_search);
    }
    public TextView getRightTextView() {
        return mRootView.findViewById(R.id.tv_bar_right);
    }
    public void setRightTextView(String text, View.OnClickListener onClickListener) {
        TextView textView = getRightTextView();
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(onClickListener);

    }

    public void setRightTextView(View.OnClickListener onClickListener) {
        TextView textView = getRightTextView();
        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(onClickListener);

    }
}
