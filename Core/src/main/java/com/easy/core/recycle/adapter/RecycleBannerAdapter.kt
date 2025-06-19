package com.easy.core.recycle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.R
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.recycle.RecycleViewBannerChangeListener
import com.easy.core.recycle.RecycleViewBannerClickListener

/**
 * 无限循环 Banner Adapter
 */
class RecycleBannerAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data:List<T> = emptyList()
    var isUnlimited:Boolean = true
    var isShowTip:Boolean = false
    var scaleType:ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP

    private var bannerClickListener:RecycleViewBannerClickListener? = null
    private var bannerChangeListener:RecycleViewBannerChangeListener<T>? = null

    fun setOnRvBannerClickListener(listener:RecycleViewBannerClickListener) {
        this.bannerClickListener = listener
    }

    fun setRecycleViewBannerChangeListener(listener:RecycleViewBannerChangeListener<T>?) {
        this.bannerChangeListener = listener
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position:Int) {
        val realPosition = getRealPosition(position)
        val item = data.getOrNull(realPosition) ?: return

        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_banner)
        val textView = holder.itemView.findViewById<TextView>(R.id.tv_code_banner)

        imageView.scaleType = scaleType

        when (item) {
            is String -> {
                textView.visibility = View.GONE
                ImageLoadUtils.with(item, imageView)
            }

            is Int -> {
                textView.visibility = View.GONE
                ImageLoadUtils.with(item, imageView)
            }

            else -> {
                val url = bannerChangeListener?.getUrl(item)
                val title = bannerChangeListener?.getTitle(item)
                if (!url.isNullOrEmpty()) {
                    ImageLoadUtils.with(url, imageView)
                }
                if (isShowTip && !title.isNullOrEmpty()) {
                    textView.text = title
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.GONE
                }
            }
        }

        holder.itemView.setOnClickListener {
            bannerClickListener?.onBannerClick(realPosition)
        }
    }

    override fun getItemCount():Int {
        return if (isUnlimited && data.size > 1) Int.MAX_VALUE else data.size
    }

    private fun getRealPosition(position:Int):Int {
        return if (data.isEmpty()) 0 else position % data.size
    }
}
