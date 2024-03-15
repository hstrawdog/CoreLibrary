package com.easy.example.ui.customize;


import android.app.Activity;
import android.content.Intent;

import com.easy.core.ui.base.BaseActivity;
import com.easy.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize
 * @FileName :   TemplatesImageActivity
 * @Date : 2020/1/10 0010  下午 1:41
 * @Email :  qiqiang213@gmail.com
 * @Describe : 限定区域内图片拖动
 */
public class TemplatesImageActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, TemplatesImageActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_templates_image;
    }

    @Override
    public void initView() {

    }
}
