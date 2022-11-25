package com.hqq.example.ui.file.image;


import android.app.Activity;
import android.content.Intent;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.file.image
 * @FileName :   ImageViewRectangleActivity
 * @Date : 2020/6/30 0030  下午 2:45
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 长方形控件 正方形图片测试
 */
public class ImageViewRectangleActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, ImageViewRectangleActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_image_view_rectangle;
    }

    @Override
    public void initView() {

    }
}