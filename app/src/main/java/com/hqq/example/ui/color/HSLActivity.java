package com.hqq.example.ui.color;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;

import java.util.Arrays;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.color
 * @FileName :   HSLActivity
 * @Date : 2020/4/29  下午3:48
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */

public class HSLActivity extends BaseActivity {

    public static void open(Context context) {
        Intent starter = new Intent(context, HSLActivity.class);
        context.startActivity(starter);
    }


    @Override
    public int getViewId() {
        return R.layout.activity_h_s_l;
    }

    @Override
    public void initView() {

        float[] outHsl = new float[]{0f, 0f, 0f};


        ColorUtils.colorToHSL(Color.parseColor("#ef2b2c"), outHsl);
        ((ImageView) findViewById(R.id.imageView5)).setBackgroundColor(Color.parseColor("#ef2b2c"));

        LogUtils.e("" + outHsl.toString());


        float[] newHsl = new float[]{15, outHsl[1] * 1.16f, outHsl[2] * 0.93f};


        ColorUtils.HSLToColor(newHsl);
        ((ImageView) findViewById(R.id.imageView6)).setBackgroundColor(computeHSL("#ef2b2c", 15f, 1.16f, 0.93f));
        ((ImageView) findViewById(R.id.imageView7)).setBackgroundColor(Color.parseColor("#ff4306"));
        float[] outHsl1 = new float[]{0f, 0f, 0f};
        ColorUtils.colorToHSL(Color.parseColor("#ff4306"), outHsl1);

        ((TextView) findViewById(R.id.textView10)).setText("主 #ef2b2c \n" +
                "SHL" + Arrays.toString(outHsl) + "\n" +
                "C1 #ff4306 \n" +
                "C1 SHL" + Arrays.toString(newHsl) + "\n" +
                "C1 SHL" + Arrays.toString(outHsl1) + "\n"

        );
    }


    /**
     *
     * @param color 颜色值
     * @param outHsl 输出比例
     * @return
     */
    public static int computeHSL(String color, float... outHsl) {
        float[] hsl = new float[]{0f, 0f, 0f};
        ColorUtils.colorToHSL(Color.parseColor(color), hsl);
        float[] newHsl = new float[]{outHsl[0], hsl[1] * outHsl[1], hsl[2] * outHsl[2]};
        return ColorUtils.HSLToColor(newHsl);

    }


}
