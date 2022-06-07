package com.hqq.example.ui.color

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.log.LogUtils.dInfo
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R
import java.util.*

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.color
 * @FileName :   HSLActivity
 * @Date : 2020/4/29  下午3:48
 * @Email : qiqiang213@gmail.com
 * @Descrive :
</描述当前版本功能> */
class HSLActivity : BaseActivity() {


    override val layoutViewId: Int
        get() = R.layout.activity_h_s_l
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

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, HSLActivity::class.java)
            context.startActivity(starter)
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
}