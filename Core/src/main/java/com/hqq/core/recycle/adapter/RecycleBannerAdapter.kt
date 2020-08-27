package com.hqq.core.recycle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.R
import com.hqq.core.glide.ImageLoadUtils
import com.hqq.core.recycle.RecycleViewBanner.RecycleViewBannerChangeListener
import com.hqq.core.recycle.RecycleViewBanner.RecycleViewBannerClickListener

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   BaseBannerAdapter
 * @Date : 2018/6/15 0015  下午 5:26
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class RecycleBannerAdapter<Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mData: MutableList<Any>
    private var onRvBannerClickListener: RecycleViewBannerClickListener<*>? = null
    private var mRecycleViewBannerChangeListener: RecycleViewBannerChangeListener<Any>? = null
    var isShowTip = false

    private var mIsUnlimited = true
    fun setUnlimited(unlimited: Boolean) {
        mIsUnlimited = unlimited
    }

    fun setData(data: MutableList<Any>?) {
        data?.let {
            mData = it
        }
    }

    fun setOnRvBannerClickListener(onRvBannerClickListener: RecycleViewBannerClickListener<*>) {
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
        return if (mIsUnlimited) {
            if (mData == null) 0 else if (mData.size < 2) mData.size else Int.MAX_VALUE
        } else {
            if (mData == null) 0 else mData.size
        }
    }


    private fun onCreateView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
    }

    private fun onBindView(holder: RecyclerView.ViewHolder?, position: Int) {
        val img = holder!!.itemView.findViewById<ImageView>(R.id.iv_banner)
        val tv = holder.itemView.findViewById<TextView>(R.id.tv_code_banner)
        val item = mData!![position % mData!!.size]
        if (item is String) {
            tv.visibility = View.GONE
            ImageLoadUtils.with(item as String, img)
        } else if (item is Int) {
            tv.visibility = View.GONE
            ImageLoadUtils.with(item as Int, img)
        } else if (null != mRecycleViewBannerChangeListener) {
            ImageLoadUtils.with(mRecycleViewBannerChangeListener!!.getUrl(item!!), img)
            if (isShowTip) {
                tv.text = mRecycleViewBannerChangeListener!!.getTitle(item!!)
                tv.visibility = View.VISIBLE
            } else {
                tv.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            if (onRvBannerClickListener != null) {
                onRvBannerClickListener!!.onBannerClick(position % (mData!!.size))
            }
        }
    }


}