package com.easy.example.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.glide.ImageLoadUtils.with
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.adapter
 * @FileName :   BannerAdapter
 * @Date : 2018/12/20 0020  下午 3:32
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
class BannerAdapter : BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_banner_adapter) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        with("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg", helper.getView<View>(R.id.iv_banner) as ImageView)
    }
}