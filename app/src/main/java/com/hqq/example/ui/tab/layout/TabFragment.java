package com.hqq.example.ui.tab.layout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hqq.core.ui.base.BaseFragment;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.tab_layout
 * @FileName :   TabFragment
 * @Date : 2019/6/10 0010  上午 10:07
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class TabFragment extends BaseFragment {
    @Override
    public int getLayoutViewId() {
        return 0;
    }

    @Override
    public View getLayoutView(ViewGroup group) {
        TextView textView = new TextView(mActivity);
        textView.setText("viewPage Fragment");
        return textView;
    }

    @Override
    public void initView() {

    }

}
