package com.easy.core.toolbar

import android.app.Activity
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.easy.core.CoreConfig
import  com.easy.core.R
import  com.easy.core.utils.ResourcesUtils
import com.easy.core.widget.FilterImageView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   BaseDefToolBarImpl
 * @Date : 2018/11/15 0015  下午 7:27
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
class DefToolBar : BaseToolBar() {

    /**
     *  标题栏 背景
     */
    override var toolBarBg: ImageView? = null

    private val mDefTitleColor = CoreConfig.get().defTitleColor


    override fun iniToolBar(activity: Activity, viewGroup: ViewGroup?): View {
        val toolbar = LayoutInflater.from(activity.baseContext).inflate(
            R.layout.layout_def_toolbar, viewGroup, false
        ) as Toolbar
        toolbar.findViewById<View>(R.id.iv_bar_back).setOnClickListener { activity.onBackPressed() }
        toolBarBg = toolbar.findViewById(R.id.iv_toolBar_Bg)
        toolBarBg?.setImageResource(defToolBarColor)
        (toolbar.findViewById<View>(R.id.tv_bar_title) as TextView).text = activity.title
        (toolbar.findViewById<View>(R.id.tv_bar_title) as TextView).setTextColor(
            ContextCompat.getColor(toolbar.context, mDefTitleColor)
        )
        R.string.key_url
        return toolbar
    }

    /**
     * 获取左边 图标View
     *
     * @return ImageView
     */
    val leftView: ImageView
        get() = rootView!!.findViewById(R.id.iv_bar_back)

    /**
     * 获取标题View
     *
     * @return
     */
    val toolbarTitle: TextView?
        get() = rootView?.findViewById(R.id.tv_bar_title)

    /**
     * 滑动效果
     *
     * @param ahpla 0 -1
     */
    override fun initScroll(ahpla: Float) {
        var ahpla = ahpla
        if (ahpla > 1) {
            ahpla = 1f
        }
        super.initScroll(ahpla)
        if (ahpla < 0.5) {
            leftView.setImageResource(R.mipmap.ic_black_whit)
            leftView.alpha = 1 - ahpla * 2
        } else {
            leftView.setImageResource(R.mipmap.ic_back_gray)
            leftView.alpha = ((ahpla - 0.5) * 2).toFloat()
        }
        if (toolbarTitle != null) {
            toolbarTitle!!.alpha = ahpla
        }
        if (toolBarBg != null) {
            toolBarBg!!.alpha = ahpla
        }
    }

    /**
     * 设置背景的透明度
     *
     * @param aphla
     */
    fun initScrollNoImage(float: Float) {
        var ahpla = float
        if (ahpla > 1) {
            ahpla = 1f
        }
        super.initScroll(ahpla)
        if (ahpla < 0.5) {
            leftView.alpha = 1 - ahpla * 2
        } else {
            leftView.alpha = ((ahpla - 0.5) * 2).toFloat()
        }
        if (toolbarTitle != null) {
            toolbarTitle!!.alpha = ahpla
        }
        if (toolBarBg != null) {
            toolBarBg!!.alpha = ahpla
        }
    }



    /**
     *   设置 标题
     * @param title CharSequence?
     */
    override fun setToolbarTitle(title: CharSequence?) {
        title?.let {
            toolbarTitle?.text = title
        }

    }

    /**
     *  配置 整个 bar的颜色
     * @param color Int
     */
    override fun setToolBarColor(color: Int) {
        // 设置  顶部
        setStatusColor(color)
        // 标题栏
        setToolBarBg(color)

    }
    /**
     * 设置背景颜色 需要在view创建完成
     *
     * @param resid   正常 应该传入 颜色资源 id
     */
    fun setToolBarBg( resid: Int): DefToolBar {
        defToolBarColor = resid
        if (toolBarBg != null) {
            toolBarBg!!.setImageResource(resid)
        }
        return this
    }

