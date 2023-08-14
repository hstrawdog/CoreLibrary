package com.easy.example.ui.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.widget.ImageView;

import com.easy.core.ui.base.BaseActivity;
import com.easy.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.view
 * @Date : 09:55
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class MirrorActivity extends BaseActivity {
    @Override
    public int getLayoutViewId() {
        return R.layout.layout_mirror;
    }

    @Override
    public void initView() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_square);
        Bitmap bit = convertBitmap(bitmap, 0, 50);
        ImageView imageView = findViewById(R.id.imageView16);
        imageView.setImageBitmap(bit);


    }

    /**
     * @param srcBitmap 原图
     * @param offsetX   0-100
     * @param offsetY   0-100
     * @return
     */
    private Bitmap convertBitmap(Bitmap srcBitmap, int offsetX, int offsetY) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        Bitmap new2 = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, m, true);

        int xSize = width / 4;
        int ySize = height / 4;
        int OffsetY = ySize * ((offsetY - 50) / 50);
        int OffsetX = xSize * ((offsetX - 50) / 50);
        // 左边图片
        cv.drawBitmap(srcBitmap,
                new Rect(xSize + OffsetX, 0, xSize + width / 2 + OffsetX, height),
                new Rect(0, 0, width / 2, height), null);
        // 右边图片
        cv.drawBitmap(new2,
                new Rect(xSize - OffsetX, 0, xSize + width / 2 - OffsetX, height),
                new Rect(width / 2, OffsetY, width, height + OffsetY), null);
        return newb;
    }

    /**
     *  镜像图片
     * @param srcBitmap
     * @return
     */
    private Bitmap convertBitmap(Bitmap srcBitmap) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        Bitmap new2 = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, m, true);

        // 右边图片
        cv.drawBitmap(new2,
                new Rect(0, 0,   width , height),
                new Rect(0, 0, width, height ), null);
        return newb;
    }
}

