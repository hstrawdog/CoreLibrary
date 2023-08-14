package com.easy.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easy.example.R
import com.easy.example.adapter.SnapHelperAdapter.GalleryViewHoler

/**
 * Created by chenzhimao on 17-7-6.
 */
class SnapHelperAdapter(context: Context?, data: ArrayList<String>) : RecyclerView.Adapter<GalleryViewHoler>() {
    var mInflater: LayoutInflater
    var mData: ArrayList<String>
    var imgs = intArrayOf(R.drawable.jdzz, R.drawable.ccdzz, R.drawable.dfh, R.drawable.dlzs, R.drawable.sgkptt, R.drawable.ttxss, R.drawable.zmq, R.drawable.zzhx)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHoler {
        val view = mInflater.inflate(R.layout.gallery_item_layout, parent, false)
        return GalleryViewHoler(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHoler, position: Int) {
        holder.mImageView.setImageResource(imgs[position % 8])
        holder.mTextView.text = mData[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class GalleryViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView
        var mTextView: TextView

        init {
            mImageView = itemView.findViewById<View>(R.id.image) as ImageView
            mTextView = itemView.findViewById<View>(R.id.tv_num) as TextView
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }
}