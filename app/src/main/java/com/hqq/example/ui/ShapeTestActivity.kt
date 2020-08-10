package com.hqq.example.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ResourcesUtils.getDimen
import com.hqq.core.utils.shape.BaseShapeBuilder
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @FileName :   ShapeTestActivity
 * @Date : 2019/12/6 0006  下午 3:51
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class ShapeTestActivity : BaseActivity() {
    var mTextView3: TextView? = null
    override fun getLayoutViewId(): Int {
        return R.layout.activity_shape_test
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