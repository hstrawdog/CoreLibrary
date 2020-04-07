package com.hqq.core.utils;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * @FileName: com.hqq.core.utils.BaseCommonUtils.java
 * @emain: 593979591@qq.com
 * @date: 2020-04-07 10:54
 * 在此写用途
 */
public class BaseCommonUtils {


    /**
     * 黑白化的一种解决方案
     * https://mp.weixin.qq.com/s/8fTWLYaPhi0to47EUmFd7A
     * https://mp.weixin.qq.com/s/EioJ8ogsCxQEFm44mKFiOQ
     *
     * @param activity
     */
    public static void blackAndWhite(Activity activity) {
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }


}
