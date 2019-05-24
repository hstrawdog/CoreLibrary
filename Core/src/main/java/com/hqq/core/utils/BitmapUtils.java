package com.hqq.core.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   BitmapUtils
 * @Date : 2018/6/25 0025  上午 11:35
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class BitmapUtils {

    /**
     * 等比缩放图片
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth - 5) / width;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();

        return bitmapCombine(newbm, 5, 5, Color.TRANSPARENT);

    }

    /**
     * 获得添加边框了的Bitmap
     *
     * @param bm     原始图片Bitmap
     * @param smallW 一条边框宽度
     * @param smallH 一条边框高度
     * @param color  边框颜色值
     * @return Bitmap 添加边框了的Bitmap
     */
    private static Bitmap bitmapCombine(Bitmap bm, int smallW, int smallH, int color) {
        //防止空指针异常
        if (bm == null) {
            return null;
        }

        // 原图片的宽高
        final int bigW = bm.getWidth();
        final int bigH = bm.getHeight();

        // 重新定义大小
        int newW = bigW + smallW * 2;
        int newH = bigH + smallH * 2;

        // 绘原图
        Bitmap newBitmap = Bitmap.createBitmap(newW, newH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(new Rect(0, 0, newW, newH), p);

        // 绘边框
        canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW, (newH
                - bigH - 2 * smallH)
                / 2 + smallH, null);


        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        bm.recycle();
        return newBitmap;
    }



    public static  Bitmap zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return newbmp;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    public synchronized static String getBase64(String path) {
        Bitmap bitmap = null;
        //字节数组输出流
        ByteArrayOutputStream baos = null;
        FileInputStream fs = null;
        String base64 = "";
        try {
            byte[] byteArray = ScalePicture(path, 30).toByteArray();
            base64 = new String(Base64.encode(byteArray, Base64.DEFAULT), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (fs != null) {
                    fs.close();
                }
                bitmap = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return base64;
    }

    public static ByteArrayOutputStream ScalePicture(String fileName, int options) {
        Bitmap image = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            FileInputStream fs = new FileInputStream(fileName);
            BufferedInputStream bs = new BufferedInputStream(fs);
            image = BitmapFactory.decodeStream(bs);
            if ((fs.available() / 1024) <= 512 && fs.available() != 0) {
                // 如果文件小于 521k  不压缩
                options = 100;
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //这里压缩options%，把压缩
            baos.writeTo(new FileOutputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos;
    }
    public static String getImageHead(String key) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("JPEG", "data:image/jpeg;base64,");
        stringMap.put("JPG", "data:image/jpg;base64,");
        stringMap.put("GIF", "data:image/gif;base64,");
        stringMap.put("PNG", "data:image/png;base64,");
        return stringMap.get(key);
    }
}
