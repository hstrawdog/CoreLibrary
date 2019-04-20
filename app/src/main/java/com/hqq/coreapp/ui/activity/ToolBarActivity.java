package com.hqq.coreapp.ui.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hqq.coreapp.R;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.core.utils.statusbar.StatusBarManager;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   ToolBarActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :
 */
public class ToolBarActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_tool_bar;
    }

    @Override
    protected void initView() {

        mRootViewBuild.getDefToolBar().setRightTextView("分享", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(mActivity, "点击分享");

            }
        });
    }


    @Override
    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bar_right:

                break;
            case R.id.button5:
                mRootViewBuild.getDefToolBar().setStatusColor(ContextCompat.getColor(mActivity, R.color.main_color));
                break;
            case R.id.button4:
                mRootViewBuild.getDefToolBar().getToolBarBg().setImageResource(R.color.white);
                break;
            case R.id.button3:
                mRootViewBuild.getDefToolBar().getToolBarBg().setImageResource(R.color.main_color);
                break;
            case R.id.button:
                StatusBarManager.statusBarLightMode(mActivity, true);

                break;
            case R.id.button2:
                StatusBarManager.statusBarLightMode(mActivity, false);

                break;
            case R.id.button6:
                mRootViewBuild.getDefToolBar().setToolbarTitle("new标题");
                break;
            default:
        }
    }
}
