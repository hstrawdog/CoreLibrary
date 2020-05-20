package com.hqq.example.ui.customize;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;
import com.hqq.example.ui.customize.widget.GuideView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize
 * @FileName :   CurtainActivity
 * @Date : 2020/5/12 0012  下午 3:22
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class CurtainActivity extends BaseActivity {

    public static void open(Activity context) {
        Intent starter = new Intent(context, CurtainActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getViewId() {
        return R.layout.activity_curtain;

    }

    @Override
    public void initView() {
        GuideView guideView = findViewById(R.id.gv_view);

        findViewById(R.id.textView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect rect = new Rect();
                findViewById(R.id.imageView8).getDrawingRect(rect);


                int[] viewLocation = new int[2];
                findViewById(R.id.imageView8).getLocationOnScreen(viewLocation);



                LogUtils.e("");

                guideView.setTargetView( findViewById(R.id.imageView8));
                guideView.setVisibility(View.VISIBLE);
            }
        });
    }
}
