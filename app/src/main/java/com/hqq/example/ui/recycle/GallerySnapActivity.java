package com.hqq.example.ui.recycle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hqq.example.R;
import com.hqq.example.adapter.SnapHelperAdapter;
import com.hqq.core.recycle.gallery.GallerySnapHelper;
import com.hqq.core.recycle.gallery.RecyclerCoverFlow;
import com.hqq.core.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.recycle
 * @FileName :   GallerySnapActivity
 * @Date : 2019/6/18 0018  下午 6:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class GallerySnapActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    ArrayList<String> mData;
    LinearLayoutManager mLayoutManager;
    GallerySnapHelper mGallerySnapHelper;
    @BindView(R.id.rcf_view)
    RecyclerCoverFlow mRcfView;

    @Override
    public int getViewId() {
        return R.layout.activity_gallery_snap;
    }

    @Override
    public void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerview.setLayoutManager(mLayoutManager);
        initData();
        mRecyclerview.setAdapter(new SnapHelperAdapter(this, mData));
        mGallerySnapHelper = new GallerySnapHelper();
        mGallerySnapHelper.attachToRecyclerView(mRecyclerview);


        mRcfView.setAdapter((new SnapHelperAdapter(this, mData)));
//        mRcfView.setAlphaItem(true);
        mRcfView.setFlatFlow(false);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mData.add("i=" + i);
        }
    }


}
