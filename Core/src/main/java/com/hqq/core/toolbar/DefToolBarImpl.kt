package com.hqq.core.toolbar

import android.app.Activity
import android.media.Image
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
import com.hqq.core.R
import com.hqq.core.utils.ResourcesUtils
import com.hqq.core.widget.FilterImageView

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   BaseDefToolBarImpl
 * @Date : 2018/11/15 0015  下午 7:27
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class DefToolBarImpl : BaseToolBar() {

    override var toolBarBg: ImageView? = null
    private val mDefTitleColor = R.color.color_333
    override fun iniToolBar(activity: Activity, viewGroup: ViewGroup?): View {
        val toolbar = LayoutInflater.from(activity.baseContext)
                .inflate(R.layout.layout_def_toolbar, viewGroup, false) as Toolbar
        toolbar.findViewById<View>(R.id.iv_bar_back).setOnClickListener { activity.onBackPressed() }
        toolBarBg = toolbar.findViewById(R.id.iv_toolBar_Bg)
        toolBarBg?.setBackgroundResource(mDefToolBarColor)
        (toolbar.findViewById<View>(R.id.tv_bar_title) as TextView).text = activity.title
        (toolbar.findViewById<View>(R.id.tv_bar_title) as TextView).setTextColor(
                ContextCompat.getColor(
                        toolbar.context,
                        mDefTitleColor
                )
        )
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

    override fun setToolBarColor(color: Int) {
        toolBarBg?.setImageResource(color)
        mDefToolBarColor = color
        if (null != statusBar) {
            statusBar!!.setBackgroundResource(color)
        } else {
            defStatusColor = color
        }
    }

    override fun setToolbarTitle(title: CharSequence?) {
        title?.let {
            toolbarTitle?.text = title
        }

    }

    /**
     * 设置背景的透明度
     *
     * @param ahpla
     */
    fun initScrollNoImage(ahpla: Float) {
        var ahpla = ahpla
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
     * 设置背景颜色 需要在view创建完成
     *
     * @param resid
     */
    fun setToolBarBg(@DrawableRes resid: Int): DefToolBarImpl {
        mDefToolBarColor = resid
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
    fun setDefToolBarColor(defToolBarColor: Int): DefToolBarImpl {
        mDefToolBarColor = defToolBarColor
        return this
    }

    /**
     * 设置标题颜色
     *
     * @param defTitleColor
     * @return
     */
    fun setDefTitleColor(defTitleColor: Int): DefToolBarImpl {
        toolbarTitle!!.setTextColor(ContextCompat.getColor(toolbarTitle!!.context, defTitleColor))
        return this
    }

    /**
     * 设置 左边图片
     *
     * @param id
     * @return
     */
    fun setLetImageView(id: Int): DefToolBarImpl {
        leftView.setImageResource(id)
        return this
    }

    /**
     * 添加View
     *
     * @param view
     */
    fun addRightView(view: View): View {
        (rootView?.findViewById<View>(R.id.ll_right) as LinearLayout).addView(view)
        return view
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
            title: String?,
            @ColorRes color: Int,
            clickListener: View.OnClickListener?
    ): TextView {
        return addRightTextView(title, R.color.color_333, R.dimen.x28, clickListener)
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
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        textView.layoutParams = params
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourcesUtils.getDimen(size))
        textView.setOnClickListener(clickListener)
        textView.setTextColor(ResourcesUtils.getColor(color))
        return textView
    }
    //endregion

    //region   图片

    /**
     * 添加图片
     *
     * @param icImage
     * @param listener
     */
    fun addRightImageView(@DrawableRes icImage: Int, listener: View.OnClickListener?): ImageView {
        return addRightImageView(icImage, 0, 0, ResourcesUtils.getDimen(R.dimen.x20).toInt(), 0, listener)
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
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.setPadding(left, top, right, bottom)
        imageView.setOnClickListener(listener)
        return imageView
    }
    //endregion

}