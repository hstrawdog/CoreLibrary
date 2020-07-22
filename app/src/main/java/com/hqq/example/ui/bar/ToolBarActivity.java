package com.hqq.example.ui.bar;

import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.core.utils.statusbar.StatusBarManager;
import com.hqq.example.R;

import butterknife.OnClick;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   ToolBarActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class ToolBarActivity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, ToolBarActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_tool_bar;
    }

    @Override
    public void initView() {

//        mRootViewBuild.getDefToolBar().setRightTextView("分享", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showToast(mActivity, "点击分享");
//
//            }
//        });

        mRootViewBuild.getDefToolBar().addRightTextView("分享", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, "点击分享");
            }
        });
        mRootViewBuild.getDefToolBar().addRightTextView("分享",R.color.color_333, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, "点击分享");
            }
        });
//        mRootViewBuild.getDefToolBar().addRightTextView("分享2", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtils.showToast(mActivity, "点击分享2");
//
//            }
//        });
        mRootViewBuild.getDefToolBar().addRightImageView(R.mipmap.ic_more, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mActivity, "点击more");

            }
        });
    }


    @Override
    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button12, R.id.button13})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bar_right:
                break;
            case R.id.button5:
                mRootViewBuild.getDefToolBar().setDefStatusColor(ContextCompat.getColor(mActivity, R.color.color_77400a));
                break;
            case R.id.button4:
                mRootViewBuild.getDefToolBar().getToolBarBg().setImageResource(R.color.white);
                break;
            case R.id.button7:
                mRootViewBuild.getDefToolBar().getToolBarBg().setImageResource(R.color.color_000);
                break;
            case R.id.button3:
                mRootViewBuild.getDefToolBar().getToolBarBg().setImageResource(R.color.color_77400a);
                break;
            case R.id.button:
                StatusBarManager.setStatusBarModel(mActivity.getWindow(), true);
                break;
            case R.id.button2:
                StatusBarManager.setStatusBarModel(mActivity.getWindow(), false);
                break;
            case R.id.button6:
                mRootViewBuild.getDefToolBar().setToolbarTitle("new标题");
                break;
            case R.id.button8:
                mRootViewBuild.getDefToolBar().setToolBarColor(R.color.color_main);
                break;
            case R.id.button12:
                SettingToolBarActivity.open(this);
                break;
            case R.id.button13:
                SearchBarActivity.open(this);
                break;
            default:
        }
    }


}
