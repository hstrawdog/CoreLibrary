package com.easy.core.recycle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import  com.easy.core.R
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.recycle.RecycleViewBanner.RecycleViewBannerChangeListener
import com.easy.core.recycle.RecycleViewBanner.RecycleViewBannerClickListener

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   BaseBannerAdapter
 * @Date : 2018/6/15 0015  下午 5:26
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class RecycleBannerAdapter<Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     *   数据源对象
     */
    var data = ArrayList<Any>()

    /**
     * 点击事件
     */
    private var onRvBannerClickListener: RecycleViewBannerClickListener? = null

    /**
     *  改变事件     重新绑定数据源地址与标题
     */
    private var mRecycleViewBannerChangeListener: RecycleViewBannerChangeListener<Any>? = null

    /**
     *  是否显示标题/提示
     */
    var isShowTip = false

    /**
     *  是否开始无限循环
     */
    var isUnlimited = true
    /**
     *  默认居中裁剪
     */
    var scaleType = ImageView.ScaleType.CENTER_CROP

    fun setOnRvBannerClickListener(onRvBannerClickListener: RecycleViewBannerClickListener) {
        this.onRvBannerClickListener = onRvBannerClickListener
    }

    fun setRecycleViewBannerChangeListener(recycleViewBannerChangeListener: RecycleViewBannerChangeListener<Any>?): RecycleBannerAdapter<Any> {
        mRecycleViewBannerChangeListener = recycleViewBannerChangeListener
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = onCreateView(parent)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindView(holder, position)
    }

    override fun getItemCount(): Int {
        return if (isUnlimited) {
            if (data.size < 2) data.size else Int.MAX_VALUE
        } else {
            data.size
        }
    }

    private fun onCreateView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
    }

    private fun onBindView(holder: RecyclerView.ViewHolder, position: Int) {
        val img = holder.itemView.findViewById<ImageView>(R.id.iv_banner)
        val tv = holder.itemView.findViewById<TextView>(R.id.tv_code_banner)
        val item = data[position % data.size]
        img.scaleType = scaleType
        if (item is String) {
            tv.visibility = View.GONE
            ImageLoadUtils.with(item as String, img)
        } else if (item is Int) {
            tv.visibility = View.GONE
            ImageLoadUtils.with(item as Int, img)
        } else if (null != mRecycleViewBannerChangeListener) {
            ImageLoadUtils.with(mRecycleViewBannerChangeListener!!.getUrl(item), img)
            if (isShowTip) {
                tv.text = mRecycleViewBannerChangeListener!!.getTitle(item)
                tv.visibility = View.VISIBLE
            } else {
                tv.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            if (onRvBannerClickListener != null) {
                onRvBannerClickListener!!.onBannerClick(position % (data.size))
            }
        }
    }


}