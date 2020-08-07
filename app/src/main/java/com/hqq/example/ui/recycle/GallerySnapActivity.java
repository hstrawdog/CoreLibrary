package com.hqq.example.ui.recycle;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hqq.core.recycle.gallery.GallerySnapHelper;
import com.hqq.core.recycle.gallery.RecyclerCoverFlow;
import com.hqq.core.ui.binding.BaseBindingActivity;
import com.hqq.example.R;
import com.hqq.example.adapter.SnapHelperAdapter;
import com.hqq.example.databinding.ActivityGallerySnapBinding;

import java.util.ArrayList;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.recycle
 * @FileName :   GallerySnapActivity
 * @Date : 2019/6/18 0018  下午 6:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class GallerySnapActivity extends BaseBindingActivity<ActivityGallerySnapBinding> {

    public static void open(Activity context) {
        Intent starter = new Intent(context, GallerySnapActivity.class);
        context.startActivityForResult(starter, -1);
    }


    RecyclerView mRecyclerview;
    ArrayList<String> mData;
    LinearLayoutManager mLayoutManager;
    GallerySnapHelper mGallerySnapHelper;
    RecyclerCoverFlow mRcfView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gallery_snap;
    }

    @Override
    public void initView() {
        mRecyclerview = findViewById(R.id.recyclerview);
        mRcfView = findViewById(R.id.rcf_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerview.setLayoutManager(mLayoutManager);
        initData();
        mRecyclerview.setAdapter(new SnapHelperAdapter(this, mData));
        mGallerySnapHelper = new GallerySnapHelper();
        mGallerySnapHelper.attachToRecyclerView(mRecyclerview);


        mRcfView.setAdapter((new SnapHelperAdapter(this, mData)));
//        mRcfView.setAlphaItem(true);
        mRcfView.setFlatFlow(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mBinding.rcList.setLayoutManager(gridLayoutManager);
        mBinding.rcList.setAdapter(new SnapHelperAdapter(this, mData));
        new PagerSnapHelper().attachToRecyclerView(mBinding.rcList);

    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mData.add("i=" + i);
        }
    }


}
