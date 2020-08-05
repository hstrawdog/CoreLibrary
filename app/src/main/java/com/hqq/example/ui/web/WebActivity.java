package com.hqq.example.ui.web;

import android.app.Activity;
import android.content.Intent;

import com.hqq.example.R;
import com.hqq.core.ui.base.BaseFrameLayoutActivity;
import com.hqq.core.ui.web.BaseWebFragment;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.web
 * @FileName :   WebActivity
 * @Date : 2019/8/5 0005  下午 7:52
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */

public class WebActivity extends BaseFrameLayoutActivity {

    public static final String URL = "URL";
    public static final String TITLE = "TITLE";

    public static void open(Activity context, String url, String title) {
        Intent starter = new Intent(context, WebActivity.class);
        starter.putExtra(URL, url);
        starter.putExtra(TITLE, title);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_web;
    }


    @Override
    public void initDefConfig() {
        super.initDefConfig();
        mRootViewBuild.setShowToolBar(false);
        mRootViewBuild.setShowStatusBar(false);
    }

    @Override
    public void initView() {

        String url = getIntent().getExtras().getString(URL, "https://www.baidu.com/");
        String title = getIntent().getExtras().getString(TITLE, "网页");


        BaseWebFragment mBaseFragment = BaseWebFragment.instantiate(this, title, url);
        addOrShowFragment(mBaseFragment, R.id.fl_layout);
    }


    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null) {
            if (!((BaseWebFragment) mCurrentFragment).onBackPressed()) {
                super.onBackPressed();
            }
        }
    }
}
