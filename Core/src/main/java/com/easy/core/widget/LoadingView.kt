package com.easy.core.widget

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.utils.data.isNotNull
import com.easy.core.utils.ResourcesUtils


/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.widget
 * @FileName :   LoadingView
 * @Date  : 2017-02-11 22:02
 * @Email :  qiqiang213@gmail.com
 * @Describe : loadingView
 */
class LoadingView constructor(var mContext: Context, themeResId: Int = R.style.LoadingDialogStyle) :
    Dialog(mContext, themeResId) {
    private var tvMsg: TextView? = null
    private var loadingWidth = ResourcesUtils.getDimen(R.dimen.x200)
        .toInt()

    var dialogDismiss: DialogDismiss? = null
    override fun dismiss() {
        if (this == null) {
            return
        }
        dialogDismiss?.onDialogDismiss()
        super.dismiss()
    }


    var mMsg = "正在加载中"

    constructor(context: Context, msg: String) : this(context, R.style.LoadingDialogStyle) {
        mMsg = msg
    }

    constructor(context: Context, msg: String, width: Int) : this(context, msg) {
        loadingWidth = width;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_layout)
        tvMsg = findViewById<View>(R.id.tv_msg) as TextView
        tvMsg?.text = mMsg

        val iv = findViewById<ImageView>(R.id.iv_loading)

        var config = CoreConfig.get()
        if (config.isShowGif && config.gifLoading.isNotNull()) {
            val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            Glide.with(iv)
                .load(CoreConfig.get().gifLoading)
                .apply(options)
                .into(iv)

        } else {
            iv.setBackgroundResource(CoreConfig.get().defLoadingImage)
            val rotationAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f)
            rotationAnimator.setDuration(2000)
            rotationAnimator.repeatCount = ObjectAnimator.INFINITE
            rotationAnimator.interpolator = LinearInterpolator()
            rotationAnimator.start()
        }

        //这里我选了最高级
        // getWindow().setType(WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL);
    }

    fun setBg() {
        (findViewById<View>(R.id.rl_loading) as ImageView).setBackgroundResource(R.color.transparent)
    }

    fun setTipMsg(msg: String?) {
        tvMsg?.text = msg
    }

    fun setTipMsg(msg: Int) {
        tvMsg?.setText(msg)
    } //    public void start() {

    /**
     * 设置显示的提示标题
     * @param title String
     */
    fun setShowTitle(title: String) {
        mMsg = title
        tvMsg?.text = title
    }

    interface DialogDismiss {
        fun onDialogDismiss()
    }
}