    /**
     * 配置默认颜色
     *
     * @param defToolBarColor
     * @return
     */
    fun setDefToolBarColor(defToolBarColor: Int): DefToolBar {
        this.defToolBarColor = defToolBarColor
        return this
    }

    /**
     * 设置标题颜色
     *
     * @param titleColor
     * @return
     */
    fun setDefTitleColor(titleColor: Int): DefToolBar {
        toolbarTitle!!.setTextColor(ContextCompat.getColor(toolbarTitle!!.context, titleColor))
        return this
    }

    /**
     * 设置 左边图片
     *
     * @param id
     * @return
     */
    fun setLetImageView(id: Int): DefToolBar {
        leftView.setImageResource(id)
        return this
    }

    //region 添加右边文字
    /**
     * 添加文字
     *
     * @param title
     * @param clickListener
     */
    fun addRightTextView(title: String?, clickListener: View.OnClickListener?): TextView {
        return addRightTextView(title, R.color.color_333, clickListener)
    }

    /**
     * @param title
     * @param color
     * @param clickListener
     */
    fun addRightTextView(
        title: String?, @ColorRes color: Int, clickListener: View.OnClickListener?
    ): TextView {
        return addRightTextView(title, color, R.dimen.x28, clickListener)
    }

    /**
     * @param title
     * @param color
     * @param size
     * @param clickListener
     */
    fun addRightTextView(
        title: String?,
        @ColorRes color: Int,
        @DimenRes size: Int,
        clickListener: View.OnClickListener?
    ): TextView {
        return addRightView(newTextView(title, color, size, clickListener)) as TextView
    }

    /**
     * 默认文本
     *
     * @param title
     * @param color
     * @param size
     * @param clickListener
     * @return
     */
    private fun newTextView(
        title: String?,
        @ColorRes color: Int,
        @DimenRes size: Int,
        clickListener: View.OnClickListener?
    ): View {
        val textView = TextView(rootView?.context)
        textView.text = title
        textView.setPadding(0, 0, ResourcesUtils.getDimen(R.dimen.x20).toInt(), 0)
        textView.gravity = Gravity.CENTER
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        textView.layoutParams = params
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourcesUtils.getDimen(size))
        textView.setOnClickListener(clickListener)
        textView.setTextColor(ResourcesUtils.getColor(color))
        return textView
    }
    //endregion

    //region   添加图片

    /**
     * 添加图片
     *
     * @param icImage
     * @param listener
     */
    fun addRightImageView(@DrawableRes icImage: Int, listener: View.OnClickListener?): ImageView {
        return addRightImageView(
            icImage,
            0,
            0,
            ResourcesUtils.getDimen(R.dimen.x20).toInt(),
            0,
            listener
        )
    }

    /**
     * 添加右边的图片
     */
    fun addRightImageView(
        @DrawableRes icImage: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        listener: View.OnClickListener?
    ): ImageView {
        return addRightView(newImageView(icImage, left, top, right, bottom, listener)) as ImageView
    }

    /**
     * 默认的图片
     *
     * @param icImage
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param listener
     * @return
     */
    private fun newImageView(
        @DrawableRes icImage: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        listener: View.OnClickListener?
    ): View {
        val imageView = FilterImageView(rootView?.context)
        imageView.adjustViewBounds = true
        imageView.setImageResource(icImage)
        imageView.scaleType = ImageView.ScaleType.CENTER
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.setPadding(left, top, right, bottom)
        imageView.setOnClickListener(listener)
        return imageView
    }
    //endregion

    /**
     * 添加View
     *
     * @param view
     */
    fun addRightView(view: View): View {
        (rootView?.findViewById<View>(R.id.ll_right) as LinearLayout).addView(view)
        return view
    }

    /**
     * 右边布局
     * @return LinearLayout
     */
    fun getRightView(): LinearLayout {
        return (rootView?.findViewById<View>(R.id.ll_right) as LinearLayout)
    }

}