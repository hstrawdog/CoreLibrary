package com.easy.example.ui.customize.matrix

import android.graphics.Matrix
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.log.LogUtils.dInfo
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.matix
 * @FileName :   MatrixActivity
 * @Date : 2020/7/23 0023  上午 9:52
 * @Email :  qiqiang213@gmail.com
 * @Describe : 矩阵的简单使用
 * 参考 https://blog.csdn.net/MonaLisaTearr/article/details/80177726
 */
class BaseMatrixActivity : BaseActivity() {
    lateinit var mButton41: Button
    lateinit var mButton42: Button
    lateinit var mButton43: Button
    lateinit var mButton44: Button
    lateinit var mButton45: Button
    lateinit var mButton46: Button
    lateinit var mButton47: Button

    override fun getLayoutViewId(): Int {
        return R.layout.activity_base_matrix

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun initView() {
        mButton47 = findViewById(R.id.button47)
        mButton46 = findViewById(R.id.button46)
        mButton45 = findViewById(R.id.button45)
        mButton44 = findViewById(R.id.button44)
        mButton43 = findViewById(R.id.button43)
        mButton42 = findViewById(R.id.button42)
        mButton41 = findViewById(R.id.button41)
        mButton41.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            matrix.setRotate(45f)
            mButton41.setAnimationMatrix(matrix)
        })
        mButton42.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            matrix.setTranslate(100f, 100f)
            mButton42.setAnimationMatrix(matrix)
        })
        mButton43.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            matrix.setScale(0.5f, 0.5f)
            mButton43.setAnimationMatrix(matrix)
        })
        mButton44.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            matrix.setScale(1.5f, 1.5f)
            mButton44.setAnimationMatrix(matrix)
        })
        mButton45.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            matrix.setScale(1.5f, 1.5f)
            matrix.postRotate(45f)
            matrix.postTranslate(200f, 200f)
            matrix.postScale(0.1f, 0.1f)
            matrix.postTranslate(200f, 200f)
            matrix.preRotate(90f)

            // pre 下到上 ->set 覆盖 ->  post上到下
            mButton45.setAnimationMatrix(matrix)
        })
        mButton46.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            matrix.setSkew(1f, 0f, 0f, 0f)
            mButton46.setAnimationMatrix(matrix)
        })
        mButton47.setOnClickListener(View.OnClickListener {
            val matrix = Matrix()
            val rect = Rect()
            mButton47.getGlobalVisibleRect(rect)
            dInfo(rect.toString())
            val dst = FloatArray(8)
            val src = FloatArray(8)
            val bw = mButton47!!.getWidth()
            val bh = mButton47!!.getHeight()
            /**
             * 原始坐标
             */
            /**
             * 原始坐标
             */
            src[0] = 0f
            src[1] = 0f
            src[2] = bw.toFloat()
            src[3] = 0f
            src[4] = 0f
            src[5] = bh.toFloat()
            src[6] = bw.toFloat()
            src[7] = bh.toFloat()
            /**
             * 新坐标
             * 这里我们只改变第四个点的坐标（没什么意外的话会出现一个不规则的菱形）
             */
            /**
             * 新坐标
             * 这里我们只改变第四个点的坐标（没什么意外的话会出现一个不规则的菱形）
             */
            dst[0] = 0f
            dst[1] = 0f
            dst[2] = bw.toFloat()
            dst[3] = 0f
            dst[4] = 0f
            dst[5] = bh.toFloat()

            //最后一个点的坐标xy都增加两倍
            dst[6] = (bw * 2).toFloat()
            dst[7] = (bh * 2).toFloat()
            matrix.setPolyToPoly(src, 0, dst, 0, 4)
            mButton47.animationMatrix = matrix
            val pts = FloatArray(8)
            mButton47.matrix.mapPoints(pts)
            dInfo(pts.toString())
        })
    }
}