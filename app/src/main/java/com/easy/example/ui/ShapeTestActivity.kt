package com.easy.example.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.ResourcesUtils.getDimen
import com.easy.core.utils.shape.BaseShapeBuilder
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @FileName :   ShapeTestActivity
 * @Date : 2019/12/6 0006  下午 3:51
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class ShapeTestActivity : BaseActivity() {
    var mTextView3: TextView? = null


    override fun getLayoutViewId(): Int {
        return  R.layout.activity_shape_test
    }

    override fun initView() {
        mTextView3 = findViewById(R.id.textView3)
        findViewById<View>(R.id.textView3).background = BaseShapeBuilder().setRectangle()
                .setColor(R.color.color_b36d61)
                .setCornerRadius(R.dimen.x20)
                .setStroke(R.dimen.x20, R.color.color_main)
                .builder()
        findViewById<View>(R.id.textView4).background = BaseShapeBuilder().setRectangle()
                .setColor(R.color.color_b36d61)
                .setCornerRadius(R.dimen.x20)
                .builder()
        (findViewById<View>(R.id.textView5) as ImageView).setImageDrawable(BaseShapeBuilder().setRectangle()
                .setColor(R.color.color_2b)
                .setCornerRadius(R.dimen.x20)
                .setSize(getDimen(R.dimen.x10).toInt(), getDimen(R.dimen.x10).toInt())
                .builder()
        )
    }
}