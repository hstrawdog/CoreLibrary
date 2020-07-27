package com.hqq.example.ui.matrix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.hqq.core.ui.BaseCoreActivity;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.R;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.matix
 * @FileName :   MatrixActivity
 * @Date : 2020/7/23 0023  上午 9:52
 * @Email :  qiqiang213@gmail.com
 * @Descrive : 矩阵的简单使用
 * 参考 https://blog.csdn.net/MonaLisaTearr/article/details/80177726
 */
public class MatrixActivity extends BaseCoreActivity {


    Button mButton41;
    Button mButton42;
    Button mButton43;
    Button mButton44;
    Button mButton45;
    Button mButton46;
    Button mButton47;

    public static void open(Activity context) {
        Intent starter = new Intent(context, MatrixActivity.class);
        context.startActivityForResult(starter, -1);
    }


    @Override
    public int getLayoutViewId() {
        return R.layout.activity_matrix;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void initView() {
        mButton47=findViewById(R.id.button47);
        mButton46=findViewById(R.id.button46);
        mButton45=findViewById(R.id.button45);
        mButton44=findViewById(R.id.button44);
        mButton43=findViewById(R.id.button43);
        mButton42=findViewById(R.id.button42);
        mButton41=findViewById(R.id.button41);


        mButton41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.setRotate(45);
                mButton41.setAnimationMatrix(matrix);
            }
        });
        mButton42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.setTranslate(100, 100);
                mButton42.setAnimationMatrix(matrix);
            }
        });
        mButton43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.setScale(0.5f, 0.5f);
                mButton43.setAnimationMatrix(matrix);
            }
        });
        mButton44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.setScale(1.5f, 1.5f);
                mButton44.setAnimationMatrix(matrix);
            }
        });
        mButton45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.setScale(1.5f, 1.5f);
                matrix.postRotate(45);
                matrix.postTranslate(200, 200);
                matrix.postScale(0.1f, 0.1f);
                matrix.postTranslate(200, 200);
                matrix.preRotate(90);

                // pre 下到上 ->set 覆盖 ->  post上到下

                mButton45.setAnimationMatrix(matrix);
            }
        });
        mButton46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();

                matrix.setSkew(1, 0, 0, 0);
                mButton46.setAnimationMatrix(matrix);
            }
        });
        mButton47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                Rect rect = new Rect();
                mButton47.getGlobalVisibleRect(rect);
                LogUtils.e(rect.toString());

                float[] dst = new float[8];
                float[] src = new float[8];


                int bw = mButton47.getWidth();
                int bh = mButton47.getHeight();


                /**
                 * 原始坐标
                 */
                src[0] = 0;
                src[1] = 0;
                src[2] = bw;
                src[3] = 0;
                src[4] = 0;
                src[5] = bh;
                src[6] = bw;
                src[7] = bh;

                /**
                 * 新坐标
                 * 这里我们只改变第四个点的坐标（没什么意外的话会出现一个不规则的菱形）
                 */
                dst[0] = 0;
                dst[1] = 0;
                dst[2] = bw;
                dst[3] = 0;
                dst[4] = 0;
                dst[5] = bh;

                //最后一个点的坐标xy都增加两倍
                dst[6] = bw * 2;
                dst[7] = bh * 2;

                matrix.setPolyToPoly(src, 0, dst, 0, 4);
                mButton47.setAnimationMatrix(matrix);

                float[] pts = new float[8];
                mButton47.getMatrix().mapPoints(pts);
                LogUtils.e(pts.toString());
            }
        });
    }



}