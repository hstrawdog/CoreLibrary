package com.easy.example.ui.recycle

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.recycle.RecycleViewBanner
import com.easy.core.recycle.adapter.NewRecycleBannerAdapter
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityBannerNewBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   BannerActivity
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Describe : TODO
 */
class NewBannerActivity : BaseViewBindingActivity<ActivityBannerNewBinding>() {



    override fun initView() {




        binding.rcBanner?.setAdapter(Adapter().apply {
            add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
            add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
            add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
            add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
            add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        })




//        val list2: MutableList<Any> = ArrayList()
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        list2.add(R.mipmap.ic_banner2)
//        binding.rcBanner2?.setAdapter(list2)
    }

    class Adapter  : NewRecycleBannerAdapter<String, QuickViewHolder>(){
        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {

            ImageLoadUtils.with( item?.get(position).toString(), holder.getView(R.id.iv_banner)
            )
        }

        override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
                return  QuickViewHolder(R.layout.item_new_bannner,parent)
        }

    }

}