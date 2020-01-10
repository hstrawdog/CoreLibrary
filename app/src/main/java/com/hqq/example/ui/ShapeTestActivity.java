package com.hqq.example.ui;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.utils.shape.BaseShapeBuilder;
import com.hqq.example.R;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @FileName :   ShapeTestActivity
 * @Date : 2019/12/6 0006  下午 3:51
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class ShapeTestActivity extends BaseActivity {

    @BindView(R.id.textView3)
    TextView mTextView3;

    @Override
    public int setViewId() {
        return R.layout.activity_shape_test;
    }

    @Override
    public void initView() {
        mTextView3.setBackground(new BaseShapeBuilder().setRectangle()
                .setColor(R.color.color_b36d61)
                .setCornerRadius(R.dimen.x20)
                .setStroke(R.dimen.x20, R.color.color_main)
                .builder()
        );

        findViewById(R.id.textView4).setBackground(new BaseShapeBuilder().setRectangle()
                .setColor(R.color.color_b36d61)
                .setCornerRadius(R.dimen.x20)
                .builder()
        );

        ((ImageView) findViewById(R.id.textView5)).setImageDrawable(new BaseShapeBuilder().setRectangle()
                .setColor(R.color.color_2b)
                .setCornerRadius(R.dimen.x20)
                .setSize((int) ResourcesUtils.getDimen(R.dimen.x10), (int) ResourcesUtils.getDimen(R.dimen.x10))
                .builder()
        );
    }


}
