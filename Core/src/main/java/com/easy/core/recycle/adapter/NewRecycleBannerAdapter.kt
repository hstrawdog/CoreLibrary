package com.easy.core.recycle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
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
abstract class NewRecycleBannerAdapter<T : Any, VH : RecyclerView.ViewHolder> : BaseQuickAdapter<T, VH>() {

    override fun getItemCount(items: List<T>): Int {
        return Int.MAX_VALUE
    }


}