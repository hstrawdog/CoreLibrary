package com.easy.example.ui.color

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.log.LogUtils.dInfo
import com.easy.example.R
import java.util.*

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.color
 * @FileName :   HSLActivity
 * @Date : 2020/4/29  下午3:48
 * @Email : qiqiang213@gmail.com
 * @Describe :
</描述当前版本功能> */
class HSLActivity : BaseActivity() {



    override fun getLayoutViewId(): Int {
        return  R.layout.activity_h_s_l
    }

    override fun initView() {
        val outHsl = floatArrayOf(0f, 0f, 0f)
        ColorUtils.colorToHSL(Color.parseColor("#ef2b2c"), outHsl)
        (findViewById<View>(R.id.imageView5) as ImageView).setBackgroundColor(Color.parseColor("#ef2b2c"))
        dInfo("" + outHsl.toString())
        val newHsl = floatArrayOf(15f, outHsl[1] * 1.16f, outHsl[2] * 0.93f)
        (findViewById<View>(R.id.imageView6) as ImageView).setBackgroundColor(computeHSL("#ef2b2c", 15f, 1.16f, 0.93f))
        (findViewById<View>(R.id.imageView7) as ImageView).setBackgroundColor(Color.parseColor("#ff4306"))
        val outHsl1 = floatArrayOf(0f, 0f, 0f)
        ColorUtils.colorToHSL(Color.parseColor("#ff4306"), outHsl1)
        (findViewById<View>(R.id.textView10) as TextView).text = """
            主 #ef2b2c
            SHL${Arrays.toString(outHsl)}
            C1 #ff4306
            C1 SHL${Arrays.toString(newHsl)}
            C1 SHL${Arrays.toString(outHsl1)}
            """.trimIndent()
    }


    /**
     *
     * @param color 颜色值
     * @param outHsl 输出比例
     * @return
     */
    fun computeHSL(color: String?, vararg outHsl: Float): Int {
        val hsl = floatArrayOf(0f, 0f, 0f)
        ColorUtils.colorToHSL(Color.parseColor(color), hsl)
        val newHsl = floatArrayOf(outHsl[0], hsl[1] * outHsl[1], hsl[2] * outHsl[2])
        return ColorUtils.HSLToColor(newHsl)
    }
}