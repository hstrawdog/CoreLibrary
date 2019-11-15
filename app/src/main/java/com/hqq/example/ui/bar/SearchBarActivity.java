package com.hqq.example.ui.bar;

import android.app.Activity;
import android.content.Intent;

import com.hqq.example.R;
import com.hqq.example.widget.BaseToolBarSearch;
import com.hqq.core.ui.BaseActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   SearchBarActivity
 * @Date : 2018/12/14 0014
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class SearchBarActivity extends BaseActivity {
    public static void open(Activity context) {
        Intent starter = new Intent(context, SearchBarActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int setViewId() {
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
