package com.easy.example.ui.customize;

import android.app.Activity;
import android.content.Intent;

import com.easy.example.R;
import com.easy.core.ui.base.BaseActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.customize
 * @FileName :   FishActivity
 * @Date : 2019/7/18 0018  下午 5:08
 * @Email :  qiqiang213@gmail.com
 * @Describe : https://www.jianshu.com/p/3dd3d1524851
 */
public class FishActivity extends BaseActivity {


    public static void open(Activity context) {
        Intent starter = new Intent(context, FishActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_fish;
    }


    @Override
    public void initView() {

    }
}
