package com.hqq.coreapp.ui.activity;

import android.app.Activity;
import android.content.Intent;

import com.hqq.coreapp.R;
import com.hqq.coreapp.weight.BaseToolBarSearch;
import com.hqq.core.ui.BaseActivity;

/**
  * @Author : huangqiqiang
  * @Package : com.hqq.iblibrary.ui.activity
  * @FileName :   SearchBarActivity
  * @Date  : 2018/12/14 0014
  * @Descrive : TODO
  * @Email :
  */
public class SearchBarActivity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, SearchBarActivity.class);
        context.startActivityForResult(starter,-1);
    }

    @Override
    public int getViewId() {
        return R.layout.activity_search_bar;
    }

    @Override
    public void initDefConfig() {
        super.initDefConfig();
        mRootViewBuild.setIToolBarClass(BaseToolBarSearch.class);
    }

     @Override
     public void initView() {

    }
}
