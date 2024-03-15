package com.easy.example.ui.recycle

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.recycle.gallery.GallerySnapHelper
import com.easy.core.recycle.gallery.RecyclerCoverFlow
import com.easy.core.ui.base.BaseDataBindingActivity
import com.easy.example.R
import com.easy.example.adapter.SnapHelperAdapter
import com.easy.example.databinding.ActivityGallerySnapBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.recycle
 * @FileName :   GallerySnapActivity
 * @Date : 2019/6/18 0018  下午 6:37
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class GallerySnapActivity( ) : BaseDataBindingActivity<ActivityGallerySnapBinding>() {

    override fun getLayoutId(): Int {
return R.layout.activity_gallery_snap
    }


    var mRecyclerview: RecyclerView? = null
    var mData: ArrayList<String>? = null
    var mLayoutManager: LinearLayoutManager? = null
    var mGallerySnapHelper: GallerySnapHelper? = null
    var mRcfView: RecyclerCoverFlow? = null
    override fun initView() {
        mRecyclerview = findViewById(R.id.recyclerview)
        mRcfView = findViewById(R.id.rcf_view)
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerview!!.setLayoutManager(mLayoutManager)
        initData()
        mRecyclerview!!.setAdapter(SnapHelperAdapter(this, mData!!))
        mGallerySnapHelper = GallerySnapHelper()
        mGallerySnapHelper!!.attachToRecyclerView(mRecyclerview)
        mRcfView!!.setAdapter(SnapHelperAdapter(this, mData!!))
        //        mRcfView.setAlphaItem(true);
        mRcfView!!.setFlatFlow(false)
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        binding.rcList.layoutManager = gridLayoutManager
        binding.rcList.adapter = SnapHelperAdapter(this, mData!!)
        PagerSnapHelper().attachToRecyclerView(binding.rcList)
    }



    private fun initData() {
        mData = ArrayList()
        for (i in 0..59) {
            mData!!.add("i=$i")
        }
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, GallerySnapActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